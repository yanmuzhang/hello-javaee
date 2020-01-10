package io.sufeng.view.home;

import io.sufeng.http.HttpClient;
import io.sufeng.view.textpage1.View1;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.http.entity.ContentType;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author: sufeng
 * @create: 2020-01-10 09:58
 */
public class HomeControl implements Initializable {




    static String[] httpMethod = new String[]{"GET","POST","PUT","DELETE"};

    private static final String DEFAULT_METHOD = httpMethod[0];

    @FXML
    public MenuButton uiHttpMethod;
    @FXML
    public TextField httpPath;
    @FXML
    public ToggleGroup groupType;
    @FXML
    public TextArea httpResponseLog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("HomeControl -> initialize");
        uiHttpMethod.setText(DEFAULT_METHOD);
        ObservableList<MenuItem> items = uiHttpMethod.getItems();
        for (String method : httpMethod) {
            MenuItem menuItem = new MenuItem(method);
            menuItem.setOnAction(event -> {
                System.out.println("onHttpMehodSelected");
                MenuItem source = (MenuItem)event.getSource();
                String text = source.getText();
                uiHttpMethod.setText(text);
            });
            items.add(menuItem);
        }
    }



    public void onExecCliced(MouseEvent mouseEvent) {
        System.out.println("onExecCliced");
        String path = httpPath.getText();
        String method = uiHttpMethod.getText();
        String userData =  groupType.getSelectedToggle().getUserData().toString();
        System.out.println(path+":"+method+":"+userData);

        String result = HttpClient.DEFAULT.request(path, method, ContentType.create(userData), Collections.EMPTY_MAP);

        httpResponseLog.setText(result);
    }

    public void handlerBtnClick(ActionEvent actionEvent) {
        System.out.println("handlerBtnClick");
    }
}
