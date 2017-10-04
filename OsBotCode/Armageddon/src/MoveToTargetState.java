import org.osbot.rs07.api.model.NPC;

/**
 * Created by Potatoes on 7/23/2016.
 */
public class MoveToTargetState extends ArmageddonBase implements State
{

    private int npcRadius;

    public MoveToTargetState(){
        npcRadius = 6;
    }

    @Override
    public int Run()
    {
        script.getWalking().webWalk(selectedNpc.getArea(npcRadius));
        return random(100,200);
    }

    @Override
    public State GetNextState()
    {
        if(InSelectedNPCArea())
        {
            return killNPCTargetState;
        }

        if(CombatHelper.IsValidNPCToAttack(selectedNpc))
        {
            return this;
        }

        return npcTargetState;
    }


    public boolean InSelectedNPCArea(){ return selectedNpc.getArea(npcRadius).contains(script.myPlayer()); }

}
