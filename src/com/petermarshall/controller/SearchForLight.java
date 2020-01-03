package com.petermarshall.controller;

import com.petermarshall.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.swing.text.ViewFactory;

public class SearchForLight {

    @FXML
    private Button backBtn;

    @FXML
    void goToMainMenu() {
        ViewManager.showMainMenu();
    }



}
