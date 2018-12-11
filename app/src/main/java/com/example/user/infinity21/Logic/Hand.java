package com.example.user.infinity21.Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 1/30/2018.
 */

public class Hand {
    // Creates a list of cards
    private List<Card> cards = new ArrayList<>();
    public int NumCard;

    /// Checks to see if the hand contains a card of a certain face value
    public boolean ContainsCard(Utility.FaceValue item) {
        for (Card c : cards) {
            if (c.getFaceVal() == item)
                return true;

        }
        return false;
    }


    public int getNumCard() {
        return cards.size();
    }

    public void setNumCard(int numCard) {
        NumCard = numCard;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
