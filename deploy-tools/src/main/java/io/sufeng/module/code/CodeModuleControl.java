package io.sufeng.module.code;

import io.sufeng.core.config.AppConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author: sufeng
 * @create: 2020-01-19 11:22
 */
public class CodeModuleControl implements Initializable {




    @FXML
    public GridPane httpBox;
    @FXML
    public Button ok;
    @FXML
    public TextArea leftText;
    @FXML
    public TextArea rightText;
    @FXML
    public Label lable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AppConfig load = AppConfig.Builder.load();
        System.out.println(getClass().getName()+": => "+load);
    }
}
