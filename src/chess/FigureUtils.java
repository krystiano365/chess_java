package chess;

import javafx.scene.paint.Color;

public class FigureUtils {

    public static void highlightTile(Tile t, GameState gameState){
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
        if (t.figureColour == gameState.getOpponent()){
            t.setFill(Color.INDIANRED);
        }
    }

}
