package com.petermarshall.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewManager {

    private static Stage mainStage;
    private static ViewManager viewManager;
    //required to have singleton within static class because getClass() method used to load in fxml cannot be called from static context.
    //could make decision to initialise all controllers through code rather than fxml, and pass in viewManager as argument to them? Will see how messy it gets.

    public static void init(Stage stage) {
        mainStage = stage;
        mainStage.setTitle("Finch UI - Assignment 2");

        viewManager = new ViewManager();
    }

    public static void showMainMenu() {
        viewManager.showMainMenuInstance();
    }

    private void showMainMenuInstance() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            mainStage.setScene(new Scene(root, 600, 600));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showSearchForLight() {
        viewManager.showSearchForLightInstance();
    }

    private void showSearchForLightInstance() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("searchForLight.fxml"));
            mainStage.setScene(new Scene(root, 600, 600));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showFinalTelemetry() {
        viewManager.showFinalTelemetryInstance();
    }

    private void showFinalTelemetryInstance() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("finalTelemetry.fxml"));
            mainStage.setScene(new Scene(root, 600, 600));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}