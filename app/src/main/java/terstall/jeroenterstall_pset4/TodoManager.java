package terstall.jeroenterstall_pset4;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TodoManager
{
    private static TodoManager instance = null;
    private List<TodoList> todomanager = new ArrayList<TodoList>();
    private String save_file = "lists.data";
    private FileOutputStream file_out;
    private FileInputStream file_in;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int currentTab;

    private TodoManager()
    {

    }

    public static TodoManager getInstance()
    {
        if(instance == null)
        {
            instance = new TodoManager();
        }
        return instance;
    }

    protected void readTodos(Context context)
    {
        try
        {
            file_in = context.openFileInput(save_file);
            in = new ObjectInputStream(file_in);
            todomanager.clear();
            todomanager = (List<TodoList>) in.readObject();
            file_in.close();
            in.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    protected boolean writeTodos(Context context)
    {
        if(todomanager.size() == 0)
        {
            System.out.println("Nothing to write");
            return false;
        }
        else
        {
            try
            {
                file_out = context.openFileOutput(save_file, Context.MODE_PRIVATE);
                out = new ObjectOutputStream(file_out);
                out.writeObject(todomanager);
                file_out.close();
                out.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return true;
        }
    }

    protected TodoList getTodoList(int position)
    {
        if(todomanager.size() > 0 && position < todomanager.size())
        {
            return todomanager.get(position);
        }
        return null;
    }

    protected boolean addTodoList(TodoList todolist)
    {
        for(TodoList list: todomanager)
        {
            if(todolist.getTitle().equals(list.getTitle()))
            {
                return false;
            }
        }
        todomanager.add(todolist);
        return true;
    }

    protected boolean updateTodoList(int position, TodoList todolist)
    {
        if(position < todomanager.size() && todomanager.size() > 0)
        {
            todomanager.set(position, todolist);
            return true;
        }
        return false;
    }

    protected int size()
    {
        return todomanager.size();
    }

    protected int getCurrentTab()
    {
        return currentTab;
    }

    protected void setCurrentTab(int tab)
    {
        currentTab = tab;
    }

}
