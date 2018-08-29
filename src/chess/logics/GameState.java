package chess.logics;

import chess.logics.figures.Bishop;
import chess.ui.Tile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    public boolean clickedSomeFigure;

    public GameState() {
        createBoard();
    }

    public List<Figure> figures = new ArrayList<>(); //figury
    public Tile[][] mapTiles = new Tile[Consts.MAP_WIDTH][Consts.MAP_HEIGHT]; //kafelki mapy
    public Button startButton;
    public Pane pane = new Pane();

    public void createBoard() {
        for (int i = 0; i < Consts.MAP_WIDTH; i++) {
            for (int j = 0; j < Consts.MAP_HEIGHT; j++) {
                Tile tile = new Tile(i, j);
                pane.getChildren().add(tile);
                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, mapTileMouseHandler);
                mapTiles[i][j] = tile;
            }
        }
    }

    public void normalColorTiles() {

        clickedSomeFigure = false;

        for (int i = 0; i < Consts.MAP_WIDTH; i++)
            for (int j = 0; j < Consts.MAP_HEIGHT; j++)
                this.mapTiles[i][j].setMapFill();
    }

    public Scene startScreen(Stage window) {
        pane.setPrefSize(Consts.START_BUTTON_WIDTH, Consts.START_BUTTON_HEIGHT);

        Scene startScreen = new Scene(pane, Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT);

        startButton = new Button();
        startButton.setText("START THE GAME");
        startButton.setPrefSize(Consts.START_BUTTON_WIDTH, Consts.START_BUTTON_HEIGHT);
        startButton.setLayoutX(Consts.WINDOW_WIDTH / 2); //fixme lespze centrowanie
        startButton.setLayoutY(Consts.WINDOW_HEIGHT / 2);
        startButton.setOnAction(event -> handleButton(event, window));

//        FIXME To dziala, ale nie potrzebujemy na razie tekstu
//        Text title = new Text("offline chess");
//        title.setFont(Font.font("verdana", FontWeight.BOLD, 40));
//        title.setX(Consts.WINDOW_HEIGHT / 2 - title.getLayoutBounds().getWidth() / 2);
//        title.setY(50);
//
//        pane.getChildren().addAll(title, startButton);


        Bishop bishop_2 = new Bishop(3, 6, Owner.BLACK_PLAYER, this);
        figures.add(bishop_2);

        Bishop bishop = new Bishop(0, 0, Owner.BLACK_PLAYER, this);
        figures.add(bishop);


//        pane.setPrefSize(Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT);
//        EventHandler<MouseEvent> mouseClick = (clickEvent) -> {
//            int x = (int)clickEvent.getX();
//            int y = (int)clickEvent.getY();
//            int i = 0, j = 0;
//        };
//        Scene gameScreen = new Scene(pane, Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT);
//        gameScreen.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClick);

        startScreen.addEventHandler(MouseEvent.MOUSE_CLICKED, rightClickMouseHandler);

        return startScreen;
    }

    public EventHandler<MouseEvent> rightClickMouseHandler = (clickEvent) -> {
        if (clickEvent.getButton().ordinal() == 3) {
            normalColorTiles();
        }
    };

    public EventHandler<MouseEvent> mapTileMouseHandler = (clickEvent) -> {

        int clickX = (int) (clickEvent.getX() / Consts.TILE_SIZE); //x, y w kafelkach (0-8) miejsca w ktore kliknieto
        int clickY = (int) (clickEvent.getY() / Consts.TILE_SIZE);

        Tile t = mapTiles[clickX][clickY];
        System.out.println("CLICK EVENT COORDINATES: Clicked X Y " + clickX + " " + clickY);
        System.out.println("FOUND TILE COORDINATES: " + t.getX() + " " + t.getY());

    };

    private void handleButton(ActionEvent event, Stage window) {
        if (event.getSource() == startButton) {
//            window.setScene(gameScreen());
        }
    }


}