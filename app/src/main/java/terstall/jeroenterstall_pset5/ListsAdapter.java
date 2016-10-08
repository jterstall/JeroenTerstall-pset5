package terstall.jeroenterstall_pset5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


public class ListsAdapter extends BaseAdapter implements ListAdapter
{
    private Context context;
    private TodoManager todomanager;

    // Basic Constructor
    public ListsAdapter(Context context, TodoManager todomanager)
    {
        this.context = context;
        this.todomanager = todomanager;
    }

    @Override
    public int getCount()
    {
        return todomanager.size();
    }

    @Override
    public TodoList getItem(int position)
    {
        return todomanager.getTodoList(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.all_lists_item, null);
        }

        // Set item in list with text
        TextView listItem = (TextView) view.findViewById(R.id.lists_item);
        listItem.setText(todomanager.getTodoList(position).getTitle());
        return view;
    }
}
