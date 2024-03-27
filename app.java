import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        root.setStyle("-fx-background-color: #131a24;");

        VBox navBar = new VBox(); // Navigation bar
        navBar.setStyle("-fx-background-color: #1e2d45;"); // Background color
        navBar.setEffect(new DropShadow(10, Color.rgb(255, 202, 135, 1))); // Drop shadow effect 


        // Custom window controls
        Button closeButton = createButton("/Icons/Close_icons/Xout3.png"); // Close button
        Button maximizeButton = createButton("/Icons/Maximize_icons/Maximize3.png"); // Maximize button
        Button minimizeButton = createButton("/Icons/Minimize_icons/Minimize3.png"); // Minimize button

        // Handling actions for window controls
        minimizeButton.setOnAction(e -> primaryStage.setIconified(true));
        maximizeButton.setOnAction(e -> primaryStage.setMaximized(!primaryStage.isMaximized()));
        closeButton.setOnAction(e -> primaryStage.close());

        navBar.getChildren().addAll(closeButton, maximizeButton, minimizeButton); //X then Max then Min button
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

    private Button createButton(String imageName) {
        Image image = new Image(getClass().getResourceAsStream(imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        Button button = new Button();
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: transparent;");

        // Apply hover animation
        ScaleTransition scaleInTransition = new ScaleTransition(Duration.millis(100), imageView);
        scaleInTransition.setToX(1.2);
        scaleInTransition.setToY(1.2);

        ScaleTransition scaleOutTransition = new ScaleTransition(Duration.millis(100), imageView);
        scaleOutTransition.setToX(1.0);
        scaleOutTransition.setToY(1.0);

        button.setOnMouseEntered(e -> scaleInTransition.playFromStart());
        button.setOnMouseExited(e -> {
            scaleOutTransition.stop();
            scaleOutTransition.playFromStart();
        });

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
