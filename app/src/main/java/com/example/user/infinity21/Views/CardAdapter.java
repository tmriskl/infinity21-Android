package com.example.user.infinity21.Views;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.example.user.infinity21.Logic.Card;
import com.example.user.infinity21.Logic.Player;
import com.example.user.infinity21.Logic.Utility;
import com.example.user.infinity21.R;

/**
 * Created by User on 21/02/2018.
 */

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private Player mPalyer;
    private Boolean hand1 = true;
    private AnimationDrawable animation;


//
//    private Integer[] back = { // array that hold all the icons
//            R.drawable.card_back_black,
//            R.drawable.card_back_blue,
//            R.drawable.card_back_red
//    };

    private Integer[] Spades = { // array that hold all the icons
            R.drawable.spades_two,
            R.drawable.spades_three,
            R.drawable.spades_four,
            R.drawable.spades_five,
            R.drawable.spades_six,
            R.drawable.spades_seven,
            R.drawable.spades_eight,
            R.drawable.spades_nine,
            R.drawable.spades_ten,
            R.drawable.spades_jack,
            R.drawable.spades_queen,
            R.drawable.spades_king,
            R.drawable.spades_ace
    };

    private Integer[] Hearts = { // array that hold all the icons
            R.drawable.hearts_two,
            R.drawable.hearts_three,
            R.drawable.hearts_four,
            R.drawable.hearts_five,
            R.drawable.hearts_six,
            R.drawable.hearts_seven,
            R.drawable.hearts_eight,
            R.drawable.hearts_nine,
            R.drawable.hearts_ten,
            R.drawable.hearts_jack,
            R.drawable.hearts_queen,
            R.drawable.hearts_king,
            R.drawable.hearts_ace
    };

    private Integer[] Clubs = { // array that hold all the icons
            R.drawable.clubs_two,
            R.drawable.clubs_three,
            R.drawable.clubs_four,
            R.drawable.clubs_five,
            R.drawable.clubs_six,
            R.drawable.clubs_seven,
            R.drawable.clubs_eight,
            R.drawable.clubs_nine,
            R.drawable.clubs_ten,
            R.drawable.clubs_jack,
            R.drawable.clubs_queen,
            R.drawable.clubs_king,
            R.drawable.clubs_ace
    };

    private Integer[] Diamonds = { // array that hold all the icons
            R.drawable.diamonds_two,
            R.drawable.diamonds_three,
            R.drawable.diamonds_four,
            R.drawable.diamonds_five,
            R.drawable.diamonds_six,
            R.drawable.diamonds_seven,
            R.drawable.diamonds_eight,
            R.drawable.diamonds_nine,
            R.drawable.diamonds_ten,
            R.drawable.diamonds_jack,
            R.drawable.diamonds_queen,
            R.drawable.diamonds_king,
            R.drawable.diamonds_ace
    };


    public CardAdapter(Context context, Player player) {
        mPalyer = player;
        mContext = context;
    }

    @Override
    public int getCount() {
        if(hand1)
            return mPalyer.getCards1().size();
        return mPalyer.getCards2().size();
    }

    @Override
    public Object getItem(int position) {
        if(hand1)
            return mPalyer.getCards1().get(position);
        return mPalyer.getCards2().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardView cardView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            cardView = new CardView(mContext);
            cardView.setLayoutParams(new GridView.LayoutParams(Utility.cardWidth, Utility.cardHeight));
        }
        else
            cardView = (CardView) convertView;

        //decides what to put on cell
        Card card;
        if(hand1)
            card = mPalyer.getCards1().get(position);
        else
            card = mPalyer.getCards2().get(position);
        if(card.isFaceUp()) {
            Utility.CardType type = card.getCardType();
            if(type == Utility.CardType.SPADES){
                cardView.image.setImageResource(Spades[card.getFaceVal().ordinal()-2]);
            }
            else if(type == Utility.CardType.HEARTS){
                cardView.image.setImageResource(Hearts[card.getFaceVal().ordinal()-2]);
            }
            else if(type == Utility.CardType.CLUBS){
                cardView.image.setImageResource(Clubs[card.getFaceVal().ordinal()-2]);
            }
            else if(type == Utility.CardType.DIAMONDS){
                cardView.image.setImageResource(Diamonds[card.getFaceVal().ordinal()-2]);
            }
            else{
                cardView.image.setImageResource(Utility.backs[Utility.getBack()]);
            }
        }else{
            cardView.image.setImageResource(Utility.backs[Utility.getBack()]);
        }

        return cardView;    }

    public Player getPalyer() {
        return mPalyer;
    }

    public Boolean getHand1() {
        return hand1;
    }

    public void setHand1(Boolean hand1) {
        this.hand1 = hand1;
    }
}
