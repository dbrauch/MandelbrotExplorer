

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class representing an image of the mandelbrot set
 */
public class Image {
    public static BufferedImage mandelbrot_image;
    int x;
    int y;

    public Image(int x_input, int y_input){
        x = x_input;
        y = y_input;
        mandelbrot_image = new BufferedImage(x,y, BufferedImage.TYPE_INT_RGB);
        for (int i=0;i<x;i++){
            for (int j=0;j<y;j++){
                this.set(i,j,Color.white);
            }
        }
    }

    /**
     * sets an rgb value of a pixel
     * @param col column of the pixel
     * @param row row of the pixel
     * @param color Color object containing RGB values for the pixel
     */
    public void set(int col, int row, Color color){
        //TODO check if col and row are in raster and if color exists
        int rgb= color.getRGB();
        mandelbrot_image.setRGB(col,row,rgb);
    }

    /**
     * Saves the current image as png
     */
    public void saveAsPNG() {
        try{
        File file = new File("mandelbrot.png");
        ImageIO.write(mandelbrot_image, "png", file);
        }
        catch (IOException e){
            return;
        }
    }

    /**
     * saves the current image as png given a file name
     * @param s String representing the file name
     * @throws IOException
     */
    public void saveAsPNG(String s) throws IOException {
        File file = new File(s+".png");
        ImageIO.write(mandelbrot_image,"png",file);
    }

    public BufferedImage getBufImg(){
        return mandelbrot_image;
    }

}
