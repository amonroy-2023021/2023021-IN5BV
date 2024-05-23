
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
import org.AlexisMonroy.bean.Cargos;

/**
 *
 * @author Dell
 */
public class MenuCargosController implements Initializable {
    private Main escenarioPrincipalCargos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Cargos> listaCargos;
    @FXML private Button btnAgregar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgReporte;
    @FXML private TableView tblCargos;
    @FXML private TableColumn colCargoEmp;
    @FXML private TableColumn colNombreCargo;
    @FXML private TableColumn colDescripcionCargo;
    @FXML private TextField txtCargoEmp;
    @FXML private TextField txtNombreCargo;
    @FXML private TextField txtDescripcionC;
    @FXML private Button btnBack;

    public void cargarDatos() {
        tblCargos.setItems(getCargos());
        colCargoEmp.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("codigoCargoEmpleado"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("descripcionCargo"));
    }
    
    public void selecdcionarElemento(){
        txtCargoEmp.setText(String.valueOf(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreCargo.setText(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcionC.setText(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo());
    }
    
    public ObservableList<Cargos> getCargos() {
        ArrayList<Cargos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Cargos(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCargos = FXCollections.observableArrayList(lista);

    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
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
                imgAgregar.setImage(new Image("/org/AlexisMonroy/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/AlexisMonroy/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }

    }

    public void guardar() {
        Cargos registro = new Cargos();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCargoEmp.getText()));
        registro.setNombreCargo(txtNombreCargo.getText());
        registro.setDescripcionCargo(txtDescripcionC.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargoEmpleado(?, ? , ?) }");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
            listaCargos.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/AlexisMonroy/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/AlexisMonroy/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblCargos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Cargos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargoEmpleado(?) }");
                            procedimiento.setInt(1, ((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listaCargos.remove(tblCargos.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, " Debe de Seleccionar un Elemento ");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                
                if(tblCargos.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/AlexisMonroy/images/guardar.png"));
                    imgReporte.setImage(new Image("/org/AlexisMonroy/images/cancelar.png"));
                    activarControles();
                    txtCargoEmp.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Elemento");
                break;
            case ACTUALIZAR:
                
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/AlexisMonroy/images/Editar.png"));
                imgReporte.setImage(new Image("/org/AlexisMonroy/images/reporte.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }        
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCargoEmpleado(?, ?, ?) }");
            Cargos registro = (Cargos) tblCargos.getSelectionModel().getSelectedItem();
            registro.setCodigoCargoEmpleado(Integer.parseInt(txtCargoEmp.getText()));
            registro.setNombreCargo(txtNombreCargo.getText());
            registro.setDescripcionCargo(txtDescripcionC.getText());
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
        } catch (Exception e) {
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
                imgEditar.setImage(new Image("/org/AlexisMonroy/images/Editar.png"));
                imgReporte.setImage(new Image("/org/AlexisMonroy/images/reporte.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void desactivarControles() {
        txtCargoEmp.setEditable(false);
        txtNombreCargo.setEditable(false);
        txtDescripcionC.setEditable(false);

    }

    public void activarControles() {
        txtCargoEmp.setEditable(true);
        txtNombreCargo.setEditable(true);
        txtDescripcionC.setEditable(true);

    }

    public void limpiarControles() {
        txtCargoEmp.clear();
        txtNombreCargo.clear();
        txtDescripcionC.clear();

    }

    public Main getEscenarioPrincipalCargos() {
        return escenarioPrincipalCargos;
    }

    public void setEscenarioPrincipalCargos(Main escenarioPrincipalCargos) {
        this.escenarioPrincipalCargos = escenarioPrincipalCargos;
    }

    
    
     public void clickAtras (ActionEvent event) throws IOException{
        if(event.getSource() == btnBack){
            escenarioPrincipalCargos.menuPrincipalView();
        }
    }
}
