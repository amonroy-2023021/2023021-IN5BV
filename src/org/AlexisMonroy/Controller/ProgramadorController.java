/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.AlexisMonroy.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.AlexisMonroy.System.Main;

/**
 *
 * @author informatica
 */
public class ProgramadorController implements Initializable   {
    private Main escenarioPrincipal;
    @FXML Button btnRegresar2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
      public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
 
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
 
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar2){
            escenarioPrincipal.menuPrincipalView();
        }
    }

    
}