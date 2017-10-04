import org.osbot.rs07.api.model.NPC;

import java.awt.*;
import java.util.Random;

/**
 * Created by Potatoes on 7/26/2016.
 */
public class AntibanFunctions extends Base{

    private static int getrandom(int lower, int upper) {
        return new Random().nextInt(upper - lower) + lower;
    }


    public static void sleepAntiBan(NPC selectedNpc, NPC nextNpc)
    {
        int randomSelect = getrandom(1,30);
        //rather than just sleeping enter in more antiban code down here..
        if(randomSelect == 1)
        {
            AntibanFunctions.moveMouseVeryRandomly();
        }
        else if(randomSelect == 2)
        {
            AntibanFunctions.moveCameraRandomly();
        }
        else if(nextNpc != null && randomSelect == 3)
        {
            script.getCamera().toEntity(nextNpc);
        }
        else if(selectedNpc != null && randomSelect == 4)
        {
            script.getCamera().toEntity(selectedNpc);
        }
        else if(randomSelect == 5)
        {
            AntibanFunctions.moveMouseVeryRandomly();
            AntibanFunctions.moveCameraRandomly();
        }
    }

    public static int getWeightedRandomMaxNumClicks()
    {
        int firstRand = getrandom(1,100);

        if(firstRand <= 60)
        {
            return 1;
        }
        else if(firstRand <= 90)
        {
            return getrandom(1,2);
        }
        else
        {
            return getrandom(1,6);
        }

    }

    public static void moveMouseVeryRandomly()
    {

        int getRandmovement = getrandom(1,10);

        if(getRandmovement <= 5)
        {
            script.getMouse().moveVerySlightly();
        }
        else if(getRandmovement <= 8)
        {
            script.getMouse().moveSlightly();
        }
        else
        {
            script.getMouse().moveRandomly();
        }


    }

    public static void moveCameraRandomly()
    {
        int getRandmovement = getrandom(1,30);

        if(getRandmovement <= 5)
        {
            moveCameraPitchVerySlightly();
        }
        else if(getRandmovement <= 8)
        {
            moveCameraPitchSlightly();
        }
        else if(getRandmovement <= 10)
        {
            moveCameraPitchRandomly();
        }
        else if(getRandmovement <= 15)
        {
            moveCameraYawVerySlightly();
        }
        else if(getRandmovement <= 18)
        {
            moveCameraYawSlightly();
        }
        else if(getRandmovement <= 20)
        {
            moveCameraYawRandomly();
        }
        else if(getRandmovement <= 25)
        {
            moveCameraPitchVerySlightly();
            moveCameraYawVerySlightly();
        }
        else if(getRandmovement <= 28)
        {
            moveCameraPitchSlightly();
            moveCameraYawSlightly();
        }
        else
        {
            moveCameraPitchRandomly();
            moveCameraYawRandomly();
        }



    }

    public static void moveCameraPitchVerySlightly()
    {
        script.getCamera().movePitch(script.getCamera().getPitchAngle() + getrandom(-3,5));
    }

    public static void moveCameraPitchSlightly()
    {
        script.getCamera().movePitch(script.getCamera().getPitchAngle() + getrandom(-10,15));
    }

    public static void moveCameraPitchRandomly()
    {
        script.getCamera().movePitch(script.getCamera().getPitchAngle() + getrandom(-67,67));
    }

    public static void moveCameraYawVerySlightly()
    {
        script.getCamera().moveYaw(script.getCamera().getYawAngle() + getrandom(-20,20));
    }

    public static void moveCameraYawSlightly()
    {
        script.getCamera().movePitch(script.getCamera().getYawAngle() + getrandom(-50,50));
    }

    public static void moveCameraYawRandomly()
    {
        script.getCamera().movePitch(script.getCamera().getYawAngle() + getrandom(-360,360));
    }
}
