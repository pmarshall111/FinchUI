package com.petermarshall.controller;

import com.petermarshall.view.ViewManager;
import javafx.fxml.FXML;

//The controller for view/mainMenu.fxml
public class MainMenu {
    @FXML
    private void goToSearchForLight() {
        ViewManager.showSearchForLight();
    }
}