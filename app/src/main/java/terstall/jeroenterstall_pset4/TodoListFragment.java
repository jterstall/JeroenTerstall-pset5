package terstall.jeroenterstall_pset4;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TodoListFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.todo_list_fragment, parentViewGroup, false);
        return rootView;
    }
}
