package com.example.user.infinity21;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.example.user.infinity21.Logic.BlackJackGame;
import com.example.user.infinity21.Logic.DBHelper;
import com.example.user.infinity21.Logic.Player;
import com.example.user.infinity21.Logic.PlayerTableRow;
import com.example.user.infinity21.Logic.Utility;
import com.example.user.infinity21.Views.CardAdapter;
import com.example.user.infinity21.Views.DeckAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21/02/2018.
 */

public class GameActivity extends AppCompatActivity {

    final int NumOfColums = 6;
    final int NumOfColumsMultiplyer = 4;
    ArrayList<CardAdapter> playersAdapter = new ArrayList<CardAdapter>();
    ArrayList<GridView> playersCards = new ArrayList<GridView>();
    ArrayList<CardAdapter> playersAdapter2 = new ArrayList<CardAdapter>();
    ArrayList<GridView> playersCards2 = new ArrayList<GridView>();
    ArrayList<TextView> playersWinLose = new ArrayList<TextView>();
    ArrayList<TextView> playerBets = new ArrayList<TextView>();
    CardAdapter dealerAdapter;
    CardAdapter dealerAdapter2;
    GridView dealerCards;
    GridView dealerCards2;
    TextView dealerWinLose;
    GridView deck;
    DeckAdapter deckAdapter;
    BlackJackGame blackJackGame;
    int currentPlayer = 0;
    boolean join = true;
    boolean delerPlay = false;
    boolean winner = false;
    Button hit;
    Button split;
    Button double_down;
    Button double_down2;
    Button stand;
    Button bet1;
    Button bet2;
    Button bet3;
    Button bet4;
    Button claer;
    DBHelper dbHelper;
    Player player;
    Button add;
    Button remove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
        List<PlayerTableRow> playersdata = dbHelper.getAllPlayerTableRows();
        if(playersdata.isEmpty()){
            player = new Player();
            dbHelper.clearDatabase(Utility.DBNAME);
            dbHelper.insertPlayer(new PlayerTableRow(player));
        }else
            player = new Player(playersdata.get(0));

        playersCards.add((GridView)findViewById(R.id.Player1Hand1));
        playersCards.add((GridView)findViewById(R.id.Player2Hand1));
        playersCards.add((GridView)findViewById(R.id.Player3Hand1));
        playersCards.add((GridView)findViewById(R.id.Player4Hand1));
        /*for(GridView playerCards: playersCards){
            playerCards.setMinimumWidth(playerCards.getWidth()*2);
        }*/

        playersCards2.add((GridView)findViewById(R.id.Player1Hand2));
        playersCards2.add((GridView)findViewById(R.id.Player2Hand2));
        playersCards2.add((GridView)findViewById(R.id.Player3Hand2));
        playersCards2.add((GridView)findViewById(R.id.Player4Hand2));
        /*for(GridView playerCards: playersCards2){
            playerCards.setMinimumWidth(0);
        }*/

        playersWinLose.add((TextView)findViewById(R.id.Player1WinLose));
        playersWinLose.add((TextView)findViewById(R.id.Player2WinLose));
        playersWinLose.add((TextView)findViewById(R.id.Player3WinLose));
        playersWinLose.add((TextView)findViewById(R.id.Player4WinLose));
        dealerWinLose = (TextView)findViewById(R.id.DealerWinLose);
        /*for(TextView player :playersWinLose){
            player.setText("");
        }*/

        playerBets.add((TextView)findViewById(R.id.Player1Bet));
        playerBets.add((TextView)findViewById(R.id.Player2Bet));
        playerBets.add((TextView)findViewById(R.id.Player3Bet));
        playerBets.add((TextView)findViewById(R.id.Player4Bet));
        /*for(TextView player :playerBets){
            player.setText("");
        }*/

        for(GridView playerCards: playersCards) {
            playerCards.setNumColumns(NumOfColums);
            playerCards.setColumnWidth(Utility.cardWidth/3);
            //playerCards.setMinimumWidth((NumOfColums+2)*Utility.cardWidth/3);
        }

        for(GridView playerCards: playersCards2) {
            playerCards.setNumColumns(NumOfColums);
            playerCards.setColumnWidth(Utility.cardWidth/3);
            //playerCards.setMinimumWidth((NumOfColums+2)*Utility.cardWidth/3);
        }

