package io.sufeng.module;

import io.sufeng.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * @author: sufeng
 * @create: 2020-01-19 11:20
 */
public enum Module {

    HTTP("Http请求", "ui/view_http.fxml"),
    ENCRYPTION("加解密","ui/view_encryption.fxml"),
    URL_CODING("URL编码/解码", "ui/view_url_encoding.fxml") ;

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
        String fxml = getFxml();
        System.out.println(fxml);
        return FXMLLoader.load(MainApplication.class.getClassLoader().getResource(fxml));
    }
}
