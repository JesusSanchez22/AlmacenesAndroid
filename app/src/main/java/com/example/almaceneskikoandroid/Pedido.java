package com.example.almaceneskikoandroid;

public class Pedido {

    private int id_pedido;
    private int id_producto;
    private int cantidad;
    private int id_cliente;

    public Pedido() {
    }

    public Pedido(int id_pedido, int id_producto, int cantidad, int id_cliente) {
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.id_cliente = id_cliente;
    }
    public Pedido(int id_producto, int cantidad, int id_cliente) {
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.id_cliente = id_cliente;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public String toString() {
        return "Código: " + id_pedido;
    }
}
