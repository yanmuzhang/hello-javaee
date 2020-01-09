package io.sufeng.control;

import io.sufeng.MainApplication;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author: sufeng
 * @create: 2020-01-09 16:30
 */
public class MainController implements EventHandler<ActionEvent> {

    static String[] httpMethod = new String[]{"GET","POST","PUT","DELETE"};

    @FXML public MenuButton uiHttpMethod;
    @FXML public TextField textEdit;

    private static final String DEFAULT_METHOD = httpMethod[0];



    public void  show(Stage primaryStage){
//        ui-http-method
        Parent root = null;
        try {
            root = FXMLLoader.load(MainApplication.class.getClassLoader().getResource("Main.fxml"));
            uiHttpMethod = (MenuButton)root.lookup("#uiHttpMethod");
            textEdit = (TextField)root.lookup("#textEdit");
            uiHttpMethod.setText(DEFAULT_METHOD);
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(MainApplication.class.getClassLoader().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Simple JavaFX");
            ObservableList<MenuItem> items = uiHttpMethod.getItems();
            for (String method : httpMethod) {
                MenuItem menuItem = new MenuItem(method);
                menuItem.setOnAction(this);
                items.add(menuItem);
            }
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("onHttpMehodSelected");
        MenuItem source = (MenuItem)event.getSource();
        String text = source.getText();
        uiHttpMethod.setText(text);
    }

    @FXML
    public void handlerBtnClick(ActionEvent event) {
        Button btnSource = (Button) event.getSource();
    }


    public void onExecCliced(MouseEvent mouseEvent) {
        String path = textEdit.getText();
        String method = uiHttpMethod.getText();


        System.out.println(path+":"+method);

    }
}
