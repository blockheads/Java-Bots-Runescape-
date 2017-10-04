/**
 * Created by Josh on 7/23/2016.
 */
public class ArmageddonMachine extends StateMachine
{

    public ArmageddonMachine()
    {
        ArmageddonBase.npcTargetState = new FindNPCTargetState();
        ArmageddonBase.killNPCTargetState = new KillNPCTargetState();
        ArmageddonBase.moveToTargetState = new MoveToTargetState();
        ArmageddonBase.killingNPCTargetState = new KillingNPCTargetState();
        ArmageddonBase.returnToStartState = new ReturnToStartState();
        ArmageddonBase.armageddonGlobalState = new ArmageddonGlobalState();
    }

    @Override
    protected State GetNextState()
    {
        return GetCurrentState().GetNextState();
    }

    @Override
    protected State GetInitState()
    {
        return ArmageddonBase.npcTargetState;
    }

    @Override
    protected State GetGlobalState()
    {
        return ArmageddonBase.armageddonGlobalState;
    }
}
