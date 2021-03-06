package terstall.jeroenterstall_pset5;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;


public class TodoAdapter extends BaseAdapter implements ListAdapter
{
    private Context context;
    private TodoList todolist;

    // Basic Constructor
    public TodoAdapter(Context context, TodoList todolist)
    {
        this.context = context;
        this.todolist = todolist;
    }

    @Override
    public int getCount()
    {
        return todolist.size();
    }

    @Override
    public TodoItem getItem(int position)
    {
        return todolist.getTodoItem(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.todo_list_item, null);
        }
        // Retrieve the activity of the context
        final MainActivity activity = (MainActivity) context;

        // Retrieve TextView object and change the text to the to do item
        final TextView todo = (TextView) view.findViewById(R.id.todo_item);

        // Set text for to-do items
        final String todo_item = todolist.getTodoItem(position).getTitle();
        todo.setText(todo_item);

        // Set strikethrough of text based on if it is completed or not
        if(todolist.getTodoItem(position).getStatus())
        {
            todo.setPaintFlags(todo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else
        {
            todo.setPaintFlags(todo.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // Retrieve check icon and set a click listener which strikethroughs the to-do item if clicked
        ImageView check = (ImageView) view.findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity.setStrikeThrough(position);
            }
        });
        return view;
    }
}
