package com.example.almaceneskikoandroid;

public class Usuario {
    private int idUsuario;
    private String contrasena;
    private boolean isEmpleado;
    private int idCliente;

    // Constructores
    public Usuario() {
        // Constructor por defecto
    }

    public Usuario(int idUsuario, String contrasena, boolean isEmpleado, int idCliente) {
        this.idUsuario = idUsuario;
        this.contrasena = contrasena;
        this.isEmpleado = isEmpleado;
        this.idCliente = idCliente;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isEmpleado() {
        return isEmpleado;
    }

    public void setEmpleado(boolean isEmpleado) {
        this.isEmpleado = isEmpleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // Método toString para obtener una representación en cadena del objeto
    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", contrasena=" + contrasena + ", isEmpleado="
                + isEmpleado + ", idCliente=" + idCliente + "]";
    }
}

