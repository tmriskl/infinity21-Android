package com.example.user.infinity21.Logic;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2/20/2018.
 */

import com.example.user.infinity21.Logic.Utility;

public class BlackJackGame{

    private int numofplayers;
    private Deck deck;
    private Player dealer;
    private List<Utility.EndResult> playerStatusArr;
    private List<Player> players;


    public void returnCardsToDeck(){
        for(Player player: players) {
            List<Card> cards = player.getCards1();
            cards.addAll(player.getCards2());
            deck.addCards(cards);
        }
    }

    public int getNumofplayers() {
        return players.size();
    }

    public void setNumofplayers(int numofplayers) {
        this.numofplayers = numofplayers;
    }


    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Player getDealer() {
        return dealer;
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public List<Utility.EndResult> getPlayerStatusArr() {
        return playerStatusArr;
    }

    public void setPlayerStatusArr(List<Utility.EndResult> playerStatusArr) {
        this.playerStatusArr = playerStatusArr;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayersarray(List<Player> playersarray) {
        this.players = players;
    }

    public BlackJackGame(List<Player> players) {
        // this.startForm = startForm;
        dealer = new Player();
        this.numofplayers = players.size();
        this.players = players;

        Update_PlayerStatusArr();


        deck = new Deck();
        deck.Shuffle();
    }

    private void Update_PlayerStatusArr() {
        playerStatusArr = new ArrayList<Utility.EndResult>();
        for (int i = 0; i < this.players.size(); i++) {
            playerStatusArr.add(Utility.EndResult.PlayerBetting);
        }
    }

    public boolean addPlayer(Player player){
        boolean added = false;
        if((players.size()<Utility.MAXNUMOFPLAYERS)&&(!players.contains(player))){
            added = players.add(player);
            added = added&&playerStatusArr.add(Utility.EndResult.PlayerBetting);
            if(added){
                Card card = deck.Draw();
                card.setFaceUp(true);
                this.players.get(players.size()-1).getHand1().getCards().add(card);
                card = deck.Draw();
                card.setFaceUp(true);
                this.players.get(players.size()-1).getHand1().getCards().add(card);
            }
        }
        return added;
    }
    public boolean removePlayer(Player player){
        boolean removed = false;
        if(players.contains(player)){
            int playerNum = players.indexOf(player);
            List<Card> cards = player.getCards1();
            cards.addAll(player.getCards2());
            deck.addCards(cards);
            removed = players.remove(player);
            removed = removed&&(!playerStatusArr.remove(playerNum).equals(null));
        }
        return removed;
    }
    public void DealNewGame() {
        // Create a new deck and then shuffle the deck
        deck = new Deck();
        deck.Shuffle();

        // Reset the player and the dealer's hands in case this is not the first game
        for (int i = 0; i < this.players.size(); i++) {
            if (playerStatusArr.get(i) != Utility.EndResult.PlayerHasLeft)
                this.players.get(i).NewHand();
        }
        dealer.NewHand();

        // Deal two cards to each person's hand (including the dealer)
        for (int i = 0; i < 2; i++) {

            for (int j = 0; j <  this.players.size(); j++) {
                if (playerStatusArr.get(j) != Utility.EndResult.PlayerHasLeft) {
                    // Draw the Card from the deck and add it to the specific player's Hand List of Cards
                    Card card = deck.Draw();
                    card.setFaceUp(true);
                    this.players.get(j).getHand1().getCards().add(card);
                }
                // Set the dealer's second card to be facing down
            }
            Card d = deck.Draw();
            if (i == 0)
                d.setFaceUp(true);
            dealer.getHand1().getCards().add(d);

            // Give the player and the dealer a handle to the current deck
            for (int k = 0; k <  this.players.size(); k++) {
                if (playerStatusArr.get(k) != Utility.EndResult.PlayerHasLeft)
                    this.players.get(k).setCurrentDeck(deck);
            }
            dealer.setCurrentDeck(deck);
        }
    }

    private void AllPlayersReadyToPlay() {
        int readyplayers = 0;
        for (int i = 0; i < this.players.size(); i++) {
            if (playerStatusArr.get(i) == Utility.EndResult.Ready || playerStatusArr.get(i) == Utility.EndResult.PlayerHasLeft)
                readyplayers++;
            else
                break;
        }
        if (readyplayers == this.numofplayers) {
            // All Players placed their bets
            for (int i = 0; i < this.players.size(); i++) {
                if (playerStatusArr.get(i) != Utility.EndResult.PlayerHasLeft)
                    playerStatusArr.set(i,Utility.EndResult.PlayerIsPlaying);
            }
            DealNewGame();
            // Dealer sends an event to the BlackJack FORM (Blackjack UI) that he is ready to play.
            // AsyncResponseToDealerEven


        }
    }

    private void AllPlayersGameEnded() {
        int endedgames = 0;
        for (int i = 0; i < this.players.size(); i++) {

            if (playerStatusArr.get(i) == Utility.EndResult.DealerBlackJack ||
                    playerStatusArr.get(i) == Utility.EndResult.DealerBust ||
                    playerStatusArr.get(i) == Utility.EndResult.DealerWin ||
                    playerStatusArr.get(i) == Utility.EndResult.PlayerBlackJack ||
                    playerStatusArr.get(i) == Utility.EndResult.PlayerBust ||
                    playerStatusArr.get(i) == Utility.EndResult.PlayerWin ||
                    playerStatusArr.get(i) == Utility.EndResult.Push ||
                    playerStatusArr.get(i) == Utility.EndResult.PlayerHasLeft)
                endedgames++;
            else
                break;
        }
        if (endedgames == this.numofplayers) {
            // DealerEvent(this, new PlayerEventArgs(-1,EndResut.PlayerBetting));
        }
    }

    private void AllPlayersLeft() {
        int playersleft = 0;
        for (int i = 0; i < this.players.size(); i++) {
            if (playerStatusArr.get(i) == Utility.EndResult.PlayerHasLeft)
                playersleft++;
            else
                break;
        }

        if (playersleft == this.numofplayers) {
            // all players have left, save balances.
        }
    }


    public void OnPlayerEvent(Object sender, PlayerEventArgs eventArgs) {
        switch (eventArgs.PlayerEndResult) {
            case Ready:
                playerStatusArr.set(eventArgs.getPlayerIndexNumber(),Utility.EndResult.Ready);
                AllPlayersReadyToPlay();
                break;
            case DealerBlackJack:
            case DealerBust:
            case DealerWin:
            case PlayerBlackJack:
            case PlayerBust:
            case PlayerWin:
            case Push:
                playerStatusArr.set(eventArgs.getPlayerIndexNumber(),eventArgs.PlayerEndResult);
                AllPlayersGameEnded();
                break;
            case PlayerHasLeft:
                playerStatusArr.set(eventArgs.getPlayerIndexNumber(), Utility.EndResult.PlayerHasLeft);
                AllPlayersReadyToPlay();
                AllPlayersGameEnded();
                AllPlayersLeft();
                break;
        }
    }


    public void DealerReadyPlay() {
        // Dealer's card that was facing down is turned up when they start playing
        for (Card card:dealer.getHand1().getCards()) {
            card.setFaceUp(true);
        }
        dealer.Split();
    }

    public boolean DealerPlay() {
        if(dealer.getHand1().GetSumOfHand() < 17)
            dealer.Hit1();
        else if((dealer.getHand2().GetSumOfHand() < 17)&&dealer.getHand2().GetSumOfHand()>0)
            dealer.Hit2();
        // Check to see if dealer has a hand that is less than 17
        return (dealer.getHand1().GetSumOfHand() < 17)||((dealer.getHand2().GetSumOfHand() < 17)&&dealer.getHand2().GetSumOfHand()>0);
    }

    public List<Player> getWinners(){
        List<Player> winners = new ArrayList<Player>();
            for(int i = 0;i<players.size();i++)
                if(dealer.getHand().CompareFaceValue(players.get(i).getHand()) < 0){
                    winners.add(players.get(i));
                    playerStatusArr.set(i, Utility.EndResult.PlayerWin);
                    players.get(i).win();
                }else {
                    players.get(i).lose();
                }
        return winners;
    }

    public void PlayerLose(int index) {
        this.players.get(index).IncrementLosses();
    }

    public void PlayerWin(int index) {
        this.players.get(index).UpdateWinningBalance(this.players.get(index).getBet());
        this.players.get(index).IncrementWins();
    }


}
