package chess.logics;

import chess.logics.figures.*;
import chess.ui.Tile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    public Owner currentPlayer;
    public Figure currentlyClickedFigure;
    public List<Point> pawnEnPassantPoints = new ArrayList<>();

    public GameState() {
        createBoard();
    }

    public List<Figure> figures = new ArrayList<>(); //figury
    public Tile[][] mapTiles = new Tile[Consts.MAP_WIDTH][Consts.MAP_HEIGHT]; //kafelki mapy
    public List<Point> moves; // możliwe ruchy
    public Button startButton;
    public Pane pane = new Pane();

    public void createBoard() {
        for (int i = 0; i < Consts.MAP_WIDTH; i++) {
            for (int j = 0; j < Consts.MAP_HEIGHT; j++) {
                Tile tile = new Tile(i, j);
                tile.setStroke(Color.BLACK);
                pane.getChildren().add(tile);
                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, mapTileMouseHandler);
                mapTiles[i][j] = tile;
            }
        }
    }

    public void normalColorTiles() {


        for (int i = 0; i < Consts.MAP_WIDTH; i++)
            for (int j = 0; j < Consts.MAP_HEIGHT; j++)
                this.mapTiles[i][j].setDefaultMapFill();
    }

    public Scene startScreen(Stage window) {

        currentPlayer = Owner.WHITE_PLAYER;

        Scene startScreen = new Scene(pane, Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT);

//        startButton = new Button();
//        startButton.setText("START THE GAME");
//        startButton.setPrefSize(Consts.START_BUTTON_WIDTH, Consts.START_BUTTON_HEIGHT);
//        startButton.setLayoutX(Consts.WINDOW_WIDTH / 2); //fixme lespze centrowanie
//        startButton.setLayoutY(Consts.WINDOW_HEIGHT / 2);
//        startButton.setOnAction(event -> handleButton(event, window));
//
//        FIXME To dziala, ale nie potrzebujemy na razie tekstu
//        Text title = new Text("offline chess");
//        title.setFont(Font.font("verdana", FontWeight.BOLD, 40));
//        title.setX(Consts.WINDOW_HEIGHT / 2 - title.getLayoutBounds().getWidth() / 2);
//        title.setY(50);
//
//        pane.getChildren().addAll(title, startButton);


        Bishop bishop_white_1 = new Bishop(2, 7, Owner.WHITE_PLAYER, this);
        figures.add(bishop_white_1);

        Bishop bishop_white_2 = new Bishop(5, 7, Owner.WHITE_PLAYER, this);
        figures.add(bishop_white_2);

        Rook rook_white_1 = new Rook(7, 7, Owner.WHITE_PLAYER, this);
        figures.add(rook_white_1);

        Rook rook_white_2 = new Rook(0, 7, Owner.WHITE_PLAYER, this);
        figures.add(rook_white_2);

        King king_white = new King(4, 7, Owner.WHITE_PLAYER, this);
        figures.add(king_white);

        Queen queen_white = new Queen(3, 7, Owner.WHITE_PLAYER, this);
        figures.add(queen_white);

        for (int x = 0; x < Consts.MAP_WIDTH; x++){
            figures.add(new Pawn(x, 6, Owner.WHITE_PLAYER, this));
        }



        Bishop bishop_black_1 = new Bishop(2, 0, Owner.BLACK_PLAYER, this);
        figures.add(bishop_black_1);

        Bishop bishop_black_2 = new Bishop(5, 0, Owner.BLACK_PLAYER, this);
        figures.add(bishop_black_2);

        Rook rook_black_1 = new Rook(0, 0, Owner.BLACK_PLAYER, this);
        figures.add(rook_black_1);

        Rook rook_black_2 = new Rook(7, 0, Owner.BLACK_PLAYER, this);
        figures.add(rook_black_2);

        King king_black = new King(4, 0, Owner.BLACK_PLAYER, this);
        figures.add(king_black);

        Queen queen_black = new Queen(3, 0, Owner.BLACK_PLAYER, this);
        figures.add(queen_black);

        for (int x = 0; x < Consts.MAP_WIDTH; x++){
            figures.add(new Pawn(x, 1, Owner.BLACK_PLAYER, this));
        }

        return startScreen;
    }

    public EventHandler<MouseEvent> mapTileMouseHandler = (clickEvent) -> {

        if (clickEvent.getButton() == MouseButton.SECONDARY) {
            normalColorTiles();
            if (currentlyClickedFigure != null) {
                moves.clear();
                currentlyClickedFigure = null;
            }
            return;
        }

        int clickX = (int) (clickEvent.getX() / Consts.TILE_SIZE); //x, y w kafelkach (0-8) miejsca w ktore kliknieto
        int clickY = (int) (clickEvent.getY() / Consts.TILE_SIZE);

        Tile t = mapTiles[clickX][clickY];
        System.out.println("CLICK EVENT COORDINATES: Clicked X Y " + clickX + " " + clickY);
        System.out.println("FIGURE COLOUR?: " + t.figureColour);

        boolean moveAllowed = false;

        if (currentlyClickedFigure != null) {  // poruszanie się po uprzednim wybraniu figury

            for (Point p : moves) {

                if (p.x == clickX && p.y == clickY ) {
                    moveAllowed = true;
                }

            }

            if (moveAllowed) {
                currentlyClickedFigure.field.setX(clickX * Consts.TILE_SIZE);
                currentlyClickedFigure.field.setY(clickY * Consts.TILE_SIZE);
                mapTiles[currentlyClickedFigure.currentPosition.x][currentlyClickedFigure.currentPosition.y].figureColour = Owner.NONE;
                currentlyClickedFigure.currentPosition.x = clickX;
                currentlyClickedFigure.currentPosition.y = clickY;
                mapTiles[currentlyClickedFigure.currentPosition.x][currentlyClickedFigure.currentPosition.y].figureColour = currentlyClickedFigure.owner;
                currentPlayer = currentPlayer == Owner.WHITE_PLAYER ? Owner.BLACK_PLAYER : Owner.WHITE_PLAYER;
            }

//            if (pawnEnPassantPoints != null) {
//                for (Point p : pawnEnPassantPoints){
//                    if (p.x == clickX && p.y == clickY) {
//                        for (int i = 0; i < pane.getHeight(); i++){
//                            pane.getChildren(1)
//                        }
//
//                    }
//                }
//
//                pawnEnPassantPoints.clear();
//            }

            currentlyClickedFigure = null;
            normalColorTiles();
            moves.clear();

        }

    };

    public Owner getOpponent(){
        if (this.currentPlayer == Owner.WHITE_PLAYER) {
            return Owner.BLACK_PLAYER;
        } else {
            return Owner.WHITE_PLAYER;
        }
    }


    private void handleButton(ActionEvent event, Stage window) {
        if (event.getSource() == startButton) {
//            window.setScene(gameScreen());
        }
    }


}