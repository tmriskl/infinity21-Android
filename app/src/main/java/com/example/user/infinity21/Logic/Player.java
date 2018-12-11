package com.example.user.infinity21.Logic;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2/19/2018.
 */

public class Player {

    //Objects to store Player Information

//    // Creates a list of cards
//    private List<Card> cards = new ArrayList<>();
    private int balance;
    private int bet;
    private int wins;
    private int losses;
    private int pushes;
    private int PlayerID;
    private String image;
    private BlackJackHand hand1;
    private BlackJackHand hand2;
    private Deck currentDeck;
    private boolean splited;


    public List<Card> getCards1() {
        return hand1.getCards();
    }
    public List<Card> getCards2() {
        return hand2.getCards();
    }

    public void setCards1(List<Card> cards) {
        this.hand1.setCards(cards);
    }
    public void setCards2(List<Card> cards) {
        this.hand2.setCards(cards);
    }


    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    public BlackJackHand getHand1() {
        return hand1;
    }

    public BlackJackHand getHand() {
        if((hand2.GetSumOfHand() == 0)||(hand1.CompareFaceValue(hand2) > 0))
            return hand1;
        else
            return hand2;
    }

    public BlackJackHand getHand2() {
        return hand2;
    }

    public void setHand1(BlackJackHand hand) {
        this.hand1 = hand;
    }
    public void setHand2(BlackJackHand hand) {
        this.hand2 = hand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPushes() {
        return pushes;
    }

    public void setPushes(int pushes) {
        this.pushes = pushes;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void addBet(int bet) {
        this.bet += bet;
        this.balance -= bet;
    }

    public void win() {
        this.balance += bet*2;
        bet = 0;
        wins++;
    }

    public void lose() {
        bet = 0;
        losses++;
    }


    public void resetBet() {
        this.balance += bet;
        this.bet = 0;
    }



    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(int playerID) {
        PlayerID = playerID;
    }

    /// <summary>
    /// Creates a player with a default balance account (i.e. it doesn't matter what the dealer's balance is)
    /// </summary>
    public Player() {
        this(1000, -1, 0, 0);
    }


    public Player(int newBalance, int PlayerID, int wins, int losses) {
        // Sets the player's image and name that is displayed in the picture frame in the UI.
        //this.image = PlayerImageLocation(this.PlayerId);
        //this.name = PlayerName(this.PlayerId);
        this.hand1 = new BlackJackHand();
        this.hand2 = new BlackJackHand();
        this.balance = newBalance;
        this.PlayerID = PlayerID;
        splited = false;
        this.wins = wins;
        this.losses = losses;

    }

    public Player(PlayerTableRow playerTableRow) {
        // Sets the player's image and name that is displayed in the picture frame in the UI.
        //this.image = PlayerImageLocation(this.PlayerId);
        //this.name = PlayerName(this.PlayerId);
        this.hand1 = new BlackJackHand();
        this.hand2 = new BlackJackHand();
        this.balance = playerTableRow.getBalance();
        this.PlayerID = -1;
        splited = false;
        this.wins = playerTableRow.getWins();
        this.losses = playerTableRow.getLosses();

    }

    /// <summary>
    /// Increases the bet amount each time a bet is added to the hand.  Invoked through the betting coins in the BlackJackForm.cs UI
    /// </summary>
    /// <param name="amt"></param>
    public void IncreaseBet(float amount) {
        // Check to see if the user has enough money to make this bet
        if ((balance - (bet + amount)) >= 0) {
            // Add money to the bet
            bet += amount;
        } else {
            System.out.println("You do not have enough monney to make this bet.");
        }
    }

    /// <summary>
    /// Places the bet and subtracts the amount from "My Account"
    /// </summary>
    public void PlaceBet() {
        if ((balance - bet) >= 0) {
            balance = balance - bet;
        } else {
            System.out.println("You do not have enough money to place this bet");
        }
    }

    /// <summary>
    /// Creates a new hand for the current player and return it
    /// </summary>
    /// <returns>BlackJackHand</returns>
    public BlackJackHand NewHand() {
        this.hand1 = new BlackJackHand();
        this.hand2 = new BlackJackHand();
        return this.hand1;
    }

    /// <summary>
    /// Set the bet value back to 0
    /// </summary>
    public void ClearBet() {
        bet = 0;
    }

    /// <summary>
    /// Check if the current player has BlackJack
    /// </summary>
    /// <returns>Returns true if the current player has BlackJack</returns>
    public boolean HasBlackJack() {
        if (hand1.GetSumOfHand() > Utility.BJMAX)
            return true;
        else
            return false;
    }

    /// <summary>
    /// Player has hit, draw a card from the deck and add it to the player's hand
    /// </summary>
    public void Hit1() {
        Card c = currentDeck.Draw();
        if(c != null) {
            c.setFaceUp(true);
            hand1.getCards().add(c);
        }
    }

    public void Hit2() {
        if(hand2.GetSumOfHand() != 0 ) {
            Card c = currentDeck.Draw();
            if(c != null) {
                c.setFaceUp(true);
                hand2.getCards().add(c);
            }
        }
    }
    /// <summary>
    /// Doubling down is allowed straight after the first two cards are dealt.
    /// Player has chosen to double down, double the player's bet and hit once,and then stand.
    /// The player can't ask for any more hits after the third card. (Hit button disabled)
    /// </summary>
    public void DoubleDown(int hand) {
        IncreaseBet(bet);
        // only decrease the balance by half of the current bet
        balance = balance - (bet / 2);
        if(hand == 1)
            Hit1();
        else
            Hit2();
    }

    /// <summary>
    /// if your first two cards hold the same value (for example two two's or two eights) you can split them into two separate playing hands.
    /// A split hand becomes two separate bets and the dealer will hit you with a further card on each of the splits.
    /// Generally, you will not be allowed to hit, double down or re-split after splitting your card. Take note of each table's individual rules on this.
    /// </summary>
    public Boolean Split() {
        if(!splited) {
            if ((hand1.getCards().size() == 2)&&(hand1.getCards().get(0).getFaceVal() == hand1.getCards().get(1).getFaceVal())) {
                Card c = hand1.getCards().remove(hand1.getCards().size() - 1);
                hand2.getCards().add(c);
                splited = true;
            }
        }
        return splited;
    }

    public boolean isSplited(){
        return splited;
    }

    public void clearBet(){
        balance += bet;
        bet = 0;
    }
    public Boolean canSplit() {
        if ((hand1.getCards().size() == 2)&&(hand1.getCards().get(0).getFaceVal() == hand1.getCards().get(1).getFaceVal())) {
            return true;
        }
        return false;
    }


    /// <summary>
    /// Surrender is offered in when the dealer's upcard (the card you can see) is either an Ace or a ten value. A player who has surredndered
    /// forfeits half of his bet to the house. The round ends and a new round can commence.
    /// </summary>
    public void Surrender() {
        balance = balance - (bet / 2);
    }

    /// <summary>
    /// An option to limit how much you can lose
    /// Where dealer's up-card is an ace, a player can take insurance against the chance that the dealer has blackjack
    /// (also called a natural or a snapper)
    /// Insurance is generally limited to half of the original bet and pays out at 2:1.
    /// If both the ealer and the player come out with the same total, it's called a "push"/tie and no money changes hands.
    /// </summary>
    public void Insurance() {
        balance+= bet/2;
        balance -= bet;
    }




    public void IncrementWins() {
        wins++;
    }

    public void IncrementLosses() {
        losses++;
    }

    public void UpdateWinningBalance(int bet) {
        balance += bet * 2;

    }






/*
    private String PlayerImageLocation(int player)
    {
        switch (player)
        {
            case 1:
                return Properties.Settings.Default.PlayerOne_Image;

            case 2:
                return Properties.Settings.Default.PlayerTwo_Image;
            case 3:
                return Properties.Settings.Default.PlayerThree_Image;

            default:
                return Properties.Settings.Default.PlayerOne_Image;

        }
    }
*/

/*
    private String PlayerName(int player)
    {
        switch (player)
        {

            case 1:
                return Properties.Settings.Default.PlayerOneName;
            case 2:
                return Properties.Settings.Default.PlayerTwoName;
            case 3:
                return Properties.Settings.Default.PlayerThreeName;

            default:
                return Properties.Settings.Default.PlayerOneName;

        }
    }
    */


}
