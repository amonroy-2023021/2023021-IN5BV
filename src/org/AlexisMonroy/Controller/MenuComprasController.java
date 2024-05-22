package org.AlexisMonroy.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.AlexisMonroy.DB.Conexion;
import org.AlexisMonroy.System.Main;
import org.AlexisMonroy.bean.Compras;

public class MenuComprasController implements Initializable {
        private Main escenarioPrincipalCompras;
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Compras> listaCompras;
    @FXML private Button btnBack;
    @FXML private TextField txtNumDoc;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtTotalDoc;
    @FXML private DatePicker txtfechaDoc;
    @FXML private TextField txttotalDoc;
    @FXML private TableView tblCompras;
    @FXML private TableColumn colNumDoc;
    @FXML private TableColumn colfechaDoc;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn coltotalDoc;
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

    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colNumDoc.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("NumeroDocumento"));
        colfechaDoc.setCellValueFactory(new PropertyValueFactory<Compras, LocalDate>("fechaDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        coltotalDoc.setCellValueFactory(new PropertyValueFactory<Compras, String>("totalDocumento"));
    }

    public void selecdcionarElemento() {
        txtNumDoc.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtDescripcion.setText((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion()));
        txttotalDoc.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));

    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                cargarDatos();
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
                cargarDatos();
                guardar();
                activarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/AlexisMonroy/images/Agregarco.png"));
                imgEliminar.setImage(new Image("/org/AlexisMonroy/images/Eliminarco.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }

    }

    public void guardar() {
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumDoc.getText()));
        LocalDate fechaN = txtfechaDoc.getValue();
        Date fechaC = Date.valueOf(fechaN);
        registro.setDescripcion(txtDescripcion.getText());
        registro.setTotalDocumento(Double.parseDouble(txtTotalDoc.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompras(?, ?, ?, ?) }");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setDate(2, fechaC);
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                java.sql.Date fecha = resultado.getDate("fechaDocumento");
                LocalDate fechaDP = fecha.toLocalDate();
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        fechaDP,
                        resultado.getString("Descripcion"),
                        resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCompras = FXCollections.observableArrayList(lista);

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
                imgAgregar.setImage(new Image("/org/AlexisMonroy/images/Agregarco.png"));
                imgEliminar.setImage(new Image("/org/AlexisMonroy/images/Eliminarco.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompras(?) }");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
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

                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/AlexisMonroy/images/guardar.png"));
                    imgReporte.setImage(new Image("/org/AlexisMonroy/images/ReportPro.png"));
                    activarControles();
                    txtNumDoc.setEditable(false);
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
                imgEditar.setImage(new Image("/org/AlexisMonroy/images/Editarco.png"));
                imgReporte.setImage(new Image("/org/AlexisMonroy/images/ReportPro.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCompras( ?, ?, ?) }");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.setDouble(3, registro.getTotalDocumento());
            
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
                imgEditar.setImage(new Image("/org/AlexisMonroy/images/Editarco.png"));
                imgReporte.setImage(new Image("/org/AlexisMonroy/images/ReportPro.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void desactivarControles() {
        txtNumDoc.setEditable(false);
        txtfechaDoc.setEditable(false);
        txtDescripcion.setEditable(false);

    }

    public void activarControles() {
        txtNumDoc.setEditable(true);
        txtfechaDoc.setEditable(true);
        txtDescripcion.setEditable(true);
    }

    public void limpiarControles() {
        txtNumDoc.clear();
        txtDescripcion.clear();
        txttotalDoc.clear();
    }

    public Main getEscenarioPrincipalCompras() {
        return escenarioPrincipalCompras;
    }
    public void setEscenarioPrincipalCompras(Main escenarioPrincipalCompras) {
        this.escenarioPrincipalCompras = escenarioPrincipalCompras;
    }

    public void clickAtras(ActionEvent event) throws IOException {
        if (event.getSource() == btnBack) {
            escenarioPrincipalCompras.menuPrincipalView();
        
        }
    }
}
