package terstall.jeroenterstall_pset4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TodoListFragment extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceSate)
    {
        View rootView = inflater.inflate(R.layout.todo_list_fragment, parentViewGroup, false);
        return rootView;
    }
}
