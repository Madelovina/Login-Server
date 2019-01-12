import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// jar cfve client.jar Main Main.class MyClient.class Combo.class

public class Main extends Application {

    final static MyClient client = new MyClient();
    // static boolean online = true;

    public static void main(String[] args) {
        launch(args);
    }

    private void loginPaneSetup(GridPane loginPane) {
        loginPane.setPadding(new Insets(10, 10, 10, 10));
        loginPane.setMaxWidth(250);
        loginPane.setMaxHeight(150);
        loginPane.setHgap(5);
        loginPane.setVgap(5);
        Image bgImg = new Image("https://i.imgur.com/Kx8b8hq.png", 300, 300, false, true);
        loginPane.setBackground(new Background(new BackgroundImage(bgImg, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
    }

    @Override
    public void start(Stage primaryStage) {
        Combo combo = new Combo("", "");

        final Stage window = primaryStage;
        window.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Login Window");
        final GridPane loginPane = new GridPane();
        final Scene login = new Scene(loginPane);

        loginPaneSetup(loginPane);

        VBox complete = new VBox();
        complete.setAlignment(Pos.CENTER);
        complete.setSpacing(10);

        Label title = new Label("Welcome...");
        title.setFont(new Font(25));

        HBox hb = new HBox();
        TextField unField = new TextField();
        PasswordField pwField = new PasswordField();
        hb.getChildren().addAll(textLabels(), textFields(unField, pwField));
        hb.setSpacing(10);

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        Label status = new Label("");

        loginButton.setOnAction(actionEvent -> {
            combo.setComplete(unField.getText() + ":" + pwField.getText());
            processLogin(status, combo.getComplete());
        });

        registerButton.setOnAction(actionEvent -> {
            combo.setComplete(unField.getText() + ":" + pwField.getText());
            if (client.sendData(combo.getComplete(), "r")) {
                status.setTextFill(Color.GREEN);
                status.setText("Registration Success. ");
            } else {
                status.setTextFill(Color.RED);
                status.setText("Registration Failure. ");
            }
        });

        complete.getChildren().addAll(title, hb, loginButtons(loginButton, registerButton), status);
        loginPane.getChildren().add(complete);
        window.setScene(login);
        primaryStage.show();
    }

    private void processLogin(Label label, String s) {
        if (client.sendData(s, "l")) {
            label.setTextFill(Color.GREEN);
            label.setText("Login Success. ");
        } else {
            label.setTextFill(Color.RED);
            label.setText("Login Failure. ");
        }
    }

    private HBox loginButtons(Button loginButton, Button r) {
        HBox hb = new HBox();
        hb.setAlignment(Pos.TOP_CENTER);
        hb.setSpacing(10);
        loginButton.setPrefWidth(150);
        r.setPrefWidth(150);
        hb.getChildren().addAll(r, loginButton);
        return hb;
    }

    private VBox textLabels() {
        VBox vb1 = new VBox();
        Label unLabel = new Label("Username: ");
        unLabel.setFont(new Font(15));
        Label pwLabel = new Label("Password: ");
        pwLabel.setFont(new Font(15));
        unLabel.setPrefWidth(125);
        pwLabel.setPrefWidth(125);
        vb1.getChildren().addAll(unLabel, pwLabel);
        vb1.setSpacing(15);
        return vb1;
    }

    private VBox textFields(TextField un, TextField pw) {
        VBox vb2 = new VBox();
        un.setPromptText("Enter username");
        pw.setPromptText("Enter password");
        un.setMaxWidth(125);
        pw.setMaxWidth(125);
        vb2.getChildren().addAll(un, pw);
        vb2.setSpacing(10);
        return vb2;
    }
}
