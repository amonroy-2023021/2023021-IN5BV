
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
import org.AlexisMonroy.Controller.MenuCargosController;
import org.AlexisMonroy.Controller.MenuClientesController;
import org.AlexisMonroy.Controller.MenuComprasController;
import org.AlexisMonroy.Controller.MenuEmpleadosController;
import org.AlexisMonroy.Controller.MenuPrincipalController;
import org.AlexisMonroy.Controller.MenuProductosController;
import org.AlexisMonroy.Controller.ProgramadorController;
import org.AlexisMonroy.Controller.MenuProveedoresController;
import org.AlexisMonroy.Controller.MenuTipoProductoController;



public class Main extends Application {
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/AlexisMonroy/View/";
            
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("MiNiMarket");
        escenarioPrincipal.getIcons().add(new Image(Main.class.getResourceAsStream("/org/AlexisMonroy/images/Logo recortado1.png")));
        menuPrincipalView();
        
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
                MenuProveedoresController MenuProveedoresView = (MenuProveedoresController)cambiarEscena("MenuProveedoresView.fxml", 950,535);
                MenuProveedoresView.setSetEscenarioPrincipalProveedores(this);
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
    
    public void MenuComprasView(){
        try{
        MenuComprasController MenuComprasView = (MenuComprasController)cambiarEscena("MenuComprasView.fxml", 765, 429 );
        MenuComprasView.setEscenarioPrincipalCompras(this);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ProductosView(){
            try{
                MenuProductosController MenuProductosView = (MenuProductosController)cambiarEscena("MenuProductosView.fxml", 876,493);
                MenuProductosView.setEscenarioPrincipalProductos(this);
            }catch(Exception e){
            e.printStackTrace();
            }
    }
    
    public void CargosView(){
            try{
                MenuCargosController MenuCargosView = (MenuCargosController)cambiarEscena("MenuCargosView.fxml", 876,493);
                MenuCargosView.setEscenarioPrincipalCargos(this);
            }catch(Exception e){
            e.printStackTrace();
            }
    }
    public void EmpleadosView(){
            try{
                MenuEmpleadosController MenuEmpleadosView = (MenuEmpleadosController)cambiarEscena("MenuEmpleadosView.fxml", 950,535);
                MenuEmpleadosView.setEscenarioPrincipalEmpleados(this);
            }catch(Exception e){
            e.printStackTrace();
            }
    
   
    }
    
}
