package chess.logics;

import chess.ui.Tile;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

//https://stackoverflow.com/questions/3344816/when-and-why-to-use-abstract-classes-methods
//dziedziczyc mozna zawsze, chyba ze jest keyword >final< przed >class<
//abstract sprawie, ze nie mozna utworzyc instancji tej klasy, np. przez ... = new Figure()
//dziedziczenie jest wykonywane przez >extends<


public abstract class Figure implements MoveValidator {

    public Tile field; //Tile to button odpowiadajacy temu pionkowi
    public Point currentPosition;  //obecny x, y
    public FigureType figureType; //typ figury np. BISHOP
    public Owner owner; //BLACK_PLAYER lub WHITE_PLAYER
    public GameState gameState;

    public Figure(int x, int y, FigureType figureType, GameState gameState, Owner owner){
        this.currentPosition = new Point(x, y);
        this.figureType = figureType;
        this.owner = owner;
        this.gameState = gameState;

        gameState.mapTiles[x][y].figureColour = this.owner;   // określenie właściciela kafelka na którym stoi TA figura

        field = new Tile(currentPosition.x , currentPosition.y);
        field.addEventHandler(MouseEvent.MOUSE_CLICKED, figureMouseEvent); //dodanie obslugi inputu tego klawisza
        this.gameState.pane.getChildren().add(field); //dodanie tego klawisza do jakiejs petli renderujacej (cos w tym stylu, to sprawia ze jest wyswietlane)

        try {
            String imageDir = System.getProperty("user.dir") + "/assets/" + figureType.toString() + "_" + owner.toString() + ".png";
            Image img = new Image(new FileInputStream(imageDir));
            this.field.setFill(new ImagePattern(img));
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku!");
            e.printStackTrace();
        }
    }



    public EventHandler<MouseEvent> figureMouseEvent = (clickEvent) -> {
        System.out.println("Just clicked (this) " + figureType + " " + owner);

        boolean killedThisFigure = false;

        int clickX = (int) (clickEvent.getX() / Consts.TILE_SIZE); //x, y w kafelkach (0-8) miejsca w ktore kliknieto
        int clickY = (int) (clickEvent.getY() / Consts.TILE_SIZE);

        System.out.println("FIGURE COLOUR?: " + gameState.mapTiles[clickX][clickY].figureColour);

        if(gameState.currentlyClickedFigure != null){
            System.out.println("Clicked before (gamesState.currentlyClickedFigure): " + gameState.currentlyClickedFigure.owner);

            if(gameState.currentlyClickedFigure.owner != this.owner) {
                for (Point p : gameState.moves){

                    if (p.x == this.currentPosition.x && p.y == this.currentPosition.y){
                        this.gameState.pane.getChildren().remove(this.field);

                        gameState.currentlyClickedFigure.field.setX(p.x * Consts.TILE_SIZE);
                        gameState.currentlyClickedFigure.field.setY(p.y * Consts.TILE_SIZE);

                        int currentX = gameState.currentlyClickedFigure.currentPosition.x;
                        int currentY = gameState.currentlyClickedFigure.currentPosition.y;
                        gameState.mapTiles[currentX][currentY].figureColour = Owner.NONE;       // setting back Tile's properties
                        currentX = gameState.currentlyClickedFigure.currentPosition.x = p.x;    // changing current position of the figure after move
                        currentY = gameState.currentlyClickedFigure.currentPosition.y = p.y;
                        gameState.mapTiles[currentX][currentY].figureColour = gameState.currentlyClickedFigure.owner;    // setting current Tile's properties

                        killedThisFigure = true;

                        gameState.currentPlayer = gameState.currentPlayer == Owner.WHITE_PLAYER ? Owner.BLACK_PLAYER : Owner.WHITE_PLAYER;
                    }
                }
            }
            gameState.normalColorTiles();
            gameState.moves.clear();

        }

        if(killedThisFigure || (gameState.currentPlayer != this.owner))
            return;

        gameState.currentlyClickedFigure = this;



        Point clickedPoint = new Point(clickX, clickY);
        gameState.moves = getPossibleMoves(clickedPoint);
        for(Point p : gameState.moves) {
            Tile t = gameState.mapTiles[p.x][p.y];
            highlightTile(t);
        }

    };

    private void highlightTile(Tile t){
        if (t.isWhite()) {
            t.setFill(Color.rgb(
                    Consts.BASE_TILE_WHITE_R - Consts.DELTA_TILE_R,
                    Consts.BASE_TILE_WHITE_G - Consts.DELTA_TILE_G,  //  podświetlanie białych kafelków
                    Consts.BASE_TILE_WHITE_B - Consts.DELTA_TILE_B));

        } else {
            t.setFill(Color.rgb(
                    Consts.BASE_TILE_BLACK_R - Consts.DELTA_TILE_R,
                    Consts.BASE_TILE_BLACK_G + Consts.DELTA_TILE_G,  //  podświetlanie czarnych kafelków
                    Consts.BASE_TILE_BLACK_B + Consts.DELTA_TILE_B));
        }
    }


    public boolean validateMoves(int x, int y, List<Point> points) {
        if (gameState.mapTiles[x][y].figureColour == Owner.NONE) {
            Point p = new Point(x, y);
            points.add(p);
        } else if (gameState.mapTiles[x][y].figureColour != owner) {
            Point p = new Point(x, y);
            points.add(p);
            return false;
        } else return false;
        return true;
    }
}
