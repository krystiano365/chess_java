package chess.figures;

import chess.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Figure {
    public Knight(int x, int y, Owner owner, GameState gameState) {
        super(x, y, FigureType.KNIGHT, gameState, owner);
    }

    @Override
    public List<Point> getPossibleMoves(Point currentPosition) {
        List<Point> points = new ArrayList<>();
        int x = currentPosition.x;
        int y = currentPosition.y;

        validateMove(x - 2, y + 1, points);
        validateMove(x + 2, y + 1, points);
        validateMove(x - 2, y - 1, points);
        validateMove(x + 2, y - 1, points);
        validateMove(x - 1, y + 2, points);
        validateMove(x + 1, y + 2, points);
        validateMove(x - 1, y - 2, points);
        validateMove(x + 1, y - 2, points);


        return points;
    }
}
