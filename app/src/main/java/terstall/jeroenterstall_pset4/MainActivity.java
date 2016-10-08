package terstall.jeroenterstall_pset4;


import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;

// Jeroen Terstall
// Pset 5 - Many Lists
//TODO VARIABLE NAMEN VERBETEREN
//TODO FUNCTIE STRUCTUUR VERBETEREN
public class MainActivity extends AppCompatActivity
{
    // Init variables
    protected TodoManager todomanager;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private DrawerLayout dl;
    private TodoListFragment todolistfragment;
    private TodoManagerFragment todomanagerfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Read in current lists
        todomanager = TodoManager.getInstance();
        todomanager.readTodos(getApplicationContext());

        // Fragments used
        todolistfragment = (TodoListFragment) getSupportFragmentManager().findFragmentById(R.id.todolistfragment);
        todomanagerfragment = (TodoManagerFragment) getSupportFragmentManager().findFragmentById(R.id.todomanagerfragment);

        // Set the toolbar
        setActionBar();
    }

    @Override
    protected void onStop()
    {
        // If app is exited write lists
        todomanager.writeTodos(this);
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        // If app is destroyed write lists
        todomanager.writeTodos(this);
        super.onDestroy();
    }

    // If back button is pressed exit navigation drawer
    @Override
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

    // Set toolbar with hamburger icon for the navigation drawer
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

    // Function which retrieves the database information and sets the listview with it
    public void initTodoList()
    {
        todolistfragment.initTodoList();
    }

    // Function to add an item to the to-do list
    public void addToDo(View v)
    {
        todolistfragment.addToDo(v);
    }

    // Mark item as completed
    public void setStrikeThrough(int position)
    {
        todolistfragment.setStrikeThrough(position);
    }

    // Add a new list
    public void addList(View v)
    {
        todomanagerfragment.addList(v);
    }
}
