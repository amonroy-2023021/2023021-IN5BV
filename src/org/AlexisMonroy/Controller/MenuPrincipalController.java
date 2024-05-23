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
import javafx.scene.control.MenuItem;
import org.AlexisMonroy.System.Main;
 
/*
Herencia multiple concepto, Interfaces. POO
*/
public class MenuPrincipalController implements Initializable  {
    private Main escenarioPrincipal;
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador;
    @FXML MenuItem btnProveedores;
    @FXML MenuItem btnTipoProducto;
    @FXML MenuItem btnCompras;
    @FXML MenuItem btnProductos;
    @FXML MenuItem btnCargos;
   
 
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
    public void buttonHandleEvent (ActionEvent event){
         if (event.getSource() == btnMenuClientes){
            escenarioPrincipal.MenuClientesView();
        }else if(event.getSource() == btnProgramador){
            escenarioPrincipal.programadorView();
        }else if(event.getSource() == btnProveedores){
            escenarioPrincipal.ProveedorView();
        }else if (event.getSource() == btnTipoProducto){
            escenarioPrincipal.TipoProductoView();
        }else if (event.getSource() == btnCompras){
            escenarioPrincipal.MenuComprasView();
        }else if (event.getSource() == btnProductos){
            escenarioPrincipal.ProductosView();
        }else if (event.getSource() == btnCargos){
            escenarioPrincipal.CargosView();
        }
    }
}
    

