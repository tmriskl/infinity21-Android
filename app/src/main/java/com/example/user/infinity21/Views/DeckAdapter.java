package com.example.user.infinity21.Views;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.user.infinity21.Logic.Card;
import com.example.user.infinity21.Logic.Deck;
import com.example.user.infinity21.Logic.Player;
import com.example.user.infinity21.Logic.Utility;
import com.example.user.infinity21.R;

/**
 * Created by User on 02/03/2018.
 */

public class DeckAdapter extends BaseAdapter {
    private Context mContext;
    private Deck deck;
    private AnimationDrawable animation;


//
//    private Integer[] back = { // array that hold all the icons
//            R.drawable.card_back_black,
//            R.drawable.card_back_blue,
//            R.drawable.card_back_red
//    };

    public DeckAdapter(Context context, Deck deck) {
        this.deck = deck;
        mContext = context;
    }

    @Override
    public int getCount() {
        return deck.numOfTop();
    }

    @Override
    public Object getItem(int position) {
        return deck.Top(position%Utility.top);
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
        cardView.image.setImageResource(Utility.backs[Utility.getBack()]);

        return cardView;    }
}