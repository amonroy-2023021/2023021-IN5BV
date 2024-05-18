
package org.AlexisMonroy.System;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.AlexisMonroy.Controller.MenuClientesController;
import org.AlexisMonroy.Controller.MenuPrincipalController;
import org.AlexisMonroy.Controller.ProgramadorController;
import org.AlexisMonroy.Controller.MenuProveedoresController;
import org.AlexisMonroy.Controller.MenuTipoProductoController;

/**
 *Documentacion
  -Nombre Mario Andreé Rodríguez Zamboni
  -Fecha de creacion: 11/04
  -Fecha de modificacion: 11/04
  -@autor mrodriguez-2023292
 **/

public class Main extends Application {
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/AlexisMonroy/View/";
            
    /* FXMLLoader, Parent, / que se usa para separar*/
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("MiNiMarket");
        escenarioPrincipal.getIcons().add(new Image(Main.class.getResourceAsStream("/org/AlexisMonroy/images/Logo recortado1.png")));
        menuPrincipalView();
        //Parent root = FXMLLoader.load(getClass().getResource("/org/mariorodriguez/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        escena = new Scene((AnchorPane)loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        
        return resultado;
    }
    
    public void menuPrincipalView (){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 612, 400);
            menuPrincipalView.setEscenarioPrincipal(this);  
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void MenuClientesView(){
        try{
            MenuClientesController MenuClientesView = (MenuClientesController)cambiarEscena("MenuClientesView.fxml", 858, 483);
            MenuClientesView.setEscenarioPrincipalClientes(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ProveedorView(){
            try{
                MenuProveedoresController MenuProveedoresView = (MenuProveedoresController)cambiarEscena
                ("MenuProveedoresView.fxml", 1181,665);
                MenuProveedoresView.setEscenarioPrincipalProveedores(this);
            }catch(Exception e){
            e.printStackTrace();
            }
    }
    
    public void programadorView(){
        try{
            ProgramadorController programadorView = (ProgramadorController)cambiarEscena("ProgramadorView.fxml", 714, 405);
            programadorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void TipoProductoView(){
        try{
            MenuTipoProductoController TipoProductoView = (MenuTipoProductoController)cambiarEscena("TipoProductoView.fxml", 770, 435);
            TipoProductoView.setEscenarioPrincipalTipoProducto(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
