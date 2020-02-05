package com.petermarshall.view;

import com.petermarshall.model.FinchLiveData;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class ViewManager {

    private static Stage mainStage;
    private static ViewManager viewManager;
    //required to have singleton within static class because getClass() method used to load in fxml cannot be called from static context.
    //could make decision to initialise all controllers through code rather than fxml, and pass in viewManager as argument to them? Will see how messy it gets.
    private static ArrayList<Stage> popupSearchStages;
    private final static int stageHeight = 850;
    private final static int stageWidth = 950;

    public static void init(Stage stage) {
        mainStage = stage;
        mainStage.setTitle("Finch UI");
        mainStage.getIcons().add(new Image("https://lh3.googleusercontent.com/meJELhcTj6NeEK2o8IvU2_QOLI8YPwg2L1m3mYf5CmxvO4v2gL-mQIw52Ucw6ENoY1jYzgl5N6A=w128-h128-e365"));
        //TODO: get working from local file

        viewManager = new ViewManager();
        popupSearchStages = new ArrayList<>();
    }

    public static void showMainMenu() {
        viewManager.showMainMenuInstance();
    }

    private void showMainMenuInstance() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            mainStage.setScene(new Scene(root, stageWidth, stageHeight));
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
            mainStage.setScene(new Scene(root, stageWidth, stageHeight));
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
            //popup.initModality(Modality.APPLICATION_MODAL); Unsure whether this is needed, or the line above. Would potentially block the user from doing anything with the old window before closing.
            //would probably prefer the user to be able to keep that window open to compare runs.
            Parent root = FXMLLoader.load(getClass().getResource("finalTelemetry.fxml"));
            popup.setScene(new Scene(root, stageWidth, stageHeight));
            popup.show();
            popup.setTitle("Finch UI");
            popup.getIcons().add(new Image("https://lh3.googleusercontent.com/meJELhcTj6NeEK2o8IvU2_QOLI8YPwg2L1m3mYf5CmxvO4v2gL-mQIw52Ucw6ENoY1jYzgl5N6A=w128-h128-e365"));
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