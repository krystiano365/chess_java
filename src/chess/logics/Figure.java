package chess.logics;

import chess.ui.Tile;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
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
        this.introduceYourself();
        this.owner = owner;
        this.gameState = gameState;
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


    protected void introduceYourself() {
        System.out.println("My figure type is: " + figureType + " and my XY is "
                + currentPosition.x + " " + currentPosition.y);
    }

    public EventHandler<MouseEvent> moveFigureEvent = (clickEvent) -> {
        int clickX = (int) (clickEvent.getX() / Consts.TILE_SIZE);
        int clickY = (int) (clickEvent.getY() / Consts.TILE_SIZE);

        for (Point p : gameState.moves){

            if (p.x == clickX && p.y == clickY && clickEvent.getButton() == MouseButton.PRIMARY){
                this.field.setX(clickX * Consts.TILE_SIZE);
                this.field.setY(clickY * Consts.TILE_SIZE);
                this.currentPosition.x = clickX;
                this.currentPosition.y = clickY;
                gameState.normalColorTiles();
                gameState.moves.clear();
                gameState.clickedSomeFigure = false;
                break;
            }
        }

    };


    public EventHandler<MouseEvent> figureMouseEvent = (clickEvent) -> {
        System.out.println("Just clicked " + figureType);

        if(this.gameState.clickedSomeFigure){
            gameState.normalColorTiles();
            gameState.moves.clear();
        }

        this.gameState.clickedSomeFigure = true;

        int clickX = (int) (clickEvent.getX() / Consts.TILE_SIZE); //x, y w kafelkach (0-8) miejsca w ktore kliknieto
        int clickY = (int) (clickEvent.getY() / Consts.TILE_SIZE);

        Point clickedPoint = new Point(clickX, clickY);
        gameState.moves = getPossibleMoves(clickedPoint);

        for(Point p : gameState.moves) {
            System.out.println("Possible: " + p.x + " " + p.y);
            Tile t = this.gameState.mapTiles[p.x][p.y];
            t.addEventHandler(MouseEvent.MOUSE_CLICKED, moveFigureEvent);  // dodanie do podświetlonych ruchów 'przycisku'

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

    };

}
