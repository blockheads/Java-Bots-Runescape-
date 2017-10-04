import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;

/**
 * Created by Potatoes on 7/24/2016.
 */
public abstract class ArmageddonBase extends Base
{
    public static FindNPCTargetState npcTargetState;
    public static KillNPCTargetState killNPCTargetState;
    public static MoveToTargetState moveToTargetState;
    public static KillingNPCTargetState killingNPCTargetState;
    public static ReturnToStartState returnToStartState;
    public static ArmageddonGlobalState armageddonGlobalState;

    protected static NPC selectedNpc;
    protected static NPC nextNpc;
    protected static String targetNpcString;
    protected static boolean isSelectedNpcDead;
    protected static boolean beginAttack;
    protected static boolean resetHoverTimer;
    protected static int selectedNPCHealthPercentgen;
    protected static Area startArea;

    public ArmageddonBase()
    {
        selectedNpc = null;
        targetNpcString = "Goblin";
        isSelectedNpcDead = false;
        beginAttack = false;

    }

    public static void setStartArea(Area startArea) {
        ArmageddonBase.startArea = startArea;
    }
}

