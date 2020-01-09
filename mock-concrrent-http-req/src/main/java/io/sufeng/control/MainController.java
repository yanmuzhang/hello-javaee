package io.sufeng.control;

import io.sufeng.ui.MainApplication;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author: sufeng
 * @create: 2020-01-09 16:30
 */
public class MainController {

    static String[] httpMethod = new String[]{"GET","POST","PUT","DELETE"};

    @FXML
    private  MenuButton ui_http_method;


    public void  show(Stage primaryStage){
//        ui-http-method
        Parent root = null;
        try {
            root = FXMLLoader.load(MainApplication.class.getClassLoader().getResource("Main.fxml"));
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(MainApplication.class.getClassLoader().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Simple JavaFX");
            ObservableList<MenuItem> items = ui_http_method.getItems();
            for (String method : httpMethod) {
                items.add(new MenuItem(method));
            }
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handlerBtnClick(ActionEvent event) {
        Button btnSource = (Button) event.getSource();
        btnSource.setText("I am clicked!");
    }

    @FXML
    public void onHttpMehodSelected(ActionEvent actionEvent) {
        MenuItem source = (MenuItem)actionEvent.getSource();
        String text = source.getText();
        ui_http_method.setText(text);
    }
}
