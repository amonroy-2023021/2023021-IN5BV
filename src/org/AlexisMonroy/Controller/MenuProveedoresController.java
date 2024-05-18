
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
import org.AlexisMonroy.bean.Proveedores;
import org.AlexisMonroy.DB.Conexion;
import org.AlexisMonroy.System.Main;

public class MenuProveedoresController implements Initializable{
    private Main escenarioPrincipalProveedores;

   private ObservableList<Proveedores> listaProveedores;
     private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperador = operador.NINGUNO;
    @FXML
    private TableView tblProveedores;
    @FXML
    private TableColumn colCodP;
    @FXML
    private TableColumn colNITP;
    @FXML
    private TableColumn colNomP;
    @FXML
    private TableColumn colApeP;
    @FXML
    private TableColumn colDireP;
    @FXML
    private TableColumn colRazonS;
    @FXML
    private TableColumn colContactoP;
    @FXML
    private TableColumn colSitioWeb;
    @FXML
    private Button btnAgregarP;
    @FXML
    private Button btnEditarP;
    @FXML
    private Button btnEliminarP;
    @FXML
    private Button btnReportesP;
    @FXML
    private TextField txtCodigoP;
    @FXML
    private TextField txtNITP;
    @FXML
    private TextField txtNombresP;
    @FXML
    private TextField txtApellidosP;
    @FXML
    private TextField txtDireccionP;
    @FXML private TextField txtRazonSocial;
    @FXML private TextField txtContactoP;
    @FXML private TextField txtSitioWeb;
    @FXML private Button btnRegresarP;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         cargarDatos();
       desactivarControles();
    }    

    
    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colCodP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNITP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITProveedor"));
        colNomP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombresProveedor"));
        colApeP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        colDireP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonS.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colSitioWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    public void seleccionarElemento() {
        txtCodigoP.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNITP.setText((((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNITproveedor()));
        txtNombresP.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor()));
        txtApellidosP.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor()));
        txtDireccionP.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor()));
        txtRazonSocial.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial()));
        txtContactoP.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal()));
        txtSitioWeb.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb()));
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaPro = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(listaPro);
    }

    public void Agregar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                activarControles();
                btnAgregarP.setText("Guardar");
                btnEliminarP.setText("Cancelar");
                btnEditarP.setDisable(true);
                btnReportesP.setDisable(true);
                //imgAgregar.setImage(new Image("URL"));
                tipoDeOperador = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();

                desactivarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReportesP.setDisable(false);
                /*regresar de nuevo a sus imagenes originales
                imgAgregar.setImage(new Image("URL"));*/
                tipoDeOperador = operador.NINGUNO;
                // cargarDatos();
                break;
        }
    }

    public void guardar() {
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
        registro.setNITproveedor(txtNITP.getText());
        registro.setNombreProveedor(txtNombresP.getText());
        registro.setApellidoProveedor(txtApellidosP.getText());
        registro.setDireccionProveedor(txtDireccionP.getText());
        registro.setRazonSocial(txtRazonSocial.getText());
        registro.setContactoPrincipal(txtContactoP.getText());
        registro.setPaginaWeb(txtSitioWeb.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores(?,?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            listaProveedores.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperador) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReportesP.setDisable(false);
                /*regresar de nuevo a sus imagenes originales
                imgAgregar.setImage(new Image("URL"));*/
                tipoDeOperador = operador.NINGUNO;
                break;
            default:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminacion del registro?", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedores(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para eliminar");
                }

                break;
        }
    }

    public void editar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("Actualizar");
                    btnReportesP.setText("Cancelar");
                    btnEliminarP.setDisable(true);
                    btnAgregarP.setDisable(true);
                    activarControles();
                    txtCodigoP.setEditable(false);
                    tipoDeOperador = operador.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarP.setText("Editar");
                btnReportesP.setText("Reportes");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperador = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reportes() {
        switch (tipoDeOperador) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarP.setText("Editar");
                btnReportesP.setText("Reportes");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                tipoDeOperador = operador.NINGUNO;

        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedores(?,?,?,?,?,?,?,?)}");
            Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
            registro.setNITproveedor(txtNITP.getText());
            registro.setNombreProveedor(txtNombresP.getText());
            registro.setApellidoProveedor(txtApellidosP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonSocial.getText());
            registro.setContactoPrincipal(txtContactoP.getText());
            registro.setPaginaWeb(txtSitioWeb.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoP.setEditable(false);
        txtNITP.setEditable(false);
        txtNombresP.setEditable(false);
        txtApellidosP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtContactoP.setEditable(false);
        txtSitioWeb.setEditable(false);
    }

    public void activarControles() {
        txtCodigoP.setEditable(true);
        txtNITP.setEditable(true);
        txtNombresP.setEditable(true);
        txtApellidosP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtContactoP.setEditable(true);
        txtSitioWeb.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoP.clear();
        txtNITP.clear();
        txtNombresP.clear();
        txtApellidosP.clear();
        txtDireccionP.clear();
        txtRazonSocial.clear();
        txtContactoP.clear();
        txtSitioWeb.clear();
    }

    public Main getEscenarioPrincipalProveedores() {
        return escenarioPrincipalProveedores;
    }

    public void setEscenarioPrincipalProveedores(Main escenarioPrincipalProveedores) {
        this.escenarioPrincipalProveedores = escenarioPrincipalProveedores;
    }
    @FXML
    private void clickMenuPrincipal(ActionEvent event) {
        if (event.getSource() == btnRegresarP) {
            escenarioPrincipalProveedores.menuPrincipalView();
        }
    }
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipalProveedores = escenarioPrincipal;
    }
    
}