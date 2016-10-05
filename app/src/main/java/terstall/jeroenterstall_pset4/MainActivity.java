package terstall.jeroenterstall_pset4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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

// Jeroen Terstall

public class MainActivity extends AppCompatActivity
{
    // Init variables for the listview and database
    private TodoManager todomanager;
    private ListsAdapter listsadapter;
    private TodoAdapter todoadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todomanager = TodoManager.getInstance();

        todomanager.readTodos(getApplicationContext());

//        List<TodoItem> todo_items = new ArrayList<TodoItem>();
//        TodoList todolist = new TodoList(todo_items, "TEST");
//        todolist.addTodoItem(new TodoItem("1", false, "1"));
//        todomanager.addTodoList(todolist);
//        List<TodoItem> todo_items2 = new ArrayList<TodoItem>();
//        TodoList todolist2 = new TodoList(todo_items2, "TEST2");
//        todolist2.addTodoItem(new TodoItem("2", false, "2"));
//        todomanager.addTodoList(todolist2);
//        List<TodoItem> todo_items3 = new ArrayList<TodoItem>();
//        TodoList todolist3 = new TodoList(todo_items3, "TEST3");
//        todolist3.addTodoItem(new TodoItem("3", false, "3"));
//        todomanager.addTodoList(todolist3);
//        todomanager.writeTodos(getApplicationContext());

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
        initListOverview();
        //TODO store current tab across activity lifecycle
        initTodoOverView(todomanager.getCurrentTab());
    }

    @Override
    protected void onStop()
    {
        todomanager.writeTodos(this);
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        todomanager.writeTodos(this);
        super.onDestroy();
    }

    private void initListOverview()
    {
        ListView lv = (ListView) findViewById(R.id.todo_manager_list);
        listsadapter = new ListsAdapter(getApplicationContext(), todomanager);
        lv.setAdapter(listsadapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                todomanager.setCurrentTab(position);
                initTodoOverView(position);
            }
        });
    }

    // Function which retrieves the database information and sets the listview with it
    private void initTodoOverView(int position)
    {
        ListView lv = (ListView) findViewById(R.id.todo_list);
        // get current todolist
        TodoList todolist = todomanager.getTodoList(position);
        todoadapter = new TodoAdapter(this, todolist);
        lv.setAdapter(todoadapter);

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

                        removeToDo(position);
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
            TodoList todolist = todomanager.getTodoList(todomanager.getCurrentTab());
            //TODO NEW SCREEN FOR ADDING TODO ITEMS
            todolist.addTodoItem(new TodoItem(todo_item, false, "Placeholder"));
            todomanager.updateTodoList(todomanager.getCurrentTab(), todolist);
            todoadapter.notifyDataSetChanged();
            return;
        }
    }

    // Function to remove an item from the to-do list
    public void removeToDo(int position)
    {
        TodoList todolist = todomanager.getTodoList(todomanager.getCurrentTab());
        todolist.removeTodoItem(position);
        todomanager.updateTodoList(todomanager.getCurrentTab(), todolist);
        todoadapter.notifyDataSetChanged();
        return;
    }

    public void setStrikeThrough(long id)
    {
        // New value in database for done
        // Strike through
        return;
    }
}
