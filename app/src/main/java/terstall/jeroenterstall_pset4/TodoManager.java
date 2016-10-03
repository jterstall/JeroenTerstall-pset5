package terstall.jeroenterstall_pset4;

import java.util.ArrayList;
import java.util.List;

public class TodoManager
{
    private static TodoManager instance = null;
    private List<TodoList> todomanager = new ArrayList<TodoList>();

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

    protected List<TodoList> readTodos()
    {
        return todomanager;
    }

    protected void writeTodos(TodoList todolist)
    {
        todomanager.add(todolist);
    }

}
