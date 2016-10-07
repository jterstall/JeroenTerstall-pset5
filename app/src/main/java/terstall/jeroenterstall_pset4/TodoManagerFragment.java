package terstall.jeroenterstall_pset4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TodoManagerFragment extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.todo_manager_fragment, parentViewGroup, false);
        return rootView;
    }
}