        bet1 = (Button)findViewById(R.id.Bet1) ;
        bet2 = (Button)findViewById(R.id.Bet2) ;
        bet3 = (Button)findViewById(R.id.Bet3) ;
        bet4 = (Button)findViewById(R.id.Bet4) ;
        claer = (Button)findViewById(R.id.ClearBet) ;


        add = (Button)findViewById(R.id.Add);
        remove = (Button)findViewById(R.id.Remove);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(join){
                    addPlayer(new Player());
                    updateViews();
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(join){
                    removePlayer(blackJackGame.getPlayers().get(blackJackGame.getPlayers().size()-1));
                    updateViews();
                }
            }
        });
/*
        playersCards.get(0).setNumColumns(NumOfColums);
        playersCards.get(1).setNumColumns(NumOfColums);
        playersCards.get(2).setNumColumns(NumOfColums);
        playersCards.get(3).setNumColumns(NumOfColums);
        playersCards.get(0).setColumnWidth(Utility.cardWidth/3);
        playersCards.get(1).setColumnWidth(Utility.cardWidth/3);
        playersCards.get(2).setColumnWidth(Utility.cardWidth/3);
        playersCards.get(3).setColumnWidth(Utility.cardWidth/3);
        playersCards.get(3).setMinimumWidth((NumOfColums+2)*Utility.cardWidth/3);

        playersCards2.get(0).setNumColumns(NumOfColums);
        playersCards2.get(1).setNumColumns(NumOfColums);
        playersCards2.get(2).setNumColumns(NumOfColums);
        playersCards2.get(3).setNumColumns(NumOfColums);
        playersCards2.get(0).setColumnWidth(Utility.cardWidth/3);
        playersCards2.get(1).setColumnWidth(Utility.cardWidth/3);
        playersCards2.get(2).setColumnWidth(Utility.cardWidth/3);
        playersCards2.get(3).setColumnWidth(Utility.cardWidth/3);
*/
        blackJackGame = new BlackJackGame(new ArrayList<Player>());
        dealerCards = (GridView)findViewById(R.id.DealerHand1);
        dealerCards.setNumColumns(NumOfColums);
        dealerAdapter = new CardAdapter(getApplicationContext(), blackJackGame.getDealer());
        dealerCards.setAdapter(dealerAdapter);
        dealerCards2 = (GridView)findViewById(R.id.DealerHand2);
        dealerCards2.setNumColumns(NumOfColums);
        dealerAdapter2 = new CardAdapter(getApplicationContext(), blackJackGame.getDealer());
        dealerAdapter2.setHand1(false);
        dealerCards2.setAdapter(dealerAdapter2);

