/**
 * Created by Josh on 7/23/2016.
 */
public interface State
{
    /* Called when a state is first switched to from a previous state*/
    default void PreRun(){};

    /* Run the state */
    int Run();

    /* called when a state is exited from a previous state */
    default void PostRun(){};

    /* Get the next state */
    State GetNextState();

    /* Called after the Run Method - don't know if we need this
    public abstract void PostRun();
    */

    default String GetName()
    {
        return this.getClass().getName();
    }

}
