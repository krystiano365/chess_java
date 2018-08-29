package chess.logics;

import chess.ui.Tile;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

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

    protected void introduceYourself() {
        System.out.println("My figure type is: " + figureType + " and my XY is "
                + currentPosition.x + " " + currentPosition.y);
    }


}
