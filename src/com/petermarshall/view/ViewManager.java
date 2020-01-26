package com.petermarshall.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewManager {

    private static Stage mainStage;
    private static ViewManager viewManager;
    //required to have singleton within static class because getClass() method used to load in fxml cannot be called from static context.
    //could make decision to initialise all controllers through code rather than fxml, and pass in viewManager as argument to them? Will see how messy it gets.
    private static ArrayList<Stage> popupSearchStages;
    private final static int stageHeight = 800;
    private final static int stageWidth = 700;

    public static void init(Stage stage) {
        mainStage = stage;
        mainStage.setTitle("Finch UI - Assignment 2");

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