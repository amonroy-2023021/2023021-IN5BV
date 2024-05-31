
package org.AlexisMonroy.bean;

public class Proveedores {
    private int codigoProveedor;
    private String NITproveedor;
    private String nombreProveedor;
    private String apellidoProveedor;
    private String direccionProveedor;
    private String razonSocial;
    private String contactoProveedor;

    public Proveedores() {
    }

    public Proveedores(int codigoProveedor, String NITproveedor, String nombreProveedor, String apellidoProveedor, String direccionProveedor, String razonSocial, String contactoProveedores) {
        this.codigoProveedor = codigoProveedor;
        this.NITproveedor = NITproveedor;
        this.nombreProveedor = nombreProveedor;
        this.apellidoProveedor = apellidoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.razonSocial = razonSocial;
        this.contactoProveedor = contactoProveedor;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITproveedor() {
        return NITproveedor;
    }

    public void setNITproveedor(String NITproveedor) {
        this.NITproveedor = NITproveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getApellidoProveedor() {
        return apellidoProveedor;
    }

    public void setApellidoProveedor(String apellidoProveedor) {
        this.apellidoProveedor = apellidoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getContactoProveedor() {
        return contactoProveedor;
    }

    public void setContactoProveedor(String contactoPrincipal) {
        this.contactoProveedor = contactoPrincipal;
    }
}
