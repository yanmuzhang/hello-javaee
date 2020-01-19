package io.sufeng;

import com.sun.javafx.application.LauncherImpl;
import io.sufeng.view.home.HomeView;

/**
 * @author: sufeng
 * @create: 2020-01-10 13:38
 */
public class MainApplication {
    public static void main(String[] args) {
        LauncherImpl.launchApplication(HomeView.class,args);
    }
}
