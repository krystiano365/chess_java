package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * Bishop moze ruszac sie dowolnie po skosie
 */
public class Bishop extends Figure {

    public Bishop(int x, int y, Owner owner, GameState gameState) {
        super(x, y, FigureType.BISHOP, gameState, owner);
    }

    //szachownica 0,0 to lewy GORNY rog, os x idzie w prawo, os y idzie w dol
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

        return points;
    }
}
