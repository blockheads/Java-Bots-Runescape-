import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

/**
 * Created by Potatoes on 7/26/2016.
 */
public class ArmageddonStartGeneration extends Base{

    private static Position startPosition;
    private static Area startArea;
    private static int width;
    private static int length;

    public static void GenerateStart(){

        startPosition = script.myPlayer().getPosition();

        //for all in one fighter
        //startArea = new Area(startPosition.getX() - width,startPosition.getY() - length,startPosition.getX() + width,startPosition.getY() + length);

        startArea = new Area(3243,3225,3264,3254);

    }

    public static Area getStartArea() {
        return startArea;
    }

    public static void setWidth(int width) {
        ArmageddonStartGeneration.width = width;
    }

    public static void setLength(int length) {
        ArmageddonStartGeneration.length = length;
    }
}
