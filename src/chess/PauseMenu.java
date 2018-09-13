package chess;

import javafx.scene.control.Button;


public class PauseMenu extends Menu{

    private GameState gameState;

    public PauseMenu(GameState gameState) {
        super(gameState, Consts.pauseWindowUpperBound);
        this.gameState = gameState;
        createPauseMenu();
    }


    private void createPauseMenu(){

        Button exitButton = createButton("EXIT", 2);
        Button continueButton = createButton("CONTINUE", 1);
        setContinueButtonHandlers(continueButton);
        setExitButtonHandlers(exitButton);

        menuPane.getChildren().addAll(
                createBackground(Consts.PAUSE_MENU_WIDTH, Consts.PAUSE_MENU_HEIGHT),
                createText("PAUSE"),
                continueButton,
                exitButton
        );

    }




}
