package io.sufeng.view.textpage1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author: sufeng
 * @create: 2020-01-10 10:16
 */
public class TextControl  implements Initializable {
    @FXML
    Label lableText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("TextControl => initialize ");
    }
}
