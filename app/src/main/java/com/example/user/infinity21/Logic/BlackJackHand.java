package com.example.user.infinity21.Logic;

/**
 * Created by pc on 2/19/2018.
 */


/// <summary>
/// This class is game-specific.  Creates a BlackJack hand that inherits from class hand
/// </summary>
public class BlackJackHand extends Hand {
    /// <summary>
/// This method compares two BlackJack hands
/// </summary>
/// <param name="otherHand"></param>
/// <returns></returns>
    public int CompareFaceValue(BlackJackHand otherHand) {

        try {
            if (otherHand != null) {
                //    return this.GetSumOfHand()
                int sum = GetSumOfHand();
                int otherSum = otherHand.GetSumOfHand();
                if(otherSum > Utility.BJMAX)
                    return 1;
                if(sum > Utility.BJMAX)
                    return -1;

                if(Integer.compare(sum, otherSum) == 0) {
                    int numCard = getNumCard();
                    int otherNumCard = otherHand.getNumCard();
                    return Integer.compare(otherNumCard, numCard);
                }
                return Integer.compare(sum, otherSum);
            }

        } catch (Exception e) {
            System.out.println("Argument is not a Hand");
            e.printStackTrace();
        }
        return -1;

    }

    /// <summary>
/// Gets the total value of a hand from BlackJack values
/// </summary>
/// <returns>int</returns>
    public int GetSumOfHand() {
        int val = 0;
        int numAces = 0;


        for (Card c : getCards()) {
            if(c.isFaceUp()) {
                if (c.getFaceVal() == Utility.FaceValue.Ace) {
                    numAces++;
                    val += 11;

                } else if (c.getFaceVal() == Utility.FaceValue.Jack || c.getFaceVal() == Utility.FaceValue.Queen
                        || c.getFaceVal() == Utility.FaceValue.King) {
                    val += 10;
                } else {
                    val += c.getFaceVal().ordinal();
                }
            }
        }

        while (val > 21 && numAces > 0) {
            val -= 10;
            numAces--;
        }

        return val;
    }
}
