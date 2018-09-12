package chess;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;


public class PauseMenu extends Menu{

    private GameState gameState;

    public PauseMenu(GameState gameState) {
        super(gameState, Consts.pauseWindowUpperBound);
        this.gameState = gameState;
        createPauseMenu();
    }


    private void createPauseMenu(){

        Button exitButton = createButton("EXIT", 2);
        exitButton.setOnAction(event -> System.exit(0));
        exitButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                System.exit(0);
            }
        });
        Button continueButton = createButton("CONTINUE", 1);
        setContinueButtonHandlers(continueButton);

        menuPane.getChildren().addAll(
                createBackground(Consts.PAUSE_MENU_WIDTH, Consts.PAUSE_MENU_HEIGHT),
                createText("PAUSE"),
                continueButton,
                exitButton
        );

    }


    private void setContinueButtonHandlers(Button continueButton){
        continueButton.setOnAction(event -> {
            hideMenu();
            gameState.pauseMenuShown = false;
        });
        continueButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                hideMenu();
                gameState.pauseMenuShown = false;
            }
        });
    }


}
