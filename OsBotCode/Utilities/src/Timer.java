import java.time.Duration;
import java.time.Instant;

/**
 * Created by Josh on 7/24/2016.
 */
public class Timer
{
    private Instant start;
    private long length;

    public Timer(){}

    public Timer(long milliseconds)
    {
        this.length = milliseconds;
        Reset();
    }

    public void Reset()
    {
        start = Instant.now();
    }

    public boolean IsRunning()
    {
        return (Duration().getSeconds()*1000) < length;
    }
    public Duration Duration() {
        return Duration.between(start, Instant.now());
    }

}
