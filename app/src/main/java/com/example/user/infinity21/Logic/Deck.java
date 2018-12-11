package com.example.user.infinity21.Logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by pc on 1/30/2018.
 */

public class Deck
{
    // Creates a list of cards
    protected List<Card> cards = new ArrayList<>();

    // Returns the card at the given position
    public Card getCard (int position){
        Card c = cards.get(position);
        return c;
    }

    /// <summary>
    /// One complete deck with every face value and suit
    /// </summary>
    public Deck()
    {
        for (Utility.CardType suit: Utility.CardType.values()) {
            for (Utility.FaceValue faceVal : Utility.FaceValue.values()) {
                if(faceVal.ordinal()>1)
                    cards.add(new Card(suit,faceVal,false));
            }
        }
    }

    /// <summary>
    /// Draws one card and removes it from the deck
    /// </summary>
    /// <returns></returns>
    public Card Draw()
    {
        if(cards.size()>0) {
            Card card = cards.get(0);
            cards.remove(0);
            return card;
        }
        return null;
    }

    /// <summary>
    /// Shuffles the cards in the deck
    /// </summary>
    public void Shuffle()
    {
        //Random random = new Random();
        Random random = new Random();
        for (int i = 0; i <cards.size(); i++)
        {
            int index1 = i;
            int index2 = random.nextInt(cards.size());
            SwapCard(index1, index2);
        }
    }

    /// <summary>
    /// Swaps the placement of two cards
    /// </summary>
    /// <param name="index1"></param>
    /// <param name="index2"></param>
    private void SwapCard(int index1, int index2)
    {
        Collections.swap(cards,index1,index2);
    }

    public int numOfCards(){
        return cards.size();
    }

    public Card Top(int position){
        return cards.get(position%Utility.top);
    }


    public int numOfTop() {
        if(numOfCards() > Utility.top)
            return Utility.top;
        else
            return numOfCards();
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

}