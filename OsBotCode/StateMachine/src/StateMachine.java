import org.osbot.rs07.script.Script;

/**
 * Created by Josh on 7/23/2016.
 */
public abstract class StateMachine extends Base
{
    private State previousState;
    private State currentState;

    protected abstract State GetNextState();
    protected abstract State GetInitState();
    protected abstract State GetGlobalState();

    protected State GetCurrentState()
    {
        return currentState;
    }

    public StateMachine()
    {
        previousState = null;
    }

    public int RunStateMachine()
    {
        if(currentState == null)
        {
            currentState = GetInitState();
        }

        //Run the global state first
        if(GetGlobalState().GetNextState() != null)
        {
            currentState = GetGlobalState().GetNextState();
        }

        //run pre
        if(previousState != currentState)
        {
            currentState.PreRun();
        }

        int sleepTime = currentState.Run();
        State nextSate = GetNextState();

        //run post
        if(nextSate != currentState)
        {
            currentState.PostRun();
            previousState = currentState;
        }

        currentState = nextSate;

        return sleepTime;
    }

    public void SetScript(Script script)
    {
        this.script = script;
    }

}
