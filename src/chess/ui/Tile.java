package chess.ui;

import chess.logics.Consts;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//https://stackoverflow.com/questions/22848829/how-do-i-add-an-image-inside-a-rectangle-or-a-circle-in-javafx
public class Tile extends Rectangle {

    int r;
    int g;
    int b;

    //x, y to piksele
    public Tile(int x, int y) {
        setWidth(Consts.TILE_SIZE);
        setHeight(Consts.TILE_SIZE);
        setX(x * Consts.TILE_SIZE);
        setY(y * Consts.TILE_SIZE);
        setDefaultMapFill();
    }

    public void setDefaultMapFill(){
        setFill( isWhite() ? Color.WHITE : Color.DARKRED);
    }

    public boolean isWhite(){
        return ((getX() / Consts.TILE_SIZE) + (getY() / Consts.TILE_SIZE)) % 2 == 0;
    }

}
