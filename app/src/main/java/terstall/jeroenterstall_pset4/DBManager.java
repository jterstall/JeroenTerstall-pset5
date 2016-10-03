package terstall.jeroenterstall_pset4;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager
{

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    // Constructor
    public DBManager(Context c)
    {
        context = c;
    }

    // Function to open the database
    public DBManager open() throws SQLException
    {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    // Function to close the database
    public void close()
    {
        dbHelper.close();
    }

    // Function to insert a new to-do item into database
    public void insert(String todo_item)
    {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.TODO_ITEM, todo_item);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    // Function to retrieve the cursor which contains the to-do item and idś
    public Cursor fetch()
    {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.TODO_ITEM };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Function to delete a certain item which has id _id
    public void delete(long _id)
    {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
