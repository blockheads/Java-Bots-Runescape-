import com.sun.media.jfxmedia.MediaPlayer;
import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Potatoes on 7/25/2016.
 */
public class ImageHelper extends Base {

    public static Image getImage(String url)
    {
        try
        {
            return ImageIO.read(new URL(url));
        }
        catch (IOException e) {}
        return null;
    }

    public static Image getGif(String url){
        try
        {
            return new ImageIcon(new URL(url)).getImage();
        }
        catch (IOException e) {

        }
        return null;


    }

}
