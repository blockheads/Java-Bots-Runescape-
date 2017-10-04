import org.osbot.rs07.script.Script;

import java.util.Random;

/**
 * Created by Josh on 7/23/2016.
 */
public abstract class Base
{
    protected static Script script;

    protected int random(int lower, int upper)
    {
        return new Random().nextInt(upper - lower) + lower;
    }
}
