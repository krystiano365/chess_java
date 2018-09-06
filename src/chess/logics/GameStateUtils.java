package chess.logics;

import chess.logics.figures.*;

import java.util.ArrayList;

public class GameStateUtils {
    
    static ArrayList<Figure> spawnFigures(GameState gameState){

        ArrayList<Figure> figures = new ArrayList<>();
        
        Bishop bishop_white_1 = new Bishop(2, 7, Owner.WHITE_PLAYER, gameState);
        figures.add(bishop_white_1);

        Bishop bishop_white_2 = new Bishop(5, 7, Owner.WHITE_PLAYER, gameState);
        figures.add(bishop_white_2);

        Rook rook_white_1 = new Rook(7, 7, Owner.WHITE_PLAYER, gameState);
        figures.add(rook_white_1);

        Rook rook_white_2 = new Rook(0, 7, Owner.WHITE_PLAYER, gameState);
        figures.add(rook_white_2);

        King king_white = new King(4, 7, Owner.WHITE_PLAYER, gameState);
        figures.add(king_white);

        Queen queen_white = new Queen(3, 7, Owner.WHITE_PLAYER, gameState);
        figures.add(queen_white);

        for (int x = 0; x < Consts.MAP_WIDTH; x++) {
            figures.add(new Pawn(x, 6, Owner.WHITE_PLAYER, gameState));
        }

        Bishop bishop_black_1 = new Bishop(2, 0, Owner.BLACK_PLAYER, gameState);
        figures.add(bishop_black_1);

        Bishop bishop_black_2 = new Bishop(5, 0, Owner.BLACK_PLAYER, gameState);
        figures.add(bishop_black_2);

        Rook rook_black_1 = new Rook(0, 0, Owner.BLACK_PLAYER, gameState);
        figures.add(rook_black_1);

        Rook rook_black_2 = new Rook(7, 0, Owner.BLACK_PLAYER, gameState);
        figures.add(rook_black_2);

        King king_black = new King(4, 0, Owner.BLACK_PLAYER, gameState);
        figures.add(king_black);

        Queen queen_black = new Queen(3, 0, Owner.BLACK_PLAYER, gameState);
        figures.add(queen_black);

        for (int x = 0; x < Consts.MAP_WIDTH; x++) {
            figures.add(new Pawn(x, 1, Owner.BLACK_PLAYER, gameState));
        }
        
        return figures;
    }
    
}
