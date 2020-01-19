package io.sufeng.core.home;

import io.sufeng.core.config.AppConfig;
import io.sufeng.core.BaseView;
import javafx.application.Platform;
import javafx.stage.WindowEvent;

import java.io.IOException;

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
        try {
            AppConfig.Builder.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Platform.exit();
    }
}
