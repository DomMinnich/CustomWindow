import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

        // Create a new label
        Label label = new Label("Label label Label");
        label.setStyle("-fx-text-fill: #fff; -fx-font-size: 18;");

        // Wrap the label in an HBox
        HBox labelContainer = new HBox(label);
        labelContainer.setPadding(new Insets(10)); // Add some padding

        // Add the label to the center of the root pane
        root.setCenter(labelContainer);

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

        // Create a ScaleTransition
        ScaleTransition st = new ScaleTransition(Duration.millis(300), button);
        st.setByX(0.15); // Increase the width by 15%
        st.setByY(0.15); // Increase the height by 15%
        st.setCycleCount(2); // The animation will play twice
        st.setAutoReverse(true); // The animation will play in reverse for the second play

        // Create shrinkTransition
        ScaleTransition shrinkTransition = new ScaleTransition(Duration.millis(300), button);
        shrinkTransition.setByX(-0.25); // Decrease the width by 15%
        shrinkTransition.setByY(-0.25); // Decrease the height by 15%
        shrinkTransition.setCycleCount(2); // The animation will play twice
        shrinkTransition.setAutoReverse(true); // The animation will play in reverse for the second play

        // Create a RotateTransition
        RotateTransition rt = new RotateTransition(Duration.millis(100), button);
        rt.setByAngle(10); // Rotate by 10 degrees
        rt.setCycleCount(4); // The animation will play 4 times
        rt.setAutoReverse(true); // The animation will play in reverse for the second and fourth plays

        // Set the onMouseEntered and onMouseExited events
        button.setOnMouseEntered(e -> {
            button.setScaleX(1.1);
            button.setScaleY(1.1);
            if (text.equals("[]")) { // Only play the scale animation for the maximize button
                st.play();
            } else if (text.equals("X")) { // Only play the rotate animation for the exit button
                rt.play();
            } else {
                shrinkTransition.play();
            }
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
