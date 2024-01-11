package com.example.almaceneskikoandroid;

public class Cliente {
    private int idCliente;
    private String nombreFiscal;
    private String nombreEmpresa;

    // Constructores
    public Cliente() {
        // Constructor por defecto
    }

    public Cliente(int idCliente, String nombreFiscal, String nombreEmpresa) {
        this.idCliente = idCliente;
        this.nombreFiscal = nombreFiscal;
        this.nombreEmpresa = nombreEmpresa;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreFiscal() {
        return nombreFiscal;
    }

    public void setNombreFiscal(String nombreFiscal) {
        this.nombreFiscal = nombreFiscal;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombreFiscal='" + nombreFiscal + '\'' +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                '}';
    }
}
