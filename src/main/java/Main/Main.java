package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import niti.NajviseZarazenihNit;

import java.util.Objects;

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("pocetniEkran.fxml")));
        primaryStage.setTitle("Covid Portal");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/img/covid.png"));
        primaryStage.show();
    }

    public static Stage getMainStage(){
        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
