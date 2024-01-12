package com.example.almaceneskikoandroid;

public class Cliente {
    private int idCliente;
    private String nombreFiscal;
    private String nombreEmpresa;
    private String calle;
    private int numero;
    private int cp;
    private String ciudad;

    // Constructores
    public Cliente() {
        // Constructor por defecto
    }

    public Cliente(int idCliente, String nombreFiscal, String nombreEmpresa, String calle, int numero, int cp, String ciudad) {
        this.idCliente = idCliente;
        this.nombreFiscal = nombreFiscal;
        this.nombreEmpresa = nombreEmpresa;
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
        this.ciudad = ciudad;
    }

    // Getters y Setters

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

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    // toString() para depuración
    @Override
    public String toString() {
        return "" + nombreFiscal;
    }
}
