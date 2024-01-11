package com.example.almaceneskikoandroid;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class MiDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bd_Almacenes";
    private static final int DATABASE_VERSION = 5;
    public MiDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MiDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public MiDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableProductos = "CREATE TABLE productos(id_producto integer PRIMARY KEY, nombre varchar(50), descripcion varchar(500) DEFAULT NULL, precio decimal(10,2), imagen BLOB, UNIQUE (nombre))";
        db.execSQL(createTableProductos);

        String createTablePedidos = "CREATE TABLE pedidos(id_pedido integer PRIMARY KEY, id_producto integer, cantidad integer, id_cliente integer, FOREIGN KEY (id_producto) REFERENCES productos(id_producto), FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente))";
        db.execSQL(createTablePedidos);

        String createTableClientes = "CREATE TABLE clientes(id_cliente integer PRIMARY KEY, nombre_fiscal varchar(50), nombre_empresa varchar(50))";
        db.execSQL(createTableClientes);

        String createTableUsuarios = "CREATE TABLE usuarios(id_usuario integer PRIMARY KEY, contrasena varchar(20), isEmpleado bool, id_cliente integer, FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente))";
        db.execSQL(createTableUsuarios);


        //Inserción de datos
        ContentValues valoresProducto1 = new ContentValues();
        valoresProducto1.put("nombre", "Vino Tinto Reserva");
        valoresProducto1.put("descripcion", "Vino tinto de alta calidad");
        valoresProducto1.put("precio", 19.99);
        // La imagen puede ser guardada como byte array en el campo "imagen"

        db.insert("productos", null, valoresProducto1);

        // Creación de un nuevo producto
        ContentValues valoresProducto2 = new ContentValues();
        valoresProducto2.put("nombre", "Vino Blanco Especial");
        valoresProducto2.put("descripcion", "Vino blanco de cosecha especial");
        valoresProducto2.put("precio", 24.99);

        db.insert("productos", null, valoresProducto2);



        ContentValues valoresCliente = new ContentValues();
        valoresCliente.put("id_cliente", 200);
        valoresCliente.put("nombre_fiscal", "Bodegas Vinícolas S.A.");
        valoresCliente.put("nombre_empresa", "Vinícolas");

        db.insert("clientes", null, valoresCliente);


        ContentValues valoresPedido = new ContentValues();
        valoresPedido.put("id_producto", 1);  // Suponiendo que el producto con id 1 existe
        valoresPedido.put("cantidad", 2);
        valoresPedido.put("id_cliente", 1);   // Suponiendo que el cliente con id 1 existe

        db.insert("pedidos", null, valoresPedido);


        ContentValues valoresUsuario = new ContentValues();
        valoresUsuario.put("contrasena", "miContrasenaSegura");
        valoresUsuario.put("isEmpleado", 0);  // 1 para true, 0 para false
        valoresUsuario.put("id_cliente", 1);  // Suponiendo que el cliente con id 1 existe

        db.insert("usuarios", null, valoresUsuario);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS pedidos");
        db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    public void insertarProductos(int id, String nombre, double precio, String descripcion, byte[] imagen){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_producto", id);
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        //values.put("cantidad", cantidad);
        values.put("imagen", imagen);
        db.insert("productos", null, values);
        db.close();

    }

    public void insertarClientes(String nombreFiscal, String nombreEmpresa){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre_fiscal", nombreFiscal);
        values.put("nombre_empresa", nombreEmpresa);
        db.insert("clientes", null, values);
        db.close();

    }

    public List<Cliente> obtenerDatosClientes(){
        List<Cliente> listaClientes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String query = "Select * from clientes";
        cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){

            do{

                @SuppressLint("Range") int id_cliente = cursor.getInt(cursor.getColumnIndex("id_cliente"));
                @SuppressLint("Range") String nombre_fiscal = cursor.getString(cursor.getColumnIndex("nombre_fiscal"));
                @SuppressLint("Range") String nombre_empresa = cursor.getString(cursor.getColumnIndex("nombre_empresa"));

                Cliente cliente = new Cliente(id_cliente, nombre_fiscal, nombre_empresa);
                listaClientes.add(cliente);

            }while(cursor.moveToNext());

        }

        db.close();

        return listaClientes;
    }

    public void insertarUsuarios(String contrasena, boolean isEmpleado, int idCliente) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("contrasena", contrasena);
        values.put("isEmpleado", isEmpleado ? 1 : 0); // Convertir booleano a entero
        values.put("id_cliente", idCliente);
        db.insert("usuarios", null, values);
        db.close();

    }


    public List<Usuario> obtenerDatosUsuarios(){
        List<Usuario> listaUsuarios = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String query = "SELECT * FROM usuarios";
        cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){

            do{
                @SuppressLint("Range") int idUsuario = cursor.getInt(cursor.getColumnIndex("id_usuario"));
                @SuppressLint("Range") String contrasena = cursor.getString(cursor.getColumnIndex("contrasena"));
                @SuppressLint("Range") boolean isEmpleado = cursor.getInt(cursor.getColumnIndex("isEmpleado")) == 1;
                @SuppressLint("Range") int idCliente = cursor.getInt(cursor.getColumnIndex("id_cliente"));

                Usuario usuario = new Usuario(idUsuario, contrasena, isEmpleado, idCliente);
                listaUsuarios.add(usuario);

            } while(cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return listaUsuarios;
    }


    public void insertarPedidos(int id_producto, int cantidad, int id_cliente){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("id_pedido", id_pedido);
        values.put("id_producto", id_producto);
        values.put("cantidad", cantidad);
        values.put("id_cliente", id_cliente);
        db.insert("pedidos", null, values);
        db.close();
    }

    public List<Pedido> obtenerDatosPedidos(){
        List<Pedido> listaPedidos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String query = "Select * from pedidos";
        cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){

            do{

                @SuppressLint("Range") int id_pedido = cursor.getInt(cursor.getColumnIndex("id_pedido"));
                @SuppressLint("Range") int id_producto = cursor.getInt(cursor.getColumnIndex("id_producto"));
                @SuppressLint("Range") int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                @SuppressLint("Range") int id_cliente = cursor.getInt(cursor.getColumnIndex("id_cliente"));

                Pedido pedido = new Pedido(id_pedido, id_producto, cantidad, id_cliente);
                listaPedidos.add(pedido);

            }while(cursor.moveToNext());

        }

        db.close();

        return listaPedidos;
    }

    public List<Producto> obtenerDatosProductos(){

        List<Producto> listaProductos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String query = "Select * from productos";
        cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){

            do{

                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id_producto"));
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                @SuppressLint("Range") double precio = Double.parseDouble(cursor.getString(cursor.getColumnIndex("precio")));
                //@SuppressLint("Range") int cantidad = Integer.parseInt(cursor.getString(cursor.getColumnIndex("cantidad")));
                @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                @SuppressLint("Range") byte[] imagen = cursor.getBlob(cursor.getColumnIndex("imagen"));

                Producto producto = new Producto(id, nombre, precio, descripcion, imagen);
                listaProductos.add(producto);

            }while(cursor.moveToNext());

        }

        db.close();

        return listaProductos;
    }


    public void modificarProducto(int id, String nombre, double precio, int cantidad, String descripcion, byte[] imagen){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id_producto", id);
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("cantidad", cantidad);
        values.put("imagen", imagen);

        String whereClause = "id_producto = ?";
        String[] whereArgs = {String.valueOf(id)};

        db.update("productos", values, whereClause, whereArgs);

        db.close();
    }

    public void eliminarProducto(int id_producto){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM productos where id_producto =" + id_producto);

    }


    public void eliminarPedido(int id_pedido){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM pedidos where id_pedido =" + id_pedido);

    }
}
