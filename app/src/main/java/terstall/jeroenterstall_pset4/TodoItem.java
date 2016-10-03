package terstall.jeroenterstall_pset4;

public class TodoItem
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
