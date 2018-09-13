package chess;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

//https://stackoverflow.com/questions/3344816/when-and-why-to-use-abstract-classes-methods
//dziedziczyc mozna zawsze, chyba ze jest keyword >final< przed >class<
//abstract sprawie, ze nie mozna utworzyc instancji tej klasy, np. przez ... = new Figure()
//dziedziczenie jest wykonywane przez >extends<


public abstract class Figure implements MoveValidator {

    public boolean killed;
    public Tile field; // field of Figure image
    public Point currentPosition;  // current x, y
    public FigureType figureType;
    public Owner owner; //BLACK_PLAYER or WHITE_PLAYER
    public GameState gameState;

    public Figure(int x, int y, FigureType figureType, GameState gameState, Owner owner) {
        this.currentPosition = new Point(x, y);
        this.figureType = figureType;
        this.owner = owner;
        this.gameState = gameState;

        gameState.mapTiles[x][y].figureColour = this.owner;   // defining Figure tile's owner

        field = new Tile(currentPosition.x, currentPosition.y);
        field.addEventHandler(MouseEvent.MOUSE_CLICKED, figureMouseEvent); // adding EventHandler to this FIGURE's image
        this.gameState.pane.getChildren().add(field);

        try {
            String imageDir = System.getProperty("user.dir") + "/assets/" + figureType.toString() + "_" + owner.toString() + ".png";
            Image img = new Image(new FileInputStream(imageDir));
            this.field.setFill(new ImagePattern(img));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }
    }


    public EventHandler<MouseEvent> figureMouseEvent = (clickEvent) -> {
        System.out.println("Just clicked (this) " + this.figureType + " " + this.owner);

        boolean killedThisFigure = false;

        int clickX = (int) (clickEvent.getX() / Consts.TILE_SIZE); // clickX/Y from <0;8>
        int clickY = (int) (clickEvent.getY() / Consts.TILE_SIZE);

        if (gameState.currentlyClickedFigure != null) {
            System.out.println("Current player (gameState.currentlyClickedFigure): " + gameState.currentlyClickedFigure.owner);

            killedThisFigure = checkAndHandleKill();

            gameState.normalColorTiles();
            gameState.moves.clear();
        }

        if (killedThisFigure || (gameState.currentPlayer != this.owner))
            return;

        gameState.currentlyClickedFigure = this;

        Point clickedPoint = new Point(clickX, clickY);
        gameState.moves = getPossibleMoves(clickedPoint);

        //checkKingMoves();

        for (Point p : gameState.moves) {
            Tile t = gameState.mapTiles[p.x][p.y];
            FigureUtils.highlightTile(t, gameState);
        }

    };

    public boolean checkAndHandleKill() {

        boolean killedThisFigure = false;

        if (gameState.currentlyClickedFigure.owner != this.owner) {

            for (Point p : gameState.moves) {

                if (p.x == this.currentPosition.x && p.y == this.currentPosition.y) {

                    removeTileFromView();

                    gameState.currentlyClickedFigure.field.setX(p.x * Consts.TILE_SIZE);
                    gameState.currentlyClickedFigure.field.setY(p.y * Consts.TILE_SIZE);

                    int currentX = gameState.currentlyClickedFigure.currentPosition.x;
                    int currentY = gameState.currentlyClickedFigure.currentPosition.y;

                    gameState.mapTiles[currentX][currentY].figureColour = Owner.NONE;       // resetting Tile's owner to NONE
                    currentX = gameState.currentlyClickedFigure.currentPosition.x = p.x;    // changing current position of the figure after move
                    currentY = gameState.currentlyClickedFigure.currentPosition.y = p.y;
                    gameState.mapTiles[currentX][currentY].figureColour = gameState.currentlyClickedFigure.owner;    // setting current Tile's properties

                    killedThisFigure = true;
                    killed = true;
                    System.out.println(gameState.currentlyClickedFigure.owner + " kills " + this.figureType);

                    if (figureType == FigureType.KING) {
                        gameState.kingKilled = true;
                        String winner = gameState.currentPlayer == Owner.WHITE_PLAYER ? "WHITES" : "BLACKS";
                        GameOverMenu gameover = new GameOverMenu(gameState, winner);
                        gameover.showMenu();
                    }
                    gameState.currentPlayer = gameState.currentPlayer == Owner.WHITE_PLAYER ? Owner.BLACK_PLAYER : Owner.WHITE_PLAYER;

                }
            }
        }
        return killedThisFigure;
    }

    void checkKingMoves() {
        if (figureType == FigureType.KING) {

            Owner opponent = gameState.getOpponent();

            for (Figure f : gameState.figures) {
                if (f.owner == opponent) {
                    for (Point possiblePoint : f.getPossibleMoves(f.currentPosition)) {
                        gameState.moves.removeIf(p -> p.x == possiblePoint.x && possiblePoint.y == p.y);
                    }
                }
            }
        }
    }

    public void removeTileFromView() {
        this.gameState.pane.getChildren().remove(this.field);  // removing figure tile from Layout
        killed = true;
    }


    public boolean validateMove(int x, int y, List<Point> points) {
        if (x < Consts.MAP_WIDTH && x >= 0 && y < Consts.MAP_HEIGHT && y >= 0) {
            if (gameState.mapTiles[x][y].figureColour == Owner.NONE) {
                Point p = new Point(x, y);
                points.add(p);
            } else if (gameState.mapTiles[x][y].figureColour != owner) {
                Point p = new Point(x, y);
                points.add(p);
                return false;
            } else return false;
        }
        return true;
    }
}
