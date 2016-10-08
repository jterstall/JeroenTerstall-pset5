package terstall.jeroenterstall_pset4;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

// Fragment which handles the inner list items
public class TodoListFragment extends Fragment
{
    // Variables needed
    MainActivity ma;
    ListView lv;
    TodoAdapter todoadapter;
    TodoManager todomanager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.todo_list_fragment, parentViewGroup, false);
        // When created init the listview of the fragment
        lv = (ListView) rootView.findViewById(R.id.todo_list);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // When the main activity is created, also initialize the todolist
        super.onActivityCreated(savedInstanceState);
        initTodoList();
    }

    // Function to initialize the todolist
    public void initTodoList()
    {
        // get current todolist
        todomanager = TodoManager.getInstance();
        TodoList todolist = todomanager.getTodoList(todomanager.getCurrentTab());
        // Set the custom adapter
        todoadapter = new TodoAdapter(ma, todolist);
        lv.setAdapter(todoadapter);
        // If the user long clicks on a list item, ask if the user wants to delete the item, if yes delete the item
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id)
            {
                // Ask confirmation for deleting the item with an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ma, R.style.Dialog));
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
        // Create a new alert dialog which asks for user input for the new to-do item
        AlertDialog.Builder builder = new AlertDialog.Builder(ma);
        builder.setTitle("Add to-do item");
        final EditText input = new EditText(ma);
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
                    Toast toast = Toast.makeText(ma.getBaseContext(), "No title filled in", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    // If the user entered correct input, make the new to-do item
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
        // Get current todolist
        TodoList todolist = todomanager.getTodoList(todomanager.getCurrentTab());
        // remove the item
        todolist.removeTodoItem(position);
        // update the correct list and notify the data set
        todomanager.updateTodoList(todomanager.getCurrentTab(), todolist);
        todoadapter.notifyDataSetChanged();
        return;
    }

    // Sets a to-do item as finished by converting the text to strikethrough
    public void setStrikeThrough(int position)
    {
        // Get current todolist
        TodoList todolist = todomanager.getTodoList(todomanager.getCurrentTab());
        // If the item was already completed...
        if(todolist.getTodoItem(position).getStatus())
        {
            todolist.getTodoItem(position).setNotCompleted();
        }
        // If the item was not completed...
        else
        {
            todolist.getTodoItem(position).setCompleted();
        }
        todoadapter.notifyDataSetChanged();
        return;
    }

    // When fragment is attached create the activity from main activity so functions can be called
    // from the main activity
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            ma = (MainActivity) getActivity();
        }
        catch (ClassCastException e)
        {
            System.out.println(e);
        }
    }
}
