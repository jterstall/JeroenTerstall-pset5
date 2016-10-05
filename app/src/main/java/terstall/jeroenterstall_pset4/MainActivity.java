package terstall.jeroenterstall_pset4;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

// Jeroen Terstall
//TODO COMMENTS
//TODO VARIABLE NAMEN VERBETEREN
//TODO FUNCTIE STRUCTUUR VERBETEREN
public class MainActivity extends AppCompatActivity
{
    // Init variables for the listview and database
    private TodoManager todomanager;
    private ListsAdapter listsadapter;
    private TodoAdapter todoadapter;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todomanager = TodoManager.getInstance();
        todomanager.readTodos(getApplicationContext());

        setActionBar();
        initListOverview();
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

    public void onBackPressed()
    {
        if(this.dl.isDrawerOpen(GravityCompat.START))
        {
            this.dl.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void setActionBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, dl, toolbar, 0, 0)
        {
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View view)
            {
                super.onDrawerOpened(view);
            }
        };
        drawerToggle.syncState();
    }

    private void initListOverview()
    {
        ListView lv = (ListView) findViewById(R.id.todo_manager_list);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header, null);
        lv.addHeaderView(header, null, false);
        listsadapter = new ListsAdapter(getApplicationContext(), todomanager);
        lv.setAdapter(listsadapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // Ignore header as a list item so decrease position by 1
                position -= 1;
                todomanager.setCurrentTab(position);
                initTodoOverView(position);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                final int new_position = position - 1;
                // Ask confirmation for deleting the item with an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.Dialog));
                builder.setMessage("Do you really want to remove this list");
                builder.setTitle("Confirmation");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                        removeList(new_position);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add to-do item");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("To-do title");
        builder.setView(input, 30, 0, 30, 0);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String todo_title = input.getText().toString();
                if(todo_title.trim().length() == 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "No title filled in", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    TodoItem todoitem = new TodoItem(todo_title, false, "PLACEHOLDER");
                    TodoList todolist = todomanager.getTodoList(todomanager.getCurrentTab());
                    todolist.addTodoItem(todoitem);
                    todomanager.updateTodoList(todomanager.getCurrentTab(), todolist);
                    todoadapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.show();
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

    public void setStrikeThrough(int position)
    {
        TodoList todolist = todomanager.getTodoList(todomanager.getCurrentTab());
        if(todolist.getTodoItem(position).getStatus())
        {
            todolist.getTodoItem(position).setNotCompleted();
        }
        else
        {
            todolist.getTodoItem(position).setCompleted();
        }
        todoadapter.notifyDataSetChanged();
        return;
    }

    public void addList(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add list");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("List title");
        builder.setView(input, 30, 0, 30, 0);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String list_title = input.getText().toString();
                if(list_title.trim().length() == 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "No title filled in", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    List<TodoItem> todo_items = new ArrayList<TodoItem>();
                    TodoList todolist = new TodoList(todo_items, list_title);
                    todomanager.addTodoList(todolist);
                    listsadapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void removeList(int position)
    {
        todomanager.removeTodoList(position);
        listsadapter.notifyDataSetChanged();
    }
}
