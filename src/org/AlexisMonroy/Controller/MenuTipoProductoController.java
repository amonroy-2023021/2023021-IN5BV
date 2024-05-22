
package org.AlexisMonroy.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.AlexisMonroy.DB.Conexion;
import org.AlexisMonroy.System.Main;
import org.AlexisMonroy.bean.TipoProducto;

/**
 *
 * @author Dell
 */
public class MenuTipoProductoController implements Initializable {

    
    private Main escenarioPrincipalTipoProducto;
    
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TipoProducto> listaTipoProducto;
    @FXML private Button btnBack;
    @FXML private TextField txtCodTipoPro;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblTipoProducto;
    @FXML private TableColumn colCodTipoPro;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    
    public void cargarDatos(){
       tblTipoProducto.setItems(getTipoProducto());
       colCodTipoPro.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("codigoTipoProducto"));
       colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("Descripcion"));
       
        
    }
    
    public void seleccionarElementos(){
        txtCodTipoPro.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
       txtDescripcion.setText(((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion());
       
    }
    
    public ObservableList<TipoProducto> getTipoProducto(){
        ArrayList<TipoProducto> lista = new ArrayList<>();
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProductos()}");
                ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                    lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                            resultado.getString("Descripcion")
                    ));
                    
                    
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        
        
        return listaTipoProducto = FXCollections.observableArrayList(lista);
    }
    
    public void Agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/AlexisMonroy/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/AlexisMonroy/images/cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                 guardar();
                 activarControles();
                 limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/AlexisMonroy/images/AgregarPro.png"));
                imgEliminar.setImage(new Image("/org/AlexisMonroy/images/EliminarPro.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
        
        
    }
     
    public void guardar(){
        TipoProducto registro = new TipoProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodTipoPro.getText()));
        registro.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoProductos( ?, ?) }");
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
            listaTipoProducto.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
       switch(tipoDeOperaciones){
           case ACTUALIZAR:
               desactivarControles();
               limpiarControles();  
               btnAgregar.setText("Agregar");
               btnEliminar.setText("Eliminar");
               btnEditar.setDisable(false);
               btnReporte.setDisable(false);
               imgAgregar.setImage(new Image("/org/AlexisMonroy/images/AgregarPro.png"));
               imgEliminar.setImage(new Image("/org/AlexisMonroy/images/EliminarPro.png"));
               tipoDeOperaciones = operaciones.NINGUNO;
               break;
           default:
               if(tblTipoProducto.getSelectionModel().getSelectedItem() != null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Tipo de Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoProductos(?) }");
                            procedimiento.setInt(1,((TipoProducto)tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listaTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
                        }catch (Exception e){
                            e.printStackTrace();
                        }   
                   }
               }else{
                   JOptionPane.showMessageDialog(null, " Debe de Seleccionar un Elemento ");
               }
       } 
    }
    
    
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                
                if(tblTipoProducto.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/AlexisMonroy/images/guardar.png"));
                    imgReporte.setImage(new Image("/org/AlexisMonroy/images/cancelar.png"));
                    activarControles();
                    txtCodTipoPro.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/AlexisMonroy/images/guardar.png"));
                imgReporte.setImage(new Image("/org/AlexisMonroy/images/cancelar.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }        
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTipoProductos(?, ?) }");
            TipoProducto registro = (TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void reporte(){
        switch (tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/AlexisMonroy/images/EditarPro.png"));
                imgReporte.setImage(new Image("/org/AlexisMonroy/images/ReportPro.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }
    
    public void desactivarControles(){
        txtCodTipoPro.setEditable(false);
        txtDescripcion.setEditable(false);
        
    }
    
     public void activarControles(){
        txtCodTipoPro.setEditable(true);
        txtDescripcion.setEditable(true);
        
    }
    
    public void limpiarControles(){
        txtCodTipoPro.clear();
        txtDescripcion.clear();
        
    }   

    public Main getEscenarioPrincipalTipoProducto() {
        return escenarioPrincipalTipoProducto;
    }

    public void setEscenarioPrincipalTipoProducto(Main escenarioPrincipalTipoProducto) {
        this.escenarioPrincipalTipoProducto = escenarioPrincipalTipoProducto;
    }
        
    
    
    
    public void clickAtras (ActionEvent event) throws IOException{
        if(event.getSource() == btnBack){
            escenarioPrincipalTipoProducto.menuPrincipalView();
        
        }
    }
    
}

