
package org.AlexisMonroy.Controller;

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
import org.AlexisMonroy.bean.Proveedores;
import org.AlexisMonroy.DB.Conexion;
import org.AlexisMonroy.System.Main;

public class MenuProveedoresController implements Initializable   {
    private Main setEscenarioPrincipalProveedores;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Proveedores> listaProveedores;
    @FXML private Button btnRegresar3;
    @FXML private TextField txtContactoP;
    @FXML private TextField txtDireccionP;
    @FXML private TextField txtCodigoP;
    @FXML private TextField txtNitP;
    @FXML private TextField txtNombreP;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtRazonP;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colCodigoP;
    @FXML private TableColumn colContactoP;
    @FXML private TableColumn colDireccionP;
    @FXML private TableColumn colNitP;
    @FXML private TableColumn colNombreP;
    @FXML private TableColumn colApellidoP;
    @FXML private TableColumn colRazonP;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
       tblProveedores.setItems(getProveedores());
       colCodigoP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
       colNitP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITproveedor"));
       colNombreP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
       colApellidoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
       colRazonP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));       
       colContactoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoProveedor"));
       colDireccionP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
    }
    
    public void seleccionarElemento(){
        txtCodigoP.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNITproveedor()));
        txtNombreP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor()));
        txtApellidoP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor()));
        txtRazonP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial()));
        txtContactoP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getContactoProveedor()));
        txtDireccionP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor()));  
    }
    
    public ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores>lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores (resultado.getInt("codigoProveedor"),
                                        resultado.getString("NITProveedor"),
                                        resultado.getString("nombreProveedor"),
                                        resultado.getString("apellidoProveedor"),
                                        resultado.getString("razonSocial"),
                                        resultado.getString("contactoProveedores"),
                                        resultado.getString("direccionProveedor")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableArrayList(lista);
    }
    
    public void agregar(){
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("guardar");
                btnEliminar.setText("cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/AlexisMonroy/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/AlexisMonroy/images/cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/AlexisMonroy/images/AgregarProv.png"));
                imgEliminar.setImage(new Image("/org/AlexisMonroy/images/EliminarProv.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                throw new AssertionError();
        }
    }
    
    public void guardar(){
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
        registro.setNITproveedor(txtNitP.getText());
        registro.setNombreProveedor(txtNombreP.getText());
        registro.setApellidoProveedor(txtApellidoP.getText());
        registro.setDireccionProveedor(txtDireccionP.getText());
        registro.setRazonSocial(txtRazonP.getText());
        registro.setContactoProveedor(txtContactoP.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getRazonSocial());
            procedimiento.setString(6, registro.getContactoProveedor());
            procedimiento.setString(7, registro.getDireccionProveedor());
            procedimiento.execute();
            listaProveedores.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("AGREGAR");
                btnEliminar.setText("ELIMINAR");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/AlexisMonroy/images/AgregarProv.png"));
                imgEliminar.setImage(new Image("/org/AlexisMonroy/images/EliminarProv.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar para eliminar el registro", "Confirmacion Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedores(?)}");
                            procedimiento.setInt(1, ((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento!");
                }
        }
    }
    
    public void editar(){
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("actualizar");
                    btnReporte.setText("cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/AlexisMonroy/images/guardar.png"));
                    imgReporte.setImage(new Image("/org/AlexisMonroy/images/cancelar.png"));
                    activarControles();
                    txtCodigoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar algun elemento!");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/AlexisMonroy/images/EditarProv.png"));
                imgReporte.setImage(new Image("/org/AlexisMonroy/images/ReporteProv.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
            default:
                throw new AssertionError();
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedores(?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = ((Proveedores)tblProveedores.getSelectionModel().getSelectedItem());
            registro.setNITproveedor(txtNitP.getText());
            registro.setNombreProveedor(txtNombreP.getText());
            registro.setApellidoProveedor(txtApellidoP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonP.getText());
            registro.setContactoProveedor(txtContactoP.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getRazonSocial());            
            procedimiento.setString(6, registro.getContactoProveedor());
            procedimiento.setString(7, registro.getDireccionProveedor());
            procedimiento.execute();
            listaProveedores.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("EDITAR");
                btnReporte.setText("REPORTES");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/AlexisMonroy/images/EditarProv.png"));
                imgReporte.setImage(new Image("/org/AlexisMonroy/images/ReportesProv.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
            default:
                throw new AssertionError();
        }
    }
    
    public void desactivarControles(){
        txtCodigoP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtContactoP.setEditable(false);
        txtNitP.setEditable(false);
        txtNombreP.setEditable(false);
        txtApellidoP.setEditable(false);
        txtRazonP.setEditable(false);
        
    }
    
    public void activarControles(){
        txtCodigoP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtContactoP.setEditable(true);
        txtNitP.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true);
        txtRazonP.setEditable(true);
        
    }
    
    public void limpiarControles(){
        txtCodigoP.clear();
        txtDireccionP.clear();
        txtContactoP.clear();
        txtNitP.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtRazonP.clear();
    }

    public Main getSetEscenarioPrincipalProveedores() {
        return setEscenarioPrincipalProveedores;
    }
    public void setSetEscenarioPrincipalProveedores(Main setEscenarioPrincipalProveedores) {
        this.setEscenarioPrincipalProveedores = setEscenarioPrincipalProveedores;
    }
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar3){
            setEscenarioPrincipalProveedores.menuPrincipalView();
        }
    }
}
