package chess.figures;

import chess.*;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Figure {

    public Queen(int x, int y, Owner owner, GameState gameState){
        super(x, y, FigureType.QUEEN, gameState, owner);
    }
    @Override
    public List<Point> getPossibleMoves(Point currentPosition) {
        List<Point> points = new ArrayList<>();

        //skos w lewo w dol
        for (int x = currentPosition.x - 1, y = currentPosition.y + 1; x >= 0 && y < Consts.MAP_HEIGHT; x--, y++) {
            if (!validateMove(x, y, points)){
                break;
            }
        }
        //skos w prawo w dol
        for (int x = currentPosition.x + 1, y = currentPosition.y + 1; x < Consts.MAP_WIDTH && y < Consts.MAP_HEIGHT; x++, y++) {
            if (!validateMove(x, y, points)){
                break;
            }
        }
        //skos w lewo w gore
        for (int x = currentPosition.x - 1, y = currentPosition.y - 1; x >= 0 && y >= 0; x--, y--) {
            if (!validateMove(x, y, points)){
                break;
            }
        }
        //skos w prawo w gore
        for (int x = currentPosition.x + 1, y = currentPosition.y - 1; x < Consts.MAP_WIDTH && y >= 0; x++, y--) {
            if (!validateMove(x, y, points)){
                break;
            }
        }

        //w gore
        for (int x = currentPosition.x, y = currentPosition.y - 1; y >= 0; y--) {
            if (!validateMove(x, y, points)){
                break;
            }
        }
        //w dol
        for (int x = currentPosition.x, y = currentPosition.y + 1; y < Consts.MAP_HEIGHT; y++) {
            if (!validateMove(x, y, points)){
                break;
            }
        }
        //w lewo
        for (int x = currentPosition.x - 1, y = currentPosition.y; x >= 0; x--) {
            if (!validateMove(x, y, points)){
                break;
            }
        }
        //w prawo
        for (int x = currentPosition.x + 1, y = currentPosition.y; x < Consts.MAP_WIDTH; x++) {
            if (!validateMove(x, y, points)){
                break;
            }
        }

        return points;
    }
}
