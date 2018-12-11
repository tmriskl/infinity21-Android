package com.example.user.infinity21.Logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.infinity21.Logic.PlayerTableRow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 2/25/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BLACKJACK_DATABASE";
    private static final int DB_VERSION = 1;
    /* -------------- Table Columns -----------------*/
    public static final String TABLE_NAME = "PlayerTB";
    public static final String COLUMN_ID = "ID";
    public static final String USERNAME = "USERNAME";
    public static final String WINS = "WINS";
    public static final String LOSSES = "LOSSES";
    public static final String TOTAL_CARDS_VAL = "SUM_HAND";
    public static final String LAST_BET = "LAST_BET";
    public static final String REMAINING_BALANCE = "REMAINING_BALANCE";
    /* ----------------------------------------------*/
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS" + TABLE_NAME;

    private Context context;
    // Value is a string that represents the type of value to be inserted inside the DB.
    private static final HashMap<String, String> SCORE_FIELDS = new HashMap<String, String>() {
        {
            //put(USERNAME, "TEXT");
            put(WINS, "INTEGER");
            put(LOSSES, "INTEGER");
            //put(TOTAL_CARDS_VAL, "INTEGER");
            //put(LAST_BET, "INTEGER");
            put(REMAINING_BALANCE, "INTEGER");
        }
    };

    SQLiteDatabase sqLiteDatabase;


    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, TABLE_NAME, SCORE_FIELDS);          // Create the table in a single, beautiful line.

    }

    // This function does all the dirty work
    public static void createTable(SQLiteDatabase db, String tableName, HashMap<String, String> fields) {
        String command = "CREATE TABLE " + TABLE_NAME
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT";

        for (Map.Entry<String, String> entry : fields.entrySet())
            command = command + ", " + entry.getKey() + " " + entry.getValue();

        command = command + " )";
        db.execSQL(command);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public boolean insertPlayer(PlayerTableRow object) {
        boolean isInserted = false;
        sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(USERNAME, object.getUsername());
        values.put(WINS, object.getWins());
        values.put(LOSSES, object.getLosses());
        //values.put(TOTAL_CARDS_VAL, object.getHand());
        //values.put(LAST_BET, object.getBet());
        values.put(REMAINING_BALANCE, object.getBalance());

        if (sqLiteDatabase.insert(TABLE_NAME, null, values) != -1)
            isInserted = true;

        sqLiteDatabase.close();
        return isInserted;
    }


    /* get Single TableRow by id .*/
    public PlayerTableRow getPlayerTableRow(int id) {

        sqLiteDatabase = getReadableDatabase();
        Cursor cursor =
                sqLiteDatabase.query(TABLE_NAME,
                        new String[]{COLUMN_ID,
                                USERNAME,
                                WINS, LOSSES,
                                TOTAL_CARDS_VAL, LAST_BET, REMAINING_BALANCE}, COLUMN_ID + "=?",
                        new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        //String username = cursor.getString(0);
        int wins = cursor.getInt(cursor.getColumnIndex(WINS));
        int losses = cursor.getInt(cursor.getColumnIndex(LOSSES));
        //int hand = cursor.getInt(3);
        //int bet = cursor.getInt(4);
        int balance = cursor.getInt(cursor.getColumnIndex(REMAINING_BALANCE));
        PlayerTableRow tb = new PlayerTableRow(/*username,*/ wins, losses,/* hand, bet,*/ balance);
        return tb;
    }


    // Getting All TableRows
    public List<PlayerTableRow> getAllPlayerTableRows() {
        List<PlayerTableRow> tableRowList = new LinkedList<PlayerTableRow>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                //String username = cursor.getString(0);
                int wins = cursor.getInt(cursor.getColumnIndex(WINS));
                int losses = cursor.getInt(cursor.getColumnIndex(LOSSES));
                //int hand = cursor.getInt(3);
                //int bet = cursor.getInt(4);
                int balance = cursor.getInt(cursor.getColumnIndex(REMAINING_BALANCE));
                PlayerTableRow tb = new PlayerTableRow(/*username,*/ wins, losses,/* hand, bet, */balance);
                tableRowList.add(tb);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tableRowList;
    }


    /* Update Record - Return number of rows affected */
    public int updateData(String id, PlayerTableRow object) {
        //String username = object.getUsername();
        int wins = object.getWins();
        int losses = object.getLosses();
        //int hand = object.getHand();
        //int bet = object.getBet();
        int balance = object.getBalance();


        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        //contentValues.put(USERNAME, username);
        contentValues.put(WINS, wins);
        contentValues.put(LOSSES, losses);
        //contentValues.put(TOTAL_CARDS_VAL, hand);
        //contentValues.put(LAST_BET, bet);
        contentValues.put(REMAINING_BALANCE, balance);


        return sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
    }

    /* Delete Record - Delete single Score-Record from Table*/
    public void deleteData(String id) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[]{id});
        sqLiteDatabase.close();
    }

    public void clearDatabase(String TABLE_NAME) {
        context.deleteDatabase(TABLE_NAME);
        /*String clearDBQuery = "DELETE * FROM " + TABLE_NAME;
        sqLiteDatabase.execSQL(clearDBQuery);*/
    }


}
