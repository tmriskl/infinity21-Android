package com.example.user.infinity21.Logic;
import com.example.user.infinity21.Logic.Utility;

/**
 * Created by pc on 1/30/2018.
 */

public class Card
{
    /**
     * Card:
     Boolean isUp
     enum diamonds/club/hearts/spades
     int value
     */
    // Objects for card information
    private Utility.CardType cardType;
    private Utility.FaceValue faceVal;
    private boolean faceUp;


    /// <summary>
    /// Constructor for a new card.  Assign the card a suit, face value, and if the card is facing up or down
    /// </summary>
    /// <param name="suit"></param>
    /// <param name="faceVal"></param>
    /// <param name="isCardUp"></param>
    public Card(Utility.CardType cardType, Utility.FaceValue faceVal, boolean isCardUp)
    {
        this.cardType = cardType;
        this.faceVal = faceVal;
        this.faceUp = isCardUp;
    }


    public Utility.CardType getCardType() {
        return cardType;
    }

    public void setCardType(Utility.CardType cardType) {
        this.cardType = cardType;
    }

    public Utility.FaceValue getFaceVal() {
        return faceVal;
    }

    public void setFaceVal(Utility.FaceValue faceVal) {
        this.faceVal = faceVal;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }





    /// <summary>
    /// Return the card as a string (i.e. "The Ace of Spades")
    /// </summary>
    /// <returns></returns>
    public String ToString()
    {
        return "The" + faceVal.toString() + "of" + cardType.toString();
    }
}