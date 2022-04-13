

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load((getClass().getClassLoader().getResource("gui.fxml")));
        primaryStage.setTitle("Mandelbrot");
        primaryStage.setScene(new Scene(root, 512, 640));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
