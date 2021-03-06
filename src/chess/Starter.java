package chess;

import javafx.application.Application;
import javafx.stage.Stage;

public class Starter extends Application {

    public static void main(String... args) {
        System.out.println("In main");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        GameState gameState = new GameState();
        System.out.println("Start");
        primaryStage.setTitle("Krystian-Chess");
        primaryStage.setResizable(false);
        primaryStage.setScene(gameState.startScreen(primaryStage));
        primaryStage.sizeToScene();
        primaryStage.show();

        System.out.println("Exiting start");
    }
}
