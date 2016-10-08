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

import java.util.ArrayList;
import java.util.List;

public class TodoManagerFragment extends Fragment
{
    MainActivity ma;
    ListView lv;
    ListsAdapter listsadapter;
    TodoManager todomanager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.todo_manager_fragment, parentViewGroup, false);
        lv = (ListView) rootView.findViewById(R.id.todo_manager_list);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater(savedInstanceState);
        todomanager = TodoManager.getInstance();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header, null);
        lv.addHeaderView(header, null, false);
        listsadapter = new ListsAdapter(getActivity().getBaseContext(), todomanager);
        lv.setAdapter(listsadapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // Ignore header as a list item so decrease position by 1
                position -= 1;
                todomanager.setCurrentTab(position);
                ma.initTodoOverView(position);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                final int new_position = position - 1;
                // Ask confirmation for deleting the item with an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Dialog));
                builder.setMessage("Do you really want to remove this list");
                builder.setTitle("Confirmation");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                        todomanager.removeTodoList(new_position);
                        listsadapter.notifyDataSetChanged();
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

    public void addList(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ma);
        builder.setTitle("Add list");
        final EditText input = new EditText(ma);
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
                    Toast toast = Toast.makeText(ma.getBaseContext(), "No title filled in", Toast.LENGTH_SHORT);
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
}
