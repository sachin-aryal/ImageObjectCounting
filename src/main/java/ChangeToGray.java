import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

/**
 * Created by iam on 11/19/16.
 */
public class ChangeToGray {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("sample.png"));

       BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        for(int i=0;i<image.getHeight();i++){
            for(int j=0;j<image.getWidth();j++){
                Color c = new Color(image.getRGB(j,i));
                int gray = image.getRaster().getSample(j,i,0);
                if(gray!=247) {
                    System.out.println(gray);
                }
                if(gray==240){
                    gray = 255;
                }else{
                    gray = 0;
                }
                grayImage.setRGB(j,i,new Color(gray,gray,gray).getRGB());
            }
            System.out.println("----------------");
        }
/*
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        BufferedImage grayImage = op.filter(image,null);*/

        ImageIO.write(grayImage,"PNG",new File("grayImage.png"));

    }
}
