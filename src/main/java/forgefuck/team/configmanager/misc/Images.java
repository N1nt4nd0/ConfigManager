package forgefuck.team.configmanager.misc;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class Images {
    
    public static final BufferedImage ICON = decodeImage("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAALElEQVR42mNgIAP8JxpQpgGrtficNDg1/F8ARSgaTvyHIipoGA6hRDMNJAEAqVn3gSGwLEcAAAAASUVORK5CYII=");
    
    public static BufferedImage decodeImage(String in) {
        try {
            return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(in)));
        } catch (IOException e) {
            return null;
        }
    }
    
}
