package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        ImageView mv = new ImageView(new Image("space.jpg"));
//        Group root = new Group();
//        root.getChildren().addAll(mv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/main.fxml"));
        Scene scene = new Scene(loader.load(), 600, 360);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Prestige Paths");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setOnCloseRequest(this::onClose);
        primaryStage.show();
    }

    private void onClose(WindowEvent windowEvent) {
    }

    public static void main(String[] args) {
        // launch window
        launch(args);
    }
}
