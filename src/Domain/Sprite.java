package Domain;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    
    private BufferedImage image;

    public Sprite(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        make();
        return image;
    }

    private void make(){
        int[] pixels = {
            0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF, 0xFF000000,
            0xFF000000, 0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF,
            0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF, 0xFF000000,
            0xFF000000, 0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF
        };
        
        // Create a new BufferedImage from the pixel array with a size of 4x4 pixels
        image = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 4, 4, pixels, 0, 4);
    }
}
