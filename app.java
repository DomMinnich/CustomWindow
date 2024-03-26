import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class app extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED); // Removes default window decorations

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #333;");

        // Creating a VBox for the navigation bar
        VBox navBar = new VBox();
        navBar.setPadding(new Insets(10));
        navBar.setSpacing(10);
        navBar.setStyle("-fx-background-color: #555;");
        navBar.setEffect(new DropShadow(10, Color.BLACK));

        // Custom window controls
        Button minimizeButton = createButton("-");
        Button maximizeButton = createButton("[]");
        Button closeButton = createButton("X");

        // Handling actions for window controls
        minimizeButton.setOnAction(e -> primaryStage.setIconified(true));
        maximizeButton.setOnAction(e -> primaryStage.setMaximized(!primaryStage.isMaximized()));
        closeButton.setOnAction(e -> primaryStage.close());

        // Adding window controls to the navigation bar
        navBar.getChildren().addAll(minimizeButton, maximizeButton, closeButton);

        // Setting the navigation bar on the left side
        root.setLeft(navBar);

       
        // Making the application draggable using the navigation bar
        navBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        navBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root, 900, 600);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Custom Window");
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #666; -fx-text-fill: #fff; -fx-font-size: 18;");
        button.setPrefSize(40, 40);
        button.setOnMouseEntered(e -> {
            button.setScaleX(1.1);
            button.setScaleY(1.1);
        });
        button.setOnMouseExited(e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
