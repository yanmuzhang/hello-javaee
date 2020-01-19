package io.sufeng.view.home;

import io.sufeng.view.base.BaseView;
import javafx.application.Platform;
import javafx.stage.WindowEvent;

/**
 * @author: sufeng
 * @create: 2020-01-09 16:30
 */
public class HomeView extends BaseView<HomeControl> {

    @Override
    public String getViewName() {
        return "ui/home.fxml";
    }

    @Override
    public void onViewStart() {
        System.out.println("onViewStart");

    }


    @Override
    public String getViewDefaultTitle() {
        return "常用工具箱";
    }

    @Override
    public void onViewClose(WindowEvent event) {
        Platform.exit();
    }
}
