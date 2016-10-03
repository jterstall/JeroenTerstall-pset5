package terstall.jeroenterstall_pset4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{

    // Table Name
    public static final String TABLE_NAME = "TODO_LIST";

    // Table columns
    public static final String TODO_ITEM = "todo_item";
    public static final String _ID = "_id";

    // Database Information
    static final String DB_NAME = "TODOLIST.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TODO_ITEM + " TEXT NOT NULL);";

    // Constructor
    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // Function to create the database, first time sets some explanation items in database
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
        db.execSQL("INSERT INTO " + TABLE_NAME + "(TODO_ITEM) VALUES ('Add items by filling in the form below and clicking the add icon')");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(TODO_ITEM) VALUES ('Finish a to-do item by clicking the check icon...')");
        db.execSQL("INSERT INTO " + TABLE_NAME + "(TODO_ITEM) VALUES ('or remove a to-do item by holding your finger on it')");
    }

    // Function to upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
