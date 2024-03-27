import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root = new BorderPane();

        root.setStyle("-fx-background-color: #131a24;");

        VBox navBar = new VBox();
        navBar.setStyle("-fx-background-color: #1e2d45; -fx-border-color: #FFCA87; -fx-border-width: 0 1 0 0;");
        navBar.setMinWidth(55);
        navBar.setMaxWidth(55);

        Button closeButton = CustomButton.createButton("/Icons/Close_icons/Xout3.png");
        Button maximizeButton = CustomButton.createButton("/Icons/Maximize_icons/Maximize3.png");
        Button minimizeButton = CustomButton.createButton("/Icons/Minimize_icons/Minimize3.png");

        minimizeButton.setOnAction(e -> primaryStage.setIconified(true));
        maximizeButton.setOnAction(e -> primaryStage.setMaximized(!primaryStage.isMaximized()));
        closeButton.setOnAction(e -> primaryStage.close());

        // Navigation buttons
        Button registerNavButton = NavigationButton.createNavigationButton("Register");
        registerNavButton.setRotate(-90);
        registerNavButton.setMinWidth(65);
        registerNavButton.setMaxWidth(65);
        registerNavButton.setTranslateY(40);
        registerNavButton.setTranslateX(-5);

        Button loginNavButton = NavigationButton.createNavigationButton("Login");
        loginNavButton.setRotate(-90);
        loginNavButton.setMinWidth(65);
        loginNavButton.setMaxWidth(65);
        loginNavButton.setTranslateY(40);
        loginNavButton.setTranslateX(-5);

        Button homeNavButton = NavigationButton.createNavigationButton("Home");
        homeNavButton.setRotate(-90);
        homeNavButton.setMinWidth(65);
        homeNavButton.setMaxWidth(65);
        homeNavButton.setTranslateY(40);
        homeNavButton.setTranslateX(-5);

        registerNavButton.setOnAction(e -> root.setCenter(createRegisterPage()));
        loginNavButton.setOnAction(e -> root.setCenter(createLoginPage()));
        homeNavButton.setOnAction(e -> root.setCenter(createHomePage()));

        VBox navButtons = new VBox(65, registerNavButton, loginNavButton, homeNavButton);
        navBar.getChildren().addAll(closeButton, maximizeButton, minimizeButton, navButtons);
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

        root.setCenter(createLoginPage());

        Scene scene = new Scene(root, 900, 600);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Custom Window");
        primaryStage.show();
    }

    private VBox createRegisterPage() {
        VBox registerPage = new VBox();
        registerPage.setStyle("-fx-background-color: #34495e; -fx-padding: 20;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            // Perform registration logic here
            System.out.println("Registered");
        });
        Label registerLabel = new Label("Register");
        registerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px;"); //
        registerPage.getChildren().addAll(registerLabel, usernameField, passwordField, registerButton);
        return registerPage;
    }

    private VBox createLoginPage() {
        VBox loginPage = new VBox();
        loginPage.setStyle("-fx-background-color: #34495e; -fx-padding: 20;");
        TextField loginUsernameField = new TextField();
        loginUsernameField.setPromptText("Username");
        PasswordField loginPasswordField = new PasswordField();
        loginPasswordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            // Perform login logic here
            String username = loginUsernameField.getText();
            String password = loginPasswordField.getText();
            if (isValidUser(username, password)) {
                // Successful login
                System.out.println("Logged in");
                // Redirect to home page
                root.setCenter(createHomePage());
            } else {
                // Failed login
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password!");
                alert.showAndWait();
            }
        });
        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        loginPage.getChildren().addAll(loginLabel, loginUsernameField, loginPasswordField, loginButton);
        return loginPage;
    }

    private VBox createHomePage() {
        VBox homePage = new VBox();
        homePage.setStyle("-fx-background-color: #2c3e50; -fx-padding: 20;");
        Label homeLabel = new Label("Home");
        homeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        homePage.getChildren().addAll(homeLabel);
        return homePage;
    }

    private boolean isValidUser(String username, String password) {
        // Perform actual validation here, for simplicity, always return true for
        // demonstration
        return true;
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

    private Button createNavigationButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-weight: bold;");
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
