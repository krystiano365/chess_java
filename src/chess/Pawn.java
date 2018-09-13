package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {

    public Pawn(int x, int y, Owner owner, GameState gameState) {
        super(x, y, FigureType.PAWN, gameState, owner);
    }

    @Override
    public List<Point> getPossibleMoves(Point currentPosition) {
        List<Point> points = new ArrayList<>();

        int x = currentPosition.x;
        int y = currentPosition.y;

        if (this.owner == Owner.WHITE_PLAYER) {
            validateForwardPawn(x, y - 1, points);
            validateDiagonalPawn(x - 1, y - 1, points);
            validateDiagonalPawn(x + 1, y - 1, points);
            if (y == 3) {
                validateEnPassant(x - 1, y, points);
                validateEnPassant(x + 1, y, points);
            }
            if (y == 6) {
                validateForwardPawn(x, y - 2, points);  // first double shift
            }
        } else if (this.owner == Owner.BLACK_PLAYER) {
            validateForwardPawn(x, y + 1, points);
            validateDiagonalPawn(x - 1, y + 1, points);
            validateDiagonalPawn(x + 1, y + 1, points);
            if (y == 4) {
                validateEnPassant(x - 1, y, points);
                validateEnPassant(x + 1, y, points);
            }
            if (y == 1) {
                validateForwardPawn(x, y + 2, points);  // first double shift
            }
        }

        return points;
    }


    private void validateForwardPawn(int x, int y, List<Point> points) {
        if (x < Consts.MAP_WIDTH && x >= 0 && y < Consts.MAP_HEIGHT && y >= 0) {
            if (gameState.mapTiles[x][y].figureColour == Owner.NONE) {
                Point p = new Point(x, y);
                points.add(p);
            }
        }
    }

    private void validateDiagonalPawn(int x, int y, List<Point> points) {
        if (x < Consts.MAP_WIDTH && x >= 0 && y < Consts.MAP_HEIGHT && y >= 0) {
            if (gameState.mapTiles[x][y].figureColour == gameState.getOpponent()) {
                validateMove(x, y, points);
            }
        }
    }

    private void validateEnPassant(int x, int y, List<Point> points) {
        if (x < Consts.MAP_WIDTH && x >= 0 && y < Consts.MAP_HEIGHT && y >= 0) {
            if (gameState.mapTiles[x][y].figureColour == gameState.getOpponent()) {
                int newY = (gameState.currentPlayer == Owner.WHITE_PLAYER ? y - 1 : y + 1);
                if (gameState.mapTiles[x][newY].figureColour == Owner.NONE) {
                    Point p = new Point(x, newY);
                    points.add(p);
                    gameState.pawnEnPassantPoints.add(p);
                }
            }
        }
    }
}
