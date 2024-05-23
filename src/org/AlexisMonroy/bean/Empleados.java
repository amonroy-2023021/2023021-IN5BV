/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.AlexisMonroy.bean;

/**
 *
 * @author Dell
 */
public class Empleados {
    private int codigoEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private double sueldo;
    private String direccionEmpleado;
    private String turno;
    private int getCodigoCargoEmpleado;

    public Empleados() {
    }

    public Empleados(int codigoEmpleado, String nombreEmpleado, String apellidoEmpleado, double sueldo, String direccionEmpleado, String turno, int CargoEmpleado_codigoCargoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.sueldo = sueldo;
        this.direccionEmpleado = direccionEmpleado;
        this.turno = turno;
        this.getCodigoCargoEmpleado = getCodigoCargoEmpleado;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getGetCodigoCargoEmpleado() {
        return getCodigoCargoEmpleado;
    }

    public void setGetCodigoCargoEmpleado(int getCodigoCargoEmpleado) {
        this.getCodigoCargoEmpleado = getCodigoCargoEmpleado;
    }

    
}
