package chess;

import java.util.List;

public interface MoveValidator {
    List<Point> getPossibleMoves(Point currentPosition);
}
