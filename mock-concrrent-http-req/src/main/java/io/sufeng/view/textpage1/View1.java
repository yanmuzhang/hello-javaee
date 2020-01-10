package io.sufeng.view.textpage1;

import io.sufeng.view.base.BaseView;
import javafx.stage.WindowEvent;

public class View1 extends BaseView<TextControl> {

    @Override
    public String getViewName() {
        return "TestView.fxml";
    }

    @Override
    public String getViewStyle() {
        return null;
    }

    @Override
    public void onViewStart() {
        control.lableText.setText("这是新页面");
    }

    @Override
    public void onViewClose(WindowEvent event) {
        System.out.println("onViewClose");
    }
}
