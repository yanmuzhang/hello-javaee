package io.sufeng.module.http;

import io.sufeng.core.config.AppConfig;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author: sufeng
 * @create: 2020-01-19 11:22
 */
public class HttpModuleControl implements Initializable {

    static String[] httpMethod = new String[]{"GET", "POST", "PUT", "DELETE"};




    @FXML
    public TextField httpUrl;
    @FXML
    public Button httpBtn;
    @FXML
    public TextArea httpResponseText;
    private static final String DEFAULT_METHOD = httpMethod[0];

    @FXML
    public MenuButton httpMethodList;
    @FXML
    public ListView httpHistoryListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppConfig load = AppConfig.Builder.load();
        System.out.println(getClass().getName() + ": => " + load);
        httpMethodList.setText(DEFAULT_METHOD);
        ObservableList<MenuItem> items = httpMethodList.getItems();
        for (String method : httpMethod) {
            MenuItem menuItem = new MenuItem(method);
            menuItem.setOnAction(event -> {
                System.out.println("onHttpMehodSelected");
                MenuItem source = (MenuItem) event.getSource();
                String text = source.getText();
                httpMethodList.setText(text);
            });
            items.add(menuItem);
        }
    }
}
