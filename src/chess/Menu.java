package chess;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public abstract class Menu {
    Pane menuPane = new Pane();
    static int menuWindowUpperBound;
    GameState gameState;

    public Menu(GameState gameState, int menuWindowUpperBound) {
        this.gameState = gameState;
        Menu.menuWindowUpperBound = menuWindowUpperBound;
    }

    public static Pane createBackground(int width, int height) {
        Rectangle transparentBackground = new Rectangle(Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT);
        transparentBackground.setFill(Color.rgb(0, 0, 0, 0.5));

        Rectangle pauseBackground = new Rectangle(width, height);
        pauseBackground.setArcHeight(20);
        pauseBackground.setArcWidth(20);
        pauseBackground.setFill(Color.WHITE);
        pauseBackground.setStroke(Color.BLACK);
        pauseBackground.setLayoutX(Consts.WINDOW_WIDTH / 2 - width / 2);
        pauseBackground.setLayoutY(menuWindowUpperBound);

        return new Pane(transparentBackground, pauseBackground);
    }

    public Text createText(String label) {
        Text pauseText = new Text(label);
        pauseText.setFont(Font.font("verdana", FontWeight.BOLD, 40));
        pauseText.setX(Consts.WINDOW_WIDTH / 2 - pauseText.getLayoutBounds().getWidth() / 2);
        pauseText.setY(menuWindowUpperBound + pauseText.getLayoutBounds().getHeight());
        return pauseText;
    }

    public Button createButton(String label, int elementNumberInButtonsRow) {
        final int spacing = 100;
        Button button = new Button();
        button.setText(label);
        button.setPrefSize(Consts.BUTTON_WIDTH, Consts.BUTTON_HEIGHT);
        button.setLayoutX(Consts.WINDOW_WIDTH / 2 - Consts.BUTTON_WIDTH / 2);
        button.setLayoutY(menuWindowUpperBound + elementNumberInButtonsRow * spacing);
        return button;
    }

    public void setExitButtonHandlers(Button exitButton) {
        exitButton.setOnAction(event -> System.exit(0));
        exitButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.exit(0);
            }
        });
    }


    public void setContinueButtonHandlers(Button continueButton) {
        continueButton.setOnAction(event -> {
            hideMenu();
            gameState.pauseMenuShown = false;
        });
        continueButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                hideMenu();
                gameState.pauseMenuShown = false;
            }
        });
    }

    public void showMenu() {
        gameState.pane.getChildren().add(menuPane);
    }

    public void hideMenu() {
        gameState.pane.getChildren().remove(menuPane);
    }
}
