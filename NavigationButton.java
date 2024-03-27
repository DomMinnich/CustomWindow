import javafx.scene.control.Button;

public class NavigationButton {
    public static Button createNavigationButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold;");
        return button;
    }
}
