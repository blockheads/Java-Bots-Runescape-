import org.osbot.rs07.api.model.NPC;

/**
 * Created by Josh on 7/23/2016.
 */
public class FindNPCTargetState extends ArmageddonBase implements State
{

    @Override
    public void PostRun()
    {
        nextNpc = null;
    }

    @Override
    public int Run()
    {

        if(CombatHelper.IsValidNPCToAttack(nextNpc))
        {
            selectedNpc = nextNpc;
        }
        else
        {
            selectedNpc = CombatHelper.GetNearestAttackableNPCInArea(targetNpcString,startArea);
        }
        return random(100,200);
    }

    @Override
    public State GetNextState()
    {
        if(!ArmageddonBase.startArea.contains(script.myPlayer()))
        {
            return returnToStartState;
        }

        if(selectedNpc != null && selectedNpc.isOnScreen())
        {
            return killNPCTargetState;
        }

        if(selectedNpc != null)
        {
            return moveToTargetState;
        }

        return this;
    }


}
