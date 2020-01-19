package io.sufeng.utils;

import io.sufeng.view.base.BaseView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @author: sufeng
 * @create: 2020-01-10 10:49
 */
public class Window {

    public static int DEFAULT_WIDTH = 600;
    public static int DEFAULT_HEIGHT = 400;
    public static String DEFAULT_APPLICATION_STYLE = "ui/application.css";
    public static String DEFAULT_TITLE = "Title";


    public final static Window MP = new Window();


    public void open(Application application, String viewName, Object... objects) throws Exception {
        Stage primaryStage = new Stage();
        URL resource = BaseView.class.getClassLoader().getResource(viewName);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent rootView = fxmlLoader.load();
        Scene scene = new Scene(rootView, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        primaryStage.setTitle(DEFAULT_TITLE);
        scene.getStylesheets().add(BaseView.class.getClassLoader().getResource(DEFAULT_APPLICATION_STYLE).toExternalForm());
        primaryStage.setOnCloseRequest(event -> {
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
