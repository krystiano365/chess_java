package chess;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    boolean pauseMenuShown = false;
    boolean kingKilled = false;

    public GameState() {
        createBoard();
    }

    public ArrayList<Figure> figures = new ArrayList<>(); //figury
    public Tile[][] mapTiles = new Tile[Consts.MAP_WIDTH][Consts.MAP_HEIGHT]; //kafelki mapy
    public List<Point> moves; // possible moves
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

        GameStateUtils.spawnWhites(this, figures);
        GameStateUtils.spawnBlacks(this, figures);

        PauseMenu pauseMenu = new PauseMenu(this);

        if (!kingKilled) {
            startScreen.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    if (pauseMenuShown) {
                        pauseMenu.hideMenu();
                        pauseMenuShown = false;
                    } else {
                        pauseMenu.showMenu();
                        pauseMenuShown = true;
                    }
                }
            });
        }


        return startScreen;
    }

    private void clearHighlights() {
        normalColorTiles();
        if (currentlyClickedFigure != null) {
            moves.clear();
            currentlyClickedFigure = null;
        }

    }

    void moveFigure(int clickX, int clickY){
        currentlyClickedFigure.field.setX(clickX * Consts.TILE_SIZE);
        currentlyClickedFigure.field.setY(clickY * Consts.TILE_SIZE);
        mapTiles[currentlyClickedFigure.currentPosition.x][currentlyClickedFigure.currentPosition.y].figureColour = Owner.NONE;
        currentlyClickedFigure.currentPosition.x = clickX;
        currentlyClickedFigure.currentPosition.y = clickY;
        mapTiles[currentlyClickedFigure.currentPosition.x][currentlyClickedFigure.currentPosition.y].figureColour = currentlyClickedFigure.owner;
        currentPlayer = currentPlayer == Owner.WHITE_PLAYER ? Owner.BLACK_PLAYER : Owner.WHITE_PLAYER;
    }

    public EventHandler<MouseEvent> mapTileMouseHandler = (clickEvent) -> {

        if (clickEvent.getButton() == MouseButton.SECONDARY) {
            clearHighlights();
        }

        int clickX = (int) (clickEvent.getX() / Consts.TILE_SIZE); // clickX/Y from <0;8>
        int clickY = (int) (clickEvent.getY() / Consts.TILE_SIZE);

        boolean moveAllowed = false;

        if (currentlyClickedFigure != null) {  // move if player has already picked a figure to control

            for (Point p : moves) {
                if (p.x == clickX && p.y == clickY) {
                    moveAllowed = true;
                }
            }

            if (moveAllowed) {
                moveFigure(clickX, clickY);
            }

            if (pawnEnPassantPoints != null) {
                handleEnPassantMove(clickX, clickY);
                pawnEnPassantPoints.clear();
            }

            currentlyClickedFigure = null;
            normalColorTiles();
            moves.clear();

        }

    };

    private void handleEnPassantMove(int clickX, int clickY){
        for (Point p : pawnEnPassantPoints) {
            if (p.x == clickX && p.y == clickY) {
                //if white, you can do en passant only up and kill figure below your position
                System.out.println("checking enPassant");
                int en_passant_y = 0;
                if (currentPlayer == Owner.WHITE_PLAYER)
                    en_passant_y = p.y - 1;
                else if (currentPlayer == Owner.BLACK_PLAYER)
                    en_passant_y = p.y + 1;

                for (Figure figure : figures) {
                    if (figure.currentPosition.x == p.x && figure.currentPosition.y == en_passant_y) {
                        figure.removeTileFromView(); // kill figure that is below(W) or behind(B) current position of PAWN
                        break;
                    }
                }
            }
        }
    }

    public Owner getOpponent() {
        if (this.currentPlayer == Owner.WHITE_PLAYER) {
            return Owner.BLACK_PLAYER;
        } else {
            return Owner.WHITE_PLAYER;
        }
    }


}