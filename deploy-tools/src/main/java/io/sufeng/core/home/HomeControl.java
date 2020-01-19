package io.sufeng.core.home;

import io.sufeng.module.Module;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author: sufeng
 * @create: 2020-01-10 09:58
 */
public class HomeControl implements Initializable {


    static String[] httpMethod = new String[]{"GET","POST","PUT","DELETE"};


    Map<String,Tab> tabMap = new HashMap<>();

    private static final String DEFAULT_METHOD = httpMethod[0];
    @FXML
    public TabPane tabPane;
//
//    @FXML
//    public MenuButton uiHttpMethod;
//    @FXML
//    public TextField httpPath;
//    @FXML
//    public ToggleGroup groupType;
//    @FXML
//    public TextArea httpResponseLog;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("HomeControl -> initialize");
//        uiHttpMethod.setText(DEFAULT_METHOD);
//        ObservableList<MenuItem> items = uiHttpMethod.getItems();
//        for (String method : httpMethod) {
//            MenuItem menuItem = new MenuItem(method);
//            menuItem.setOnAction(event -> {
//                System.out.println("onHttpMehodSelected");
//                MenuItem source = (MenuItem)event.getSource();
//                String text = source.getText();
//                uiHttpMethod.setText(text);
//            });
//            items.add(menuItem);
//        }
        ObservableList<Tab> tabs = tabPane.getTabs();
        tabPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getTarget() instanceof  Text){
                    System.out.println(((Text)event.getTarget()).getText());
                }
            }
        });

        for (Module value : Module.values()) {
            Tab tab = new Tab(value.getName());
            try {
                tab.setContent(value.getNode());
            } catch (IOException e) {
                e.printStackTrace();
            }
            tabs.add(tab);
            tabMap.put(value.getName(),tab);
        }

        System.out.println("HomeControl -> initialize");
    }



    public void onExecCliced(MouseEvent mouseEvent) {
//        System.out.println("onExecCliced");
//        String path = httpPath.getText();
//        String method = uiHttpMethod.getText();
//        String userData =  groupType.getSelectedToggle().getUserData().toString();
//        System.out.println(path+":"+method+":"+userData);
//
//        String result = HttpClient.INSTANCE.request(path, method, ContentType.create(userData), Collections.EMPTY_MAP);
//
//        httpResponseLog.setText(result);
    }

    public void handlerBtnClick(ActionEvent actionEvent) {
        System.out.println("handlerBtnClick");
    }
}
