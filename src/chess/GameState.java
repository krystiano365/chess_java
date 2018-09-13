package chess;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    boolean kingKilled = !true;

    public GameState() {
        createBoard();
    }

    public ArrayList<Figure> figures = new ArrayList<>(); //figury
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

    private void clearHighlights(){
        normalColorTiles();
        if (currentlyClickedFigure != null) {
            moves.clear();
            currentlyClickedFigure = null;
        }
        return;

    }

    public EventHandler<MouseEvent> mapTileMouseHandler = (clickEvent) -> {

        if (clickEvent.getButton() == MouseButton.SECONDARY) {
            clearHighlights();
        }

        int clickX = (int) (clickEvent.getX() / Consts.TILE_SIZE); //x, y w kafelkach (0-8) miejsca w ktore kliknieto
        int clickY = (int) (clickEvent.getY() / Consts.TILE_SIZE);

        boolean moveAllowed = false;

        if (currentlyClickedFigure != null) {  // poruszanie się po uprzednim wybraniu figury

            for (Point p : moves) {
                if (p.x == clickX && p.y == clickY) {
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

            if (pawnEnPassantPoints != null) {
                for (Point p : pawnEnPassantPoints) {
                    if (p.x == clickX && p.y == clickY) {
                        //if white, you can en passant only down
                        System.out.println("checking enPassant");
                        int en_passant_y = 0;
                        if (currentPlayer == Owner.WHITE_PLAYER)
                            en_passant_y = p.y - 1;
                        else if (currentPlayer == Owner.BLACK_PLAYER)
                            en_passant_y = p.y + 1;

                        for (Figure figure : figures) {
                            if (figure.currentPosition.x == p.x && figure.currentPosition.y == en_passant_y) {
                                figure.removeTileFromView();
                                break;
                            }
                        }

                    }
                }
                pawnEnPassantPoints.clear();
            }

            currentlyClickedFigure = null;
            normalColorTiles();
            moves.clear();

        }

    };

    public Owner getOpponent() {
        if (this.currentPlayer == Owner.WHITE_PLAYER) {
            return Owner.BLACK_PLAYER;
        } else {
            return Owner.WHITE_PLAYER;
        }
    }


}