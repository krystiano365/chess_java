package chess.logics.figures;

import chess.logics.*;
import chess.ui.Tile;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Bishop moze ruszac sie dowolnie po skosie
 */
public class Bishop extends Figure {

    public EventHandler<MouseEvent> figureMouseEvent = (clickEvent) -> {
        System.out.println("Just clicked " + figureType);

        gameState.normalColorTiles();
        this.gameState.clickedSomeFigure = true;

        int clickX = (int) (clickEvent.getX() / Consts.TILE_SIZE); //x, y w kafelkach (0-8) miejsca w ktore kliknieto
        int clickY = (int) (clickEvent.getY() / Consts.TILE_SIZE);

        Point clickedPoint = new Point(clickX, clickY);
        List<Point> moves = getPossibleMoves(clickedPoint);

        for(Point p : moves){
            System.out.println("Possible: " + p.x + " " + p.y);
            this.gameState.mapTiles[p.x][p.y].setFill(Color.rgb(0, 100, 0));
        }

    };

    public Bishop(int x, int y, Owner owner, GameState gameState) {
        this.currentPosition = new Point(x, y);
        this.figureType = FigureType.BISHOP;
        this.introduceYourself();
        this.owner = owner;
        this.gameState = gameState;
        this.field = new Tile(currentPosition.x , currentPosition.y);

        this.field.addEventHandler(MouseEvent.MOUSE_CLICKED, figureMouseEvent); //dodanie obslugi inputu tego klawisza
        this.gameState.pane.getChildren().add(this.field); //dodanie tego klawisza do jakiejs petli renderujacej (cos w tym stylu, to sprawia ze jest wyswietlane)

        try {
            Image img = new Image(new FileInputStream(System.getProperty("user.dir") + "/assets/knight.png"));
            this.field.setFill(new ImagePattern(img));
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku!");
            e.printStackTrace();
        }
    }

    //szachownica 0,0 to lewy GORNY rog, os x idzie w prawo, os y idzie w dol
    @Override
    public List<Point> getPossibleMoves(Point currentPosition) {

        List<Point> points = new ArrayList<>();

        //skos w lewo w dol
        for (int x = currentPosition.x - 1, y = currentPosition.y + 1; x >= 0 && y < Consts.MAP_HEIGHT; x--, y++) {
            Point p = new Point(x, y);
            points.add(p);
        }
        //skos w prawo w dol
        for (int x = currentPosition.x + 1, y = currentPosition.y + 1; x < Consts.MAP_WIDTH && y < Consts.MAP_HEIGHT; x++, y++) {
            Point p = new Point(x, y);
            points.add(p);
        }
        //skos w lewo w gore
        for (int x = currentPosition.x - 1, y = currentPosition.y - 1; x >= 0 && y >= 0; x--, y--) {
            Point p = new Point(x, y);
            points.add(p);
        }
        //skos w prawo w gore
        for (int x = currentPosition.x + 1, y = currentPosition.y - 1; x < Consts.MAP_WIDTH && y >= 0; x++, y--) {
            Point p = new Point(x, y);
            points.add(p);
        }

        return points;
    }
}
