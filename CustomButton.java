import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CustomButton {
    public static Button createButton(String imageName) {
        Image image = new Image(CustomButton.class.getResourceAsStream(imageName));
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
}
