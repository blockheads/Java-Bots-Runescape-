import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;

/**
 * Created by Potatoes on 7/23/2016.
 */
public class KillingNPCTargetState extends ArmageddonBase implements State
{

    private Timer timer;
    private Timer hoverTimer;
    private int maximumGoblinHoverRange;
    private Position trackingGoblinStartPosition;

    public KillingNPCTargetState()
    {
        this.timer = new Timer(5000);
        this.hoverTimer = new Timer(5000);
        this.maximumGoblinHoverRange = 2;
    }

    @Override
    public void PreRun()
    {
        timer.Reset();

        //nextNpc = null;
    }

    @Override
    public void PostRun()
    {

    }

    @Override
    public int Run()
    {

        //hover timer
        if(hoverTimer != null && resetHoverTimer)
        {
            hoverTimer.Reset();
            resetHoverTimer = false;
        }

        boolean attacking = CombatHelper.IsAttackingTarget(selectedNpc);

        if(CombatHelper.IsNPCDead(selectedNpc))
        {
            isSelectedNpcDead = true;
        }
        else
        {
            isSelectedNpcDead = false;

        }

        if(!timer.IsRunning() && !isSelectedNpcDead && !attacking && !script.myPlayer().isMoving())
        {
            //I am not attacking the target after 5 seconds, what am I doing lol?
            //If I am just standing still, lets just call this NpcDead and find a new one
            isSelectedNpcDead = true;
        }

        //if the npc is moving  and we are tracking then track it

        if(nextNpc != null && nextNpc.getPosition().distance(trackingGoblinStartPosition) >= maximumGoblinHoverRange)
        {
            nextNpc = null;
        }

        if(!isSelectedNpcDead && attacking && hoverTimer.Duration().getNano() > random(10000000,2000000000) && selectedNpc.getHealthPercent() < selectedNPCHealthPercentgen)
        {
            npcHover();
        }
        else
        {
            AntibanFunctions.sleepAntiBan(selectedNpc,nextNpc);
        }

        return random(200,1000);
    }

    @Override
    public State GetNextState() {
        if(isSelectedNpcDead || selectedNpc == null || !script.myPlayer().isInteracting(selectedNpc))
        {
            isSelectedNpcDead = false;
            return npcTargetState;
        }

        return this;
    }

    public void npcHover()
    {
        hoverTimer.Reset();
        //Find the next npc
        NPC newNpc = CombatHelper.GetNearestAttackableNPCInArea(targetNpcString,startArea);
        if(newNpc != nextNpc && (nextNpc == null || CombatHelper.IsNPCDead(nextNpc)))
        {
            nextNpc = newNpc;
            trackingGoblinStartPosition = nextNpc.getPosition();
        }

        if(nextNpc != null)
        {
            //hover it?
            nextNpc.hover();

            //right click antiban
            if(random(1,30) == 1)
                script.getMouse().click(true);
        }
    }



}
