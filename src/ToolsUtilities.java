import java.util.HashMap;
import javax.swing.Action;
import javax.swing.text.JTextComponent;

/*
 * Class to handle the note area functionality.
 */
public class ToolsUtilities
{
    HashMap<Object,Action> actions;
    
    
    /*
     * The ToolsUtilities Constructor.
     */
    ToolsUtilities(JTextComponent component)
    {
        this.actions = createActionTable(component);
    }
    

    
private HashMap<Object, Action> createActionTable(JTextComponent textComponent)
{   
    actions = new HashMap<Object, Action>();
    
    Action[] actionsArray = textComponent.getActions();
    
    for (int i = 0; i < actionsArray.length; i++)
    {
        Action a = actionsArray[i];
        actions.put(a.getValue(Action.NAME), a);
    }
        
    return actions;
}



    /*
     * Returns a specific action
     */
    public Action getActionByName(String name)
    {
        return (Action)(actions.get(name));
    }
}