//        addPlayer(new Player(5000,0,0,0));
//        addPlayer(new Player(300,1,0,0));
//        addPlayer(new Player(500,2,0,0));
//        addPlayer(new Player(597,3,0,0));
        addPlayer(player);
        blackJackGame.DealNewGame();

        deckAdapter = new DeckAdapter(getApplicationContext(), blackJackGame.getDeck());
        deck = ((GridView)findViewById(R.id.Deck));
        deck.setNumColumns(Utility.top*2);
        deck.setAdapter(deckAdapter);

        hit = ((Button)findViewById(R.id.Hit));
        setHitButton();

        split = ((Button)findViewById(R.id.Split));
        setSpiltButton();

        stand = ((Button)findViewById(R.id.Stand));
        stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlayer < blackJackGame.getPlayers().size()-1) {
                    currentPlayer++;
                    setHitButton();
                    setSpiltButton();
                    setDouble(true);
                    setBets(true);
                }
                else{
                    join = false;
                    delerPlay = true;
                    setDouble(false);
                    setClickable(hit,false);
                    setClickable(split,false);
                    setClickable(double_down,false);
                    setClickable(double_down2,false);
                    setClickable(stand,false);
                    setBets(false);
                    setDouble(false);

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            blackJackGame.DealerReadyPlay();
                            pause();
                            while(blackJackGame.DealerPlay()) {
                                pause();
                                updateViews();
                            }
                            newGame();
                        }

                    });
                    t.start();

                }
              /* if(blackJackGame.getPlayers().size()>0) {
                    Player playerWinLos = blackJackGame.getPlayers().get(blackJackGame.getNumofplayers() - 1);
                    removePlayer(playerWinLos);
                }*/
                updateViews();
            }
        });

        double_down = ((Button)findViewById(R.id.Double));
        double_down2 = ((Button)findViewById(R.id.Double2));
        setDouble(true);

        setBets(true);
        updateViews();
    }

    private void setDouble(boolean player) {
        if(player) {
            if (blackJackGame.getPlayers().get(currentPlayer).getBet() == 0)
                setClickable(double_down, false);
            else if (blackJackGame.getPlayers().get(currentPlayer).isSplited()) {
                setClickable(double_down, true);
                double_down2.setVisibility(View.VISIBLE);
                double_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        blackJackGame.getPlayers().get(currentPlayer).Hit1();
                        blackJackGame.getPlayers().get(currentPlayer).setBet(blackJackGame.getPlayers().get(currentPlayer).getBet() * 2);
                        setClickable(hit,false);
                        setClickable(double_down,false);
                        playersCards.get(currentPlayer).setNumColumns(blackJackGame.getPlayers().get(currentPlayer).getHand1().getNumCard()*NumOfColumsMultiplyer);
                        updateViews();
                    }
                });
                double_down2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        blackJackGame.getPlayers().get(currentPlayer).Hit2();
                        blackJackGame.getPlayers().get(currentPlayer).setBet(blackJackGame.getPlayers().get(currentPlayer).getBet() * 2);
                        setClickable(split,false);
                        setClickable(double_down2,false);
                        playersCards2.get(currentPlayer).setNumColumns(blackJackGame.getPlayers().get(currentPlayer).getHand2().getNumCard()*NumOfColumsMultiplyer);
                        updateViews();
                    }
                });

            } else {
                setClickable(double_down, true);
                setClickable(double_down2, true);
                double_down2.setVisibility(View.INVISIBLE);
                double_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        blackJackGame.getPlayers().get(currentPlayer).Hit1();
                        blackJackGame.getPlayers().get(currentPlayer).setBet(blackJackGame.getPlayers().get(currentPlayer).getBet() * 2);
                        setClickable(hit,false);
                        setClickable(double_down,false);
                        updateViews();
                    }
                });
            }
        }
        else{
            setClickable(double_down, false);
            setClickable(double_down2, false);
            double_down2.setVisibility(View.INVISIBLE);
        }
    }

    private void setBets(boolean player) {
        final double currentBalance = blackJackGame.getPlayers().get(currentPlayer).getBalance();
        if((currentBalance >= Utility.BET4)&&player){
            bet4.setClickable(true);
            bet4.setText(Utility.BET4+"");
            bet4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    blackJackGame.getPlayers().get(currentPlayer).addBet(Utility.BET4);
                    setDouble(true);
                    setBets(true);
                    updateViews();
                }
            });
        }else{
            bet4.setClickable(false);
            bet4.setText("");
        }
        if((currentBalance >= Utility.BET3)&&player){
            bet3.setClickable(true);
            bet3.setText(Utility.BET3+"");
            bet3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    blackJackGame.getPlayers().get(currentPlayer).addBet(Utility.BET3);
                    setDouble(true);
                    setBets(true);
                    updateViews();
                }
            });
        }else{
            bet3.setClickable(false);
            bet3.setText("");
        }
        if((currentBalance >= Utility.BET2)&&player){
            bet2.setClickable(true);
            bet2.setText(Utility.BET2+"");
            bet2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    blackJackGame.getPlayers().get(currentPlayer).addBet(Utility.BET2);
                    setDouble(true);
                    setBets(true);
                    updateViews();
                }
            });
        }else{
            bet2.setClickable(false);
            bet2.setText("");
        }
        if((currentBalance >= Utility.BET1)&&player){
            bet1.setClickable(true);
            bet1.setText(Utility.BET1+"");
            bet1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    blackJackGame.getPlayers().get(currentPlayer).addBet(Utility.BET1);
                    setDouble(true);
                    setBets(true);
                    updateViews();
                }
            });
        }else{
            bet1.setClickable(false);
            bet1.setText("");
        }
        if(player){
            claer.setVisibility(View.VISIBLE);
            claer.setClickable(true);
            claer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    blackJackGame.getPlayers().get(currentPlayer).clearBet();
                    setDouble(false);
                    updateViews();
                }
            });
        }else {
            claer.setVisibility(View.INVISIBLE);
            claer.setClickable(false);
        }

    }

    private void pause() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setClickable(Button b,boolean clickable){
        b.setClickable(clickable);
        if(clickable)
            b.setTextColor(Color.BLACK);
        else
            b.setTextColor(Color.DKGRAY);

    }

    public boolean canJoin(){
        return join;
    }
    private void newGame() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                winner = true;
                List<Player> winners = blackJackGame.getWinners();
                for(int i = 0; i < playersAdapter.size();i++){
                    if(winners.contains(playersAdapter.get(i).getPalyer())){
                        playersWinLose.get(i).setTextColor(Color.GREEN);
                        playersAdapter.get(i).getPalyer().win();
                    }
                    else{
                        playersWinLose.get(i).setTextColor(Color.RED);
                        playersAdapter.get(i).getPalyer().lose();
                    }
                }
                currentPlayer = 0;
                setClickable(hit, true);
                hit.setText(R.string.NewGame);
                hit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        winner = false;
                        blackJackGame.DealNewGame();
                        setClickable(hit, true);
                        setClickable(stand, true);
                        setClickable(double_down, false);
                        setClickable(double_down2, false);
                        setHitButton();
                        setSpiltButton();
                        for(int i = 0; i < playersWinLose.size(); i++){
                            playersWinLose.get(i).setTextColor(Color.BLACK);
                        }
                        for(GridView player : playersCards){
                            player.setNumColumns(NumOfColums);
                        }
                        join = true;
                        delerPlay = false;
                        setBets(true);
                        setDouble(true);
                        updateViews();
                    }
                });
            }
        });
        setBets(false);
        setDouble(false);
        updateViews();
    }

    private void setHitButton() {
        setClickable(hit,true);
        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBets(false);
                setDouble(false);
                if((blackJackGame.getPlayers().get(currentPlayer).getHand1().GetSumOfHand() < Utility.BJMAX)&&(blackJackGame.getDeck().numOfCards()!= 0)) {
                    blackJackGame.getPlayers().get(currentPlayer).Hit1();
                    playersCards.get(currentPlayer).setNumColumns(blackJackGame.getPlayers().get(currentPlayer).getHand1().getNumCard()*NumOfColumsMultiplyer);
                }
                if((blackJackGame.getDeck().numOfCards() == 0)||(blackJackGame.getPlayers().get(currentPlayer).getHand1().GetSumOfHand() >= Utility.BJMAX)) {
                    setClickable(hit, false);
                }
                updateViews();
            }
        });
    }

    private void setSpiltButton() {
        split.setText(R.string.Split);
        hit.setText(R.string.Hit);
        if(blackJackGame.getPlayers().get(currentPlayer).canSplit()){
            setClickable(split,true);
            split.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    blackJackGame.getPlayers().get(currentPlayer).Split();
                    hit.setText(R.string.Hit1);
                    split.setText(R.string.Hit2);
                    playersCards.get(currentPlayer).setMinimumWidth(playersCards.get(currentPlayer).getWidth()/2);
                    playersCards2.get(currentPlayer).setMinimumWidth(playersCards.get(currentPlayer).getWidth());
                    split.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setBets(false);
                            setDouble(false);
                            if((blackJackGame.getPlayers().get(currentPlayer).getHand2().GetSumOfHand() < Utility.BJMAX)&&(blackJackGame.getDeck().numOfCards()!= 0)) {
                                blackJackGame.getPlayers().get(currentPlayer).Hit2();
                                playersCards2.get(currentPlayer).setNumColumns(blackJackGame.getPlayers().get(currentPlayer).getHand2().getNumCard()*NumOfColumsMultiplyer);
                            }
                            if((blackJackGame.getDeck().numOfCards() == 0)||(blackJackGame.getPlayers().get(currentPlayer).getHand1().GetSumOfHand() >= Utility.BJMAX))
                                setClickable(split,false);
                            updateViews();
                        }
                    });
                /*blackJackGame.DealNewGame();
                CardAdapter clear = new CardAdapter(getApplicationContext(),new Player());*/
                /*for (GridView gridView:playersCards) {
                    if(gridView.getAdapter() == null){
                        gridView.setAdapter(clear);
                    }
                }*/
                    updateViews();
               /*for (GridView gridView:playersCards) {
                    if(gridView.getAdapter().equals(clear)){
                        gridView.setAdapter(null);
                    }
                }*/
                }
            });
        }
        else{
            /*split.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/
            setClickable(split,false);
        }

    }

    public boolean addPlayer(Player player){
        boolean added = blackJackGame.addPlayer(player);
        if(added) {
            CardAdapter cardAdapter = new CardAdapter(getApplicationContext(), player);
            playersAdapter.add(cardAdapter);
            playersCards.get(blackJackGame.getNumofplayers() - 1).setAdapter(cardAdapter);

            CardAdapter cardAdapter2 = new CardAdapter(getApplicationContext(), player);
            cardAdapter2.setHand1(false);
            playersAdapter2.add(cardAdapter2);
            playersCards2.get(blackJackGame.getNumofplayers() - 1).setAdapter(cardAdapter2);
        }
        if(blackJackGame.getPlayers().size() == Utility.MAXNUMOFPLAYERS)
            join = false;
        return added;
    }

    public boolean removePlayer(Player player){
        boolean removed = blackJackGame.removePlayer(player);
       if(removed){
            CardAdapter cardAdapter1 = null;
            for(CardAdapter cardAdapter :playersAdapter)
                if(cardAdapter.getPalyer().equals(player)) {
                    cardAdapter1 = cardAdapter;
                    break;
                }
            playersAdapter.remove(cardAdapter1);
            for(GridView gridView :playersCards) {
                if((gridView.getAdapter() != null)&&(gridView.getAdapter().equals(cardAdapter1)))
                    gridView.setAdapter(null);
            }
            if(!delerPlay)
                join = true;
        }
        return removed;
    }

    public void updateViews() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< blackJackGame.getPlayers().size(); i++){
                    if(!winner)
                        playersWinLose.get(i).setTextColor(Color.BLACK);
                    playersWinLose.get(i).setText("" + blackJackGame.getPlayers().get(i).getBalance());
                    playersWinLose.get(i).setVisibility(View.VISIBLE);
                    if(!winner)
                        playerBets.get(i).setTextColor(Color.BLACK);
                    playerBets.get(i).setText(blackJackGame.getPlayers().get(i).getBet() + " " + blackJackGame.getPlayers().get(i).getHand().GetSumOfHand() + " " + blackJackGame.getPlayers().get(i).getHand().getNumCard());
                    playerBets.get(i).setVisibility(View.VISIBLE);

                }
                for(int i = blackJackGame.getPlayers().size(); i< Utility.MAXNUMOFPLAYERS; i++){
                    playersWinLose.get(i).setVisibility(View.INVISIBLE);
                    playerBets.get(i).setVisibility(View.INVISIBLE);
                }
                if(!delerPlay&&!winner) {
                    playersWinLose.get(currentPlayer).setTextColor(Color.YELLOW);
                    playerBets.get(currentPlayer).setTextColor(Color.YELLOW);
                }
                for(int i = 0; i < blackJackGame.getNumofplayers(); i++) {
                    ((CardAdapter) playersCards.get(i).getAdapter()).notifyDataSetChanged();
                    ((CardAdapter) playersCards2.get(i).getAdapter()).notifyDataSetChanged();
                }
                dealerWinLose.setText(blackJackGame.getDealer().getHand().GetSumOfHand() + " " + blackJackGame.getDealer().getHand().getNumCard());

                dbHelper.clearDatabase(Utility.DBNAME);
                dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
                dbHelper.insertPlayer(new PlayerTableRow(player));

                dealerCards.setNumColumns(blackJackGame.getDealer().getHand1().getNumCard()*NumOfColumsMultiplyer);
                dealerCards2.setNumColumns(blackJackGame.getDealer().getHand2().getNumCard()*NumOfColumsMultiplyer);
                ((CardAdapter) dealerCards.getAdapter()).notifyDataSetChanged();
                ((CardAdapter) dealerCards2.getAdapter()).notifyDataSetChanged();
                ((DeckAdapter) deck.getAdapter()).notifyDataSetChanged();

                setClickable(add,true);
                setClickable(remove,true);
                if(blackJackGame.getPlayers().size() >= Utility.MAXNUMOFPLAYERS){
                    setClickable(add,false);
                }
                if(blackJackGame.getPlayers().size() == 1){
                    setClickable(remove,false);
                }
            }
        });
    }
}
