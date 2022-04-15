
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Controller {

    @FXML
    public Label fpsLabel;
    @FXML
    private TextField xCoordinateText;
    @FXML
    private TextField yCoordinateText;
    @FXML
    private TextField magnifierText;

    @FXML
    private ImageView img;

    private static int dragCount = 0;
    private static int scrollCount = 0;
    private static double pointerXStart;
    private static double pointerYStart;

    private static int frameCounter = 0;

    private static long timeOfLastSecond = System.currentTimeMillis();


    /**
     * Renders and updates the image at program start
     */
    @FXML
    protected void initialize() {
        try {
            setViewMultiThreaded();
        } catch (Exception ignored) {
        }
    }

    /**
     * special case of checkInputForDouble, making sure the magnification Factor is not negative or zero
     */
    public void checkInputMagnifier() {
        try {
            if (Double.parseDouble(magnifierText.getText()) <= 0) {
                magnifierText.setText(String.valueOf(Double.parseDouble(magnifierText.getText()) * (-1)));
            }

        } catch (Exception e) {
            magnifierText.setText("");
            throw new RuntimeException("Input is not a number");
        }
    }

    public void checkInputY() {
        checkInputForDouble(yCoordinateText);
    }

    public void checkInputX(KeyEvent ke) {
        checkInputForDouble(xCoordinateText);
    }

    /**
     * checks the input of a text field for the correct format
     *
     * @param textfield a TextField object containing the text
     */
    public void checkInputForDouble(TextField textfield) {
        if (textfield.getText().equals("+") || textfield.getText().equals("-")) {
            return;
        }
        try {
            Double.parseDouble(textfield.getText());
        } catch (Exception e) {
            textfield.setText("");
            throw new RuntimeException("Input is not a number");
        }
    }

    /**
     * Listener for enter key, updates the image if enter is pressed
     *
     * @param ke a KeyEvent
     * @throws IOException
     */
    public void textEnter(KeyEvent ke) throws IOException {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            setViewMultiThreaded();
        }
    }

    /**
     * Event Listener for scrolling, updates the image depending on level of magnification
     *
     * @param e an event object
     * @throws IOException
     */
    public void scrollZoomSetView(ScrollEvent e) throws IOException {
        double magfac = Double.parseDouble(magnifierText.getText());
        double deltaY = e.getDeltaY();
        if (deltaY != 0.0) {
            deltaY = deltaY / Math.abs(deltaY);
        } else {
            deltaY = 0;
        }
        switch ((int) deltaY) {
            case 1:
                magnifierText.setText(Double.toString(magfac + magfac * 0.05));
                break;
            case -1:
                magnifierText.setText(Double.toString(magfac - magfac * 0.05));
                break;
            case 0:
                break;
        }
        if (scrollCount % 8*Double.parseDouble(magnifierText.getText()) == 0) {
            setViewMultiThreaded();
            scrollCount = 0;
        }
        scrollCount++;
    }


    /**
     * Event Listener for Mouse dragging, updates the global pointer coordinates
     *
     * @param e MouseEvent object
     * @throws IOException
     */
    public void imgMouseDrag(MouseEvent e) throws IOException {

        double deltaX = ((pointerXStart - e.getX()));
        double deltaY = ((pointerYStart - e.getY()));
        ComplexNumber newCentrePoint = MandelbrotModel.PixelToComplexNumber(
                (int) deltaX, (int) deltaY,
                new ComplexNumber(Double.parseDouble(xCoordinateText.getText()),
                        Double.parseDouble(yCoordinateText.getText())), Double.parseDouble(magnifierText.getText()),
                (int) img.getFitHeight(), (int) img.getFitWidth());
        xCoordinateText.setText(Double.toString(newCentrePoint.getReal()));
        yCoordinateText.setText(Double.toString(newCentrePoint.getImaginary()));


        if (dragCount % 4*Double.parseDouble(magnifierText.getText()) == 0) {
            setViewMultiThreaded();
        }
        dragCount++;
        pointerXStart = e.getX();
        pointerYStart = e.getY();
    }

    /**
     * EventListener for MouseEvent, updates the global pointer position
     *
     * @param e
     */
    public void imgClick(MouseEvent e) {
        pointerXStart = e.getX();
        pointerYStart = e.getY();
    }

    /**
     * Calls the Mandelbrot calculation operation and redraws the image.
     *
     * @throws IOException
     */
    public void setViewMultiThreaded() throws IOException {
        double x = Double.parseDouble(xCoordinateText.getText());
        double y = Double.parseDouble(yCoordinateText.getText());
        double magfac = Double.parseDouble(magnifierText.getText());
        int i = (int) (512 + (magfac * 0.0005));
        int width = 512;
        int height = 512;
        MandelbrotModel.make(width, height, i, magfac, new ComplexNumber(x, y));
        redraw();
    }

    public void redraw() {
        img.setImage(SwingFXUtils.toFXImage(MandelbrotModel.getImage().getBufImg(), null));
        frameCounter++;
        long tmpTime = System.currentTimeMillis();
        if (tmpTime >= timeOfLastSecond + 1000) {

            fpsLabel.setText("fps: " + frameCounter/((tmpTime-timeOfLastSecond)/1000));
            frameCounter = 0;
            timeOfLastSecond = System.currentTimeMillis();
        }
    }

    /**
     * Saves the current image as png file with a unique file name
     *
     * @throws IOException
     */
    @FXML
    public void saveImage() throws IOException {
        String s;
        s = "mandelbrot@" + xCoordinateText.getText().substring(0, 5) + ";" + yCoordinateText.getText().substring(0, 5) + ";" +
                magnifierText.getText() + ";" + (int) (512 + Double.parseDouble(magnifierText.getText()) * 0.01);
        MandelbrotModel.getImage().saveAsPNG(s);
    }

}
