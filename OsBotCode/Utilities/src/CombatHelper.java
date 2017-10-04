
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import java.util.ArrayList;


/**
 * Created by Josh on 7/24/2016.
 */
public class CombatHelper extends Base
{
    public static NPC GetNearestAttackableNPC(String target)
    {
        return script.getNpcs().closest((n) -> IsValidNPCToAttack(n) && n.getName().equals(target) && script.getMap().canReach(n));
    }

    public static NPC GetNearestAttackableNPCInArea(String target,Area area)
    {
        return script.getNpcs().closest((n) -> IsValidNPCToAttack(n) && n.getName().equals(target) && script.getMap().canReach(n) && area.contains(n));
    }

    public static boolean IsValidNPCToAttack(NPC targetNPC)
    {
        return !IsNPCDead(targetNPC) && !targetNPC.isUnderAttack() && targetNPC.isAttackable() && !(targetNPC.getCurrentHealth() > 0);
    }

    public static boolean IsNPCDead(NPC targetNPC)
    {
        return targetNPC == null || !targetNPC.exists() || targetNPC.getHealth() <= 0;
    }

    public static boolean IsAttackingTarget(NPC target)
    {
        return target != null && target.isUnderAttack() && target.equals(script.myPlayer().getInteracting());
    }

    /* tracks a npc's position from it's start at run to its end*/
    public static int getCurrentDistanceFromStart(Position Start,Position End){return Start.distance(End);}


    public static NPC findBestNPC(String target)
    {

        //check closest npc and see if there are no players nearby
        //then check closest npc excluding last if that doesn't work
        //then check closest npc excluding last 2 and so on...

        ArrayList<NPC> excludedNPCS = new ArrayList<NPC>();

        for(NPC currentNPC: script.getNpcs().getAll())
        {



        }

        return null;

    }

}
