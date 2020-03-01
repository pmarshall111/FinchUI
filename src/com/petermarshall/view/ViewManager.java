package com.petermarshall.view;

import com.petermarshall.controller.SearchForLightTask;
import com.petermarshall.model.FinchLiveData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

//This class determines which view is shown on the GUI. It's designed as a Singleton class, and has an internal instance.
//Methods are repeated from a static context to an instance context because the method @getClass cannot be called from a static
//context, but the static context is needed for controllers to call methods.
public class ViewManager {
    private static Stage mainStage;
    private static ViewManager viewManager;
    private static ArrayList<Stage> popupSearchStages;
    private final static int newWindowHeight = 700;
    private final static int newWindowWidth = 800;

    private ViewManager() {}

    public static void init(Stage stage) {
        mainStage = stage;
        mainStage.setTitle("Finch UI");
        mainStage.getIcons().add(new Image("https://lh3.googleusercontent.com/meJELhcTj6NeEK2o8IvU2_QOLI8YPwg2L1m3mYf5CmxvO4v2gL-mQIw52Ucw6ENoY1jYzgl5N6A=w128-h128-e365"));
        viewManager = new ViewManager();
        popupSearchStages = new ArrayList<>();
    }

    public static void showMainMenu() {
        viewManager.showMainMenuInstance();
    }

    private void showMainMenuInstance() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            //Different options for setting scene so that if the user changes the window dimensions, this is maintained
            //after a page change.
            if (mainStage.getScene() == null) {
                mainStage.setScene(new Scene(root, newWindowWidth, newWindowHeight));
            } else {
                double nWidth = Math.max(newWindowWidth, mainStage.getScene().getWidth());
                double nHeight = Math.max(newWindowHeight, mainStage.getScene().getHeight());
                mainStage.setScene(new Scene(root, nWidth, nHeight));
            }
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
            Parent root = FXMLLoader.load(getClass().getResource("searchForLightTask.fxml"));
            double nWidth = Math.max(newWindowWidth, mainStage.getScene().getWidth());
            double nHeight = Math.max(newWindowHeight, mainStage.getScene().getHeight());
            mainStage.setScene(new Scene(root, nWidth, nHeight));
            mainStage.show();
            mainStage.setOnCloseRequest(event -> FinchLiveData.stopProgram());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showFinalTelemetry() {
        viewManager.showFinalTelemetryInstance();
    }

    private void showFinalTelemetryInstance() {
        try {
            Stage popup = new Stage();
            popupSearchStages.add(popup);
            popup.initOwner(mainStage);
            Parent root = FXMLLoader.load(getClass().getResource("finalTelemetry.fxml"));
            popup.setScene(new Scene(root, newWindowWidth, newWindowHeight));
            popup.setTitle("Finch UI");
            popup.getIcons().add(new Image("https://lh3.googleusercontent.com/meJELhcTj6NeEK2o8IvU2_QOLI8YPwg2L1m3mYf5CmxvO4v2gL-mQIw52Ucw6ENoY1jYzgl5N6A=w128-h128-e365"));
            popup.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getNumbSearchPopups() {
        return popupSearchStages.size();
    }

    public static void closeSearchPopup(int _id) {
        popupSearchStages.get(_id-1).close();
    }


}