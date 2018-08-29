package chess.ui;

import chess.logics.Consts;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

//https://stackoverflow.com/questions/22848829/how-do-i-add-an-image-inside-a-rectangle-or-a-circle-in-javafx
public class Tile extends Rectangle {

    //x, y to piksele
    public Tile(int x, int y) {
        setWidth(Consts.TILE_SIZE);
        setHeight(Consts.TILE_SIZE);
        setX(x * Consts.TILE_SIZE);
        setY(y * Consts.TILE_SIZE);
        setMapFill();
    }

    public void setMapFill(){
        setFill(((getX() / Consts.TILE_SIZE) + (getY() / Consts.TILE_SIZE)) % 2 == 0 ? Color.WHITE : Color.DARKRED);
    }

}
