package io.sufeng.module.web;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author: sufeng
 * @create: 2020-01-19 11:22
 */
public class WebModuleControl implements Initializable {


    @FXML
    public WebView webview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine engine = webview.getEngine();
        webview.setZoom(0.7);
        engine.setOnResized(
                new EventHandler<WebEvent<Rectangle2D>>() {
                    public void handle(WebEvent<Rectangle2D> ev) {
                        Rectangle2D r = ev.getData();
                        webview.setMinWidth(r.getWidth());
                        webview.setMinHeight(r.getHeight());
                    }
                });
        engine.load("http://www.jsons.cn/urlencode/");

    }
}
