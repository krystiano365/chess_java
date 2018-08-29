/*
package chess.ui;

import chess.piece.*;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

import static chess.Main.board;

public class Board {

    EventHandler<MouseEvent> mouseEvent;
    public static final int height = 8;
    public static final int width = 8;

    public static Tile[][] tilesArr;
    public ArrayList<Piece> pieces = new ArrayList<>();

    public Board(Pane tiles) {
        System.out.println("Konstruktor");
        createBoard(tiles);
        createPieces(true, tiles);
    }


    public void removeHighlight(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tilesArr[i][j].setFill(((i + j) % 2 == 0) ? Tile.light : Tile.black);
            }
        }
    }

    public void handleClick(){
        mouseEvent = (clickEvent) -> {
            int clickX = (int)(clickEvent.getX() / Main.tileSize);
            int clickY = (int)(clickEvent.getY() / Main.tileSize);
            ImageView image;

            for(Piece p: pieces){
                image = p.image;
                int imageX = (int)(image.getX() / Main.tileSize);
                int imageY = (int)(image.getY() / Main.tileSize);

                if((imageX == clickX && imageY == clickY) || (p.field.getX() == clickX && p.field.getY() == clickY)){
                    if (p instanceof MoveHighlighter) {
                        ((MoveHighlighter) p).highlightMoves(board);

                    }
                }else{
                   removeHighlight();
                }
            }
            System.out.println(clickX + " " + clickY);
        };
    }


    public static void getTile() {
    }
}
*/
