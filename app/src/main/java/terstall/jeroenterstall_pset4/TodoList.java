package terstall.jeroenterstall_pset4;

import java.io.Serializable;
import java.util.List;

public class TodoList implements Serializable
{
    private List<TodoItem> todolist;
    private String title;

    public TodoList(List<TodoItem> todolist, String title)
    {
        this.todolist = todolist;
        this.title = title;
    }

    protected TodoItem getTodoItem(int position)
    {
        return todolist.get(position);
    }

    protected void removeTodoItem(int position)
    {
        todolist.remove(position);
    }

    protected void addTodoItem(TodoItem todoitem)
    {
        todolist.add(todoitem);
    }

    protected String getTitle()
    {
        return title;
    }

    protected void setTitle(String newtitle)
    {
        title = newtitle;
    }

    protected int size()
    {
        return todolist.size();
    }

}
