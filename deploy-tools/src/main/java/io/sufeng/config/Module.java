package io.sufeng.config;

import io.sufeng.view.base.BaseView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * @author: sufeng
 * @create: 2020-01-19 11:20
 */
public enum Module {

    HTTP("Http请求", "ui/http.fxml"),
    ENCRYPTION("加解密","ui/encryption.fxml"),
    URL_CODING("URL编码/解码", "ui/urlencoding.fxml"),
    API_MOCK("接口模拟", "ui/http.fxml");

    String name;
    String fxml;

    Module(String name, String fxml) {
        this.name = name;
        this.fxml = fxml;
    }

    public String getName() {
        return name;
    }

    public String getFxml() {
        return fxml;
    }


    public Parent getNode() throws IOException{
        URL resource = BaseView.class.getClassLoader().getResource(getFxml());
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        return fxmlLoader.load();
    }
}
