package io.sufeng.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfxtras.styles.jmetro8.JMetro;

import java.net.URL;

/**
 * @author: sufeng
 * @create: 2020-01-10 09:25
 */
public abstract class BaseView<Control> extends Application {

    protected Parent rootView;

    protected Control control;
    @Override
    public void start(Stage primaryStage) throws Exception {
        String viewName = getViewName();
        String viewStyle = getViewStyle();
        String viewDefaultTitle = getViewDefaultTitle();
        URL resource = BaseView.class.getClassLoader().getResource(viewName);
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        rootView = fxmlLoader.load();
        control = (Control) fxmlLoader.getController();
        Scene scene = new Scene(rootView);
        primaryStage.setTitle(viewDefaultTitle);
        if(viewStyle != null && viewStyle.length()!=0){
            scene.getStylesheets().add(BaseView.class.getClassLoader().getResource(viewStyle).toExternalForm());
        }
        new JMetro(JMetro.Style.LIGHT).applyTheme(scene);
        primaryStage.setOnCloseRequest(event -> onViewClose(event));
        primaryStage.setScene(scene);
        primaryStage.show();
        this.onViewStart();
    }

    public String getViewDefaultTitle() {
        return "Title";
    }

    public int getViewDefaultWidth() {
        return 600;
    }

    public int getViewDefaultHeight() {
        return 500;
    }

    public abstract String getViewName();

    public String getViewStyle() {
        return null;
    }

    public abstract void onViewStart();

    public abstract void onViewClose(WindowEvent event);

}
