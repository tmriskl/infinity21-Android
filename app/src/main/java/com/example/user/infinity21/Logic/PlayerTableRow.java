package com.example.user.infinity21.Logic;

/**
 * Created by pc on 3/2/2018.
 */


public class PlayerTableRow {


    //private String username;
    private int wins;
    private int losses;
    //private int hand;
    //private int bet;


    private int balance;


    public PlayerTableRow(/*String username,*/ int wins, int losses/*, int hand, int bet*/, int balance) {
        //setUsername(username);
        setWins(wins);
        setLosses(losses);
        //setHand(hand);
        // NsetBet(bet);
        setBalance(balance);
    }


    public PlayerTableRow(Player player) {
        //setUsername("");
        setWins(player.getWins());
        setLosses(player.getLosses());
        //setHand(player.getHand().GetSumOfHand());
        //setBet(player.getBet());
        setBalance(player.getBalance());
    }

/*
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
*/
    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}