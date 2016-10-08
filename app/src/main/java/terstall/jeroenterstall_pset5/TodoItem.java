package terstall.jeroenterstall_pset5;

import java.io.Serializable;

// Simple class which holds a single to-do item object, functions are self-explanatory
public class TodoItem implements Serializable
{
    private String title;
    private String description;
    private Boolean completed;

    public TodoItem(String title, Boolean completed, String description)
    {
        this.title = title;
        this.completed = completed;
        this.description = description;
    }

    protected void setTitle(String newtitle)
    {
        title = newtitle;
    }

    protected String getTitle()
    {
        return title;
    }

    protected void setCompleted()
    {
        completed = true;
    }

    protected void setNotCompleted()
    {
        completed = false;
    }

    protected boolean getStatus()
    {
        return completed;
    }

    protected void setDescription(String newdescription)
    {
        description = newdescription;
    }

    protected String getDescription()
    {
        return description;
    }
}
