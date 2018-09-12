package chess;


import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class GameOverMenu extends Menu {

    private String winner;

    public GameOverMenu(GameState gameState, String winner) {
        super(gameState, Consts.gameoverWindowUpperBound);
        this.winner = winner;
        createGameoverMenu();
    }

    private void createGameoverMenu(){

        Button exitButton = createButton("EXIT", 1);
        Text gameoverText = createText(winner + " WIN!");

        menuPane.getChildren().addAll(
                createBackground((int)(gameoverText.getLayoutBounds().getWidth() + 100), Consts.GAMEOVER_MENU_HEIGHT),
                gameoverText,
                exitButton

        );


    }
}
