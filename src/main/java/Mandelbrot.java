
import java.awt.*;
import java.io.IOException;
import java.util.stream.IntStream;

//this should be a singleton
public class Mandelbrot {
    private static final int x_ratio = 2;
    private static final int y_ratio = 1;
    private static final ComplexNumber z0 = new ComplexNumber(0,0);
    private static Image image = new Image(512,512);

    public static Image getImage(){
        return image;
    }

    public static void setDimensions(int width, int height){
        image =new Image(width,height);
    }

    /**
     * checks whether a complex number is within the mandelbrot set given a number of iterations
     * @param c ComplexNumber object representing the complex number
     * @param iterations the maximum number of iterations to try
     * @return the RGB value of the complex number, either white if it is in the set or another color depending on how many iterarions were needed
     */
    public static Color isInSetWithRGB(ComplexNumber c, int iterations){
        ComplexNumber z = z0;
        double zrttp = z.r*z.r;
        double zrittp = z.ri*z.ri;
        for (int i=0;i<iterations;i++){
            if (zrttp+zrittp>=4.0){
                return new Color(((i+i%32)%256),((i+i%64)%256),((i+i%128)%256));
            }
            z=z.multiply(z).add(c);
            zrttp=z.r*z.r;
            zrittp = z.ri*z.ri;
        }
        return new Color(255,255,255);
    }


    //For the mouse dragging in the Controller Class, finds Complex Number that corresponds to a certain pixel in the pane

    /**
     * Calculates the complex number corresponding to a given pixel given a level of magnification
     * @param x column of the pixel
     * @param y row of the pixel
     * @param centrePoint the complex number in the middle of the image
     * @param magnifier the magnification factor
     * @param height the height of the image in pixels
     * @param width the width of the image in pixels
     * @return the complex number the pixel is corresponding to
     */
    public static ComplexNumber PixelToComplexNumber(int x, int y, ComplexNumber centrePoint, double magnifier, int height, int width){
        return new ComplexNumber(
                centrePoint.r+ x * (x_ratio/magnifier)/width,
                centrePoint.ri-(y * (y_ratio/magnifier)/height)*2);
    }

    /**
     * computes a graphical representation of the mandelbrot set
     * @param centrePoint the complex number in the centre of the image
     * @param iterations the maximum number of iterations to try
     * @param m the magnification factor
     * @param image an Image object being manipulated
     */
    public static void createMandelbrotImg(ComplexNumber centrePoint, int iterations, double m, Image image) {
        IntStream.range(0, image.x).parallel().forEach((int i)->
            IntStream.range(0, image.y).parallel().forEach((int j)->
                    image.set(i,j,isInSetWithRGB(new ComplexNumber(
                            centrePoint.r  -(x_ratio/m)/ image.x + i * (x_ratio/m)/ image.x,
                            centrePoint.ri + 2*(y_ratio/m)/ image.y-(j * (y_ratio/m)/ image.y)*2),
                            iterations)
        )));
    }

    /**
     * computes a graphical representation of the mandelbrot set
     * @param width the width of the image in pixels
     * @param height the height of the image in pixels
     * @param i the maximum number of iterations to try
     * @param m the magnification factor
     * @param centrePoint the complex number in the centre of the image
     * @throws IOException
     */
    public static void make(int width, int height, int i, double m, ComplexNumber centrePoint) throws IOException {
        createMandelbrotImg(new ComplexNumber(
                centrePoint.r-(x_ratio/m)/2,
                centrePoint.ri+2*(y_ratio/m)/2
        ),i,m, image);
    }

}
