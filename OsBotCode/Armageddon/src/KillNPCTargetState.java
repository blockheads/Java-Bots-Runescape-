/**
 * Created by Josh on 7/23/2016.
 */
public class KillNPCTargetState extends ArmageddonBase implements State
{
    private Timer attackTimer;
    private int numClicks;
    private int maxnumclicks;
    private boolean escapeState;

    public KillNPCTargetState()
    {
        attackTimer = new Timer(2500);
    }

    @Override
    public void PreRun()
    {
        numClicks = 0;
        maxnumclicks = AntibanFunctions.getWeightedRandomMaxNumClicks();
        escapeState = false;
        attackTimer.Reset();
    }

    @Override
    public int Run()
    {
        //for the timer that hovers over another npc
        resetHoverTimer = true;
        selectedNPCHealthPercentgen = random(3,6);

        //failsafe to attack what's attacking you..
        if(script.myPlayer().isUnderAttack())
        {
            selectedNpc = script.getNpcs().closest((n) -> n.isInteracting(script.myPlayer()));
        }

        if(script.myPlayer().isMoving() || numClicks == maxnumclicks)
        {
            //wait while player is moving to target
            AntibanFunctions.sleepAntiBan(selectedNpc,null);
            attackTimer.Reset();
            return random(100, 1000);
        }

        if(CombatHelper.IsValidNPCToAttack(selectedNpc))
        {
            if(selectedNpc.interact("Attack"))
            {
                beginAttack = true;
                attackTimer.Reset();
                numClicks++;
            }
            else
            {
                beginAttack = false;
            }
        }

        if(!attackTimer.IsRunning() && !script.myPlayer().isMoving())
        {
            escapeState = true;
        }

        return random(100, 1000);
    }

    @Override
    public State GetNextState()
    {
        if(CombatHelper.IsAttackingTarget(selectedNpc))
        {
            return killingNPCTargetState;
        }

        if(!CombatHelper.IsValidNPCToAttack(selectedNpc) || escapeState)
        {
            return npcTargetState;
        }

        return this;
    }
}
