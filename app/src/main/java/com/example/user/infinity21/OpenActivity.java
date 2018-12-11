package com.example.user.infinity21;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.infinity21.Logic.Bluetooth;
import com.example.user.infinity21.Logic.DBHelper;
import com.example.user.infinity21.Logic.Player;
import com.example.user.infinity21.Logic.PlayerTableRow;
import com.example.user.infinity21.Logic.Utility;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class OpenActivity extends AppCompatActivity {




    private Button host;
    private Button join;
    private Button delete;
    private Button back;

    private Intent intent;

    private DBHelper dbHelper;

    private Player player;

    private TextView balance;
    private TextView wins;
    private TextView losses;



    private Bluetooth bluetooth;
    private BluetoothDevice device;
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals((BluetoothDevice.ACTION_BOND_STATE_CHANGED))){
                BluetoothDevice tmpDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(tmpDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    device = tmpDevice;
                }
            }
        }
    };
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        bluetooth.startClient(device,uuid);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
        //dbHelper.insertScore(new TableRow(winnerName.getText().toString(), Integer.valueOf(score), winnerlocation.getText().toString()), difString);
        List<PlayerTableRow> players = dbHelper.getAllPlayerTableRows();
        if(players.isEmpty()){
            player = new Player();
            dbHelper.clearDatabase(Utility.DBNAME);
            dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
            dbHelper.insertPlayer(new PlayerTableRow(player));
        }else{
            player = new Player(players.get(0));
        }
        balance = (TextView)findViewById(R.id.Balance);
        wins = (TextView)findViewById(R.id.Wins);
        losses = (TextView)findViewById(R.id.Losses);

        balance.setText(" " + player.getBalance());
        wins.setText(" " + player.getWins());
        losses.setText(" " + player.getLosses());

        intent = new Intent(this, GameActivity.class);
        //intent.putExtra(Utility.DIFFICULTYTAG, dif.toString());
        //start.start();
        host = (Button)findViewById(R.id.Host);
        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                List<PlayerTableRow> players = dbHelper.getAllPlayerTableRows();
                if(players.isEmpty()){
                    player = new Player();
                    dbHelper.clearDatabase(Utility.DBNAME);
                    dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
                    dbHelper.insertPlayer(new PlayerTableRow(player));
                }else{
                    player = new Player(players.get(0));
                }
                balance.setText(" " + player.getBalance());
                wins.setText(" " + player.getWins());
                losses.setText(" " + player.getLosses());
            }
        });
        delete = (Button)findViewById(R.id.Delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.clearDatabase(Utility.DBNAME);
                dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
                dbHelper.insertPlayer(new PlayerTableRow(new Player()));
                List<PlayerTableRow> players = dbHelper.getAllPlayerTableRows();
                if(players.isEmpty()){
                    player = new Player();
                    dbHelper.clearDatabase(Utility.DBNAME);
                    dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
                    dbHelper.insertPlayer(new PlayerTableRow(player));
                }else{
                    player = new Player(players.get(0));
                }
                balance.setText(" " + player.getBalance());
                wins.setText(" " + player.getWins());
                losses.setText(" " + player.getLosses());
            }
        });
        back = (Button)findViewById(R.id.CardBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.setBack(Utility.getBack()+1);
                back.setBackgroundResource(Utility.backs[Utility.getBack()]);
                delete.setBackgroundResource(Utility.backs[Utility.getBack()]);
                join.setBackgroundResource(Utility.backs[Utility.getBack()]);
                host.setBackgroundResource(Utility.backs[Utility.getBack()]);
            }
        });
        join = (Button)findViewById(R.id.Join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConnection();
                byte[] bytes = "string".getBytes(Charset.defaultCharset());
                bluetooth.write(bytes);
            }
        });

    }

    public void startConnection(){
        startBTConnection(device,Utility.MY_UUID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //battle.start();
        List<PlayerTableRow> players = dbHelper.getAllPlayerTableRows();
        if(players.isEmpty()){
            player = new Player();
            dbHelper.clearDatabase(Utility.DBNAME);
            dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
            dbHelper.insertPlayer(new PlayerTableRow(player));
        }else{
            player = new Player(players.get(0));
        }
        balance.setText(" " + player.getBalance());
        wins.setText(" " + player.getWins());
        losses.setText(" " + player.getLosses());

    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // battle.start();
        //progressBar.setVisibility(ProgressBar.INVISIBLE);
        dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
        List<PlayerTableRow> players = dbHelper.getAllPlayerTableRows();
        if(players.isEmpty()){
            player = new Player();
            dbHelper.clearDatabase(Utility.DBNAME);
            dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
            dbHelper.insertPlayer(new PlayerTableRow(player));
        }else{
            player = new Player(players.get(0));
        }
        balance.setText(" " + player.getBalance());
        wins.setText(" " + player.getWins());
        losses.setText(" " + player.getLosses());

    }

    @Override
    protected void onStop() {
        super.onStop();
        //battle.stop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //battle.start();
        //progressBar.setVisibility(ProgressBar.INVISIBLE);
        List<PlayerTableRow> players = dbHelper.getAllPlayerTableRows();
        if(players.isEmpty()){
            player = new Player();
            dbHelper.clearDatabase(Utility.DBNAME);
            dbHelper = new DBHelper(getApplicationContext(), Utility.DBNAME, null,Utility.DBVER);
            dbHelper.insertPlayer(new PlayerTableRow(player));
        }else{
            player = new Player(players.get(0));
        }
        balance.setText(" " + player.getBalance());
        wins.setText(" " + player.getWins());
        losses.setText(" " + player.getLosses());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //battle.pause();
    }
}
