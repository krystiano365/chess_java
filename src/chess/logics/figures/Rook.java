package chess.logics.figures;

import chess.logics.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Figure {

    public Rook(int x, int y, Owner owner, GameState gameState) {
        super(x, y, FigureType.ROOK, gameState, owner);
    }



    @Override
    public List<Point> getPossibleMoves(Point currentPosition) {
        List<Point> points = new ArrayList<>();

        //w gore
        for (int x = currentPosition.x, y = currentPosition.y - 1; y >= 0; y--) {
            if (!validateMoves(x, y, points)){
                break;
            }
        }
        //w dol
        for (int x = currentPosition.x, y = currentPosition.y + 1; y < Consts.MAP_HEIGHT; y++) {
            if (!validateMoves(x, y, points)){
                break;
            }
        }
        //w lewo
        for (int x = currentPosition.x - 1, y = currentPosition.y; x >= 0; x--) {
            if (!validateMoves(x, y, points)){
                break;
            }
        }
        //w prawo
        for (int x = currentPosition.x + 1, y = currentPosition.y; x < Consts.MAP_WIDTH; x++) {
            if (!validateMoves(x, y, points)){
                break;
            }
        }
        return points;
    }
}
