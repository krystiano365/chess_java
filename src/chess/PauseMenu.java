package chess;


import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PauseMenu{

    private Pane menuPane = new Pane();
    private GameState gameState;
    private final int pauseWindowUpperBound = Consts.WINDOW_HEIGHT/2 - Consts.PAUSE_MENU_HEIGHT/2;

    public PauseMenu(GameState gameState){
        this.gameState = gameState;
        createPauseMenu();
    }

    private void createPauseMenu(){



        Text pauseText = new Text("PAUSE");
        pauseText.setFont(Font.font("verdana", FontWeight.BOLD, 40));
        pauseText.setX(Consts.WINDOW_WIDTH/2 - pauseText.getLayoutBounds().getWidth()/2);
        pauseText.setY(pauseWindowUpperBound + pauseText.getLayoutBounds().getHeight());

        Button exitButton = new Button();
        exitButton.setText("EXIT");
        exitButton.setPrefSize(Consts.BUTTON_WIDTH, Consts.BUTTON_HEIGHT);
        exitButton.setLayoutX(Consts.WINDOW_WIDTH / 2 - Consts.BUTTON_WIDTH/2);
        exitButton.setLayoutY(pauseWindowUpperBound + 200);
        exitButton.setOnAction(event -> System.exit(0));

        menuPane.getChildren().addAll(createPauseBackground(), pauseText, exitButton);

    }

    private Pane createPauseBackground(){
        Rectangle transparentBackground = new Rectangle(Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT);
        transparentBackground.setFill(Color.rgb(0, 0, 0, 0.5));
        Rectangle pauseBackground = new Rectangle(Consts.PAUSE_MENU_WIDTH, Consts.PAUSE_MENU_HEIGHT);
        pauseBackground.setArcHeight(20);
        pauseBackground.setArcWidth(20);
        pauseBackground.setFill(Color.WHITE);
        pauseBackground.setStroke(Color.BLACK);
        pauseBackground.setLayoutX(Consts.WINDOW_WIDTH/2 - Consts.PAUSE_MENU_WIDTH/2);
        pauseBackground.setLayoutY(pauseWindowUpperBound);

        return new Pane(transparentBackground, pauseBackground);
    }


    public void showPauseMenu() {
        gameState.pane.getChildren().add(menuPane);
    }

    public void hidePauseMenu() {
        gameState.pane.getChildren().remove(menuPane);
    }

}
