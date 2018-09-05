package chess.logics.figures;

import chess.logics.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {

    public Pawn(int x, int y, Owner owner, GameState gameState){
        super(x, y, FigureType.PAWN, gameState, owner);
    }

    @Override
    public List<Point> getPossibleMoves(Point currentPosition) {
        List<Point> points = new ArrayList<>();

        int x = currentPosition.x;
        int y = currentPosition.y;

        if (this.owner == Owner.WHITE_PLAYER) {
            validateMovePawn(x, y - 1, points);
            validateDiagonalPawn(x - 1, y - 1, points);
            validateDiagonalPawn(x + 1, y - 1, points);
            if (y == 6) {
                validateMovePawn(x, y - 2, points);  // first double shift
            }
        } else if (this.owner == Owner.BLACK_PLAYER) {
            validateMovePawn(x, y + 1, points);
            validateDiagonalPawn(x - 1, y + 1, points);
            validateDiagonalPawn(x + 1, y + 1, points);
            if (y == 1) {
                validateMovePawn(x, y + 2, points);  // first double shift
            }
        }

        return points;
    }


    private void validateMovePawn (int x, int y, List<Point> points) {
        if (x < Consts.MAP_WIDTH && x >= 0 && y < Consts.MAP_HEIGHT && y >= 0) {
            if (gameState.mapTiles[x][y].figureColour == Owner.NONE) {
                Point p = new Point(x, y);
                points.add(p);
            }
        }
    }

    private void validateDiagonalPawn (int x, int y, List<Point> points) {
        if (x < Consts.MAP_WIDTH && x >= 0 && y < Consts.MAP_HEIGHT && y >= 0)
            if (gameState.mapTiles[x][y].figureColour == gameState.getOpponent()){
               validateMove(x, y, points);
            }
    }
}
