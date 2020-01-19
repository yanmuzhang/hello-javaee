package io.sufeng.module.http;

import io.sufeng.core.config.AppConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author: sufeng
 * @create: 2020-01-19 11:22
 */
public class HttpModuleControl   implements Initializable {

    @FXML
    public VBox httpBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AppConfig load = AppConfig.Builder.load();
        System.out.println(getClass().getName()+": => "+load);

//        httpBox.prefWidthProperty().bind(pane.widthProperty());//宽度绑定为Pane宽度
    }
}
