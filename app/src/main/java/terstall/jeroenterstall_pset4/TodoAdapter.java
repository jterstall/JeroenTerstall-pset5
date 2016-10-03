package terstall.jeroenterstall_pset4;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


// Jeroen Terstall
// This class overwrites the basic cursor adapter for a listview with a check image and text

public class TodoAdapter extends CursorAdapter
{
    // Basic Constructor
    public TodoAdapter(Context context, Cursor cursor, int flags)
    {
        super(context, cursor, flags);
    }

    // Inflate view with our custom layout
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.todo_list_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        // Retrieve the activity of the context
        final MainActivity activity = (MainActivity) context;

        // Retrieve TextView object and change the text to the to do item
        final TextView todo = (TextView) view.findViewById(R.id.todo_item);
        final String todo_item = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TODO_ITEM));
        todo.setText(todo_item);

        // Retrieve the id of the to-do item
        final long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper._ID));

        // Retrieve check icon and set a click listener which removes the to-do item if clicked
        ImageView check = (ImageView) view.findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity.setStrikeThrough(id);
            }
        });

    }
}
