package io.sufeng.view.home;

import io.sufeng.view.base.BaseView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author: sufeng
 * @create: 2020-01-09 16:30
 */
public class HomeView extends BaseView<HomeControl> {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public String getViewName() {
        return "home.fxml";
    }

    @Override
    public void onViewStart() {
        System.out.println("onViewStart");

    }


    @Override
    public String getViewDefaultTitle() {
        return "Mock-Concurrent-Http-Req";
    }

    @Override
    public void onViewClose(WindowEvent event) {
        Platform.exit();
    }
}
