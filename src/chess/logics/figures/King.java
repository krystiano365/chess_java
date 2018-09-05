package chess.logics.figures;

import chess.logics.*;

import java.util.ArrayList;
import java.util.List;

public class King extends Figure {

    public King(int x, int y, Owner owner, GameState gameState){
        super(x, y, FigureType.KING, gameState, owner);
    }
    @Override
    public List<Point> getPossibleMoves(Point currentPosition) {

        List<Point> points = new ArrayList<>();
        int x = currentPosition.x;
        int y = currentPosition.y;

        validateMove(x, y - 1, points); // up
        validateMove(x + 1, y - 1, points); //up right
        validateMove(x + 1, y, points); // right
        validateMove(x + 1, y + 1, points); // down right
        validateMove(x, y + 1, points); // down
        validateMove(x - 1, y + 1, points); //down left
        validateMove(x - 1, y, points); // left
        validateMove(x - 1, y - 1, points); // up left

        return points;
    }
}
