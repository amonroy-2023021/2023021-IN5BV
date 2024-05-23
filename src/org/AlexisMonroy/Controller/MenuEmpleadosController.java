
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
import javafx.scene.control.ComboBox;
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
import org.AlexisMonroy.bean.Empleados;

public class MenuEmpleadosController implements Initializable {

    private Main escenarioPrincipalEmpleados;
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Cargos> listaCargos;
    @FXML private TableView tblEmpleados;
    @FXML private TextField txtCodEmpleado;
    @FXML private TextField txtNombreE;
    @FXML private TextField txtApellidoE;
    @FXML private TextField txtSueldoE;
    @FXML private TextField txtDireccionE;
    @FXML private TextField txtTurno;    
    @FXML private ComboBox cmbCargoEmpleado;
    @FXML private TableColumn colCodEmpleado;
    @FXML private TableColumn colNombreE;
    @FXML private TableColumn colApellidoE;
    @FXML private TableColumn colSueldoE;
    @FXML private TableColumn colDireccionE;
    @FXML private TableColumn colTurno;
    @FXML private TableColumn colCargoEmpleado;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCargoEmpleado.setItems(getCargos());
    }

    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
        colCodEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("CodEmpleado"));
        colNombreE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colApellidoE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
        colSueldoE.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccionE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("DireccionE"));
        colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("Turno"));
        colCargoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("cargoEmpleado"));
    }

    public void seleccionarElementos() {
        txtCodEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNombreE.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
        txtApellidoE.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
        txtSueldoE.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        cmbCargoEmpleado.getSelectionModel().select(buscarCargo(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getGetCodigoCargoEmpleado()));
        txtDireccionE.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado()));
        txtTurno.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));

    }

    public Cargos buscarCargo(int cargoId) {
        Cargos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCargos(?)}");
            procedimiento.setInt(1, cargoId);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Cargos(registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;

    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(
                        resultado.getInt("CodEmpleado"),
                        resultado.getString("nombreEmpleado"),
                        resultado.getString("apellidoEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("DireccionE"),
                        resultado.getString("Turno"),
                        resultado.getInt("cargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEmpleados = FXCollections.observableArrayList(lista);

    }

    public ObservableList<Cargos> getCargos() {
        ArrayList<Cargos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Cargos(resultado.getInt("cargoId"),
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
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodEmpleado.getText()));
        registro.setNombreEmpleado(txtNombreE.getText());
        registro.setApellidoEmpleado(txtApellidoE.getText());
        registro.setDireccionEmpleado(txtDireccionE.getText());
        registro.setTurno(txtTurno.getText());
        registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmpleados(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setString(4, registro.getDireccionEmpleado());
            procedimiento.setString(5, registro.getTurno());
            procedimiento.setDouble(6, registro.getSueldo());
            procedimiento.setInt(7, registro.getGetCodigoCargoEmpleado());
            procedimiento.execute();
            listaEmpleados.add(registro);
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
                imgAgregar.setImage(new Image("/org/harolrodriguez/images/AgregarC.png"));
                imgEliminar.setImage(new Image("/org/harolrodriguez/images/EliminarC.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmpleado(?) }");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, " Debe de Seleccionar un Elemento ");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:

                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/AlexisMonroy/images/Editar.png"));
                    imgReporte.setImage(new Image("/org/AlexisMonroy/images/reporte.png"));
                    activarControles();
                    txtCodEmpleado.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Elemento");
                }
                break;
            case ACTUALIZAR:

                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/AlexisMonroy/images/Agregar.png"));
                imgReporte.setImage(new Image("/org/AlexisMonroy/images/reporte.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_editarEmpleado(?, ?, ?, ?, ?, ?, ?) }");
            Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setCodigoEmpleado(Integer.parseInt(txtCodEmpleado.getText()));
            registro.setGetCodigoCargoEmpleado(((Cargos) cmbCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
            registro.setNombreEmpleado(txtNombreE.getText());
            registro.setApellidoEmpleado(txtApellidoE.getText());
            registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccionEmpleado());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getGetCodigoCargoEmpleado());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
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
        txtCodEmpleado.setEditable(false);
        txtNombreE.setEditable(false);
        txtApellidoE.setEditable(false);
        txtSueldoE.setEditable(false);
        txtTurno.setEditable(false);
        txtDireccionE.setEditable(false);
        cmbCargoEmpleado.setEditable(true);
    }

    public void activarControles() {
        txtCodEmpleado.setEditable(true);
        txtNombreE.setEditable(true);
        txtApellidoE.setEditable(true);
        txtSueldoE.setEditable(true);
        txtTurno.setEditable(true);
        txtDireccionE.setEditable(true);
        cmbCargoEmpleado.setEditable(false);
    }

    public void limpiarControles() {
        txtCodEmpleado.clear();
        txtNombreE.clear();
        txtApellidoE.clear();
        txtSueldoE.clear();
        txtTurno.clear();
        txtDireccionE.clear();
    }

    public Main getEscenarioPrincipalEmpleados() {
        return escenarioPrincipalEmpleados;
    }

    public void setEscenarioPrincipalEmpleados(Main escenarioPrincipalEmpleados) {
        this.escenarioPrincipalEmpleados = escenarioPrincipalEmpleados;
    }

    

    public void clickAtras(ActionEvent event) throws IOException {
        if (event.getSource() == btnBack) {
            escenarioPrincipalEmpleados.menuPrincipalView();
        
    }

}
}
