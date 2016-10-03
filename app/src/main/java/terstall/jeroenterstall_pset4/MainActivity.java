package terstall.jeroenterstall_pset4;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

// Jeroen Terstall

public class MainActivity extends AppCompatActivity
{
    // Init variables for the listview and database
    private TodoAdapter adapter;
    private DBManager dbManager;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve database
        dbManager = new DBManager(this);
        dbManager.open();

        // Set enter button to go instead of space/lower keyboard
        EditText todo = (EditText) findViewById(R.id.add_edittext);
        todo.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_GO)
                {
                    addToDo(v);
                    return true;
                }
                return false;
            }
        });

        // Populate the to-do list with stored items
        populateTODO();
    }

    @Override
    protected void onDestroy()
    {
        // If app is closed, close the database as well
        dbManager.close();
        super.onDestroy();
    }

    // Function which retrieves the database information and sets the listview with it
    private void populateTODO()
    {
        // Retrieve cursor with database information
        cursor = dbManager.fetch();

        // Init adapter with cursor
        adapter = new TodoAdapter(this, cursor, 0);

        // Retrieve the ListView and set the adapter
        ListView lv = (ListView) findViewById(R.id.todo_list);
        lv.setAdapter(adapter);

        // If the user long clicks on a list item, ask if the user wants to delete the item, if yes delete the item
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id)
            {
                // Ask confirmation for deleting the item with an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.Dialog));
                builder.setMessage("Do you really want to remove this item from your To-Do list?");
                builder.setTitle("Confirmation");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {

                        removeToDo(id);
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        return;
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    // Function to add an item to the to-do list
    public void addToDo(View v)
    {
        // Retrieve what the user filled in
        EditText todo = (EditText) findViewById(R.id.add_edittext);
        String todo_item = todo.getText().toString();
        todo.setText("");
        // Check if something was filled in
        if(todo_item.trim().length() == 0)
        {
            // If not, tell the user so
            Toast toast = Toast.makeText(getApplicationContext(), "Empty To-Do Activity", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            // If so, insert into database and renew the cursor and adapter because the data changed
            dbManager.insert(todo_item);
            cursor = dbManager.fetch();
            adapter.changeCursor(cursor);
            adapter.notifyDataSetChanged();
        }
    }

    // Function to remove an item from the to-do list
    public void removeToDo(long id)
    {
        // Delete the item with the correct id
        dbManager.delete(id);
        // Renew the cursor and adapter because the data set changed
        cursor = dbManager.fetch();
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged();
    }

    public void setStrikeThrough(long id)
    {
        // New value in database for done
        // Strike through
        return;
    }
}
