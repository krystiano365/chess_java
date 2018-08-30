package chess;

import chess.logics.Consts;
import chess.logics.GameState;
import javafx.application.Application;
import javafx.stage.Stage;

public class Starter extends Application {

    private static GameState gameState = new GameState();

    public static void main(String... args) {
        System.out.println("In main");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Start");
        primaryStage.setTitle("Krystian-Chess");
        primaryStage.setResizable(false);
        primaryStage.setScene(gameState.startScreen(primaryStage));
        primaryStage.sizeToScene();
        primaryStage.show();

        System.out.println("Exiting start");
    }
}
