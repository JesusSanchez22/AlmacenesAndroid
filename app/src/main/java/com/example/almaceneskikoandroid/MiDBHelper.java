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

        String createTableProductos = "CREATE TABLE productos(id_producto integer PRIMARY KEY, nombre varchar(50), descripcion varchar(500) DEFAULT NULL, precio decimal(10,2), imagen BLOB, cantidad integer, UNIQUE (nombre))";
        db.execSQL(createTableProductos);

        String createTablePedidos = "CREATE TABLE pedidos(id_pedido integer PRIMARY KEY, id_producto integer, cantidad integer, id_cliente integer, FOREIGN KEY (id_producto) REFERENCES productos(id_producto), FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente))";
        db.execSQL(createTablePedidos);

        String createTableClientes = "CREATE TABLE clientes(id_cliente integer PRIMARY KEY, nombre_fiscal varchar(50), nombre_empresa varchar(50), calle varchar(35), numero integer, cp integer(5), ciudad varchar(40))";
        db.execSQL(createTableClientes);

        String createTableUsuarios = "CREATE TABLE usuarios(id_usuario varchar(20) PRIMARY KEY, contrasena varchar(20), isEmpleado bool, id_cliente integer, FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente))";
        db.execSQL(createTableUsuarios);


        //Inserción de productos

        //Producto 1
        ContentValues valoresProducto1 = new ContentValues();
        valoresProducto1.put("nombre", "Vino Tinto Reserva");
        valoresProducto1.put("descripcion", "Vino tinto de alta calidad");
        valoresProducto1.put("precio", 19.99);
        valoresProducto1.put("cantidad", 100);

        db.insert("productos", null, valoresProducto1);

        //Producto 2
        ContentValues valoresProducto2 = new ContentValues();
        valoresProducto2.put("nombre", "Vino Blanco Especial");
        valoresProducto2.put("descripcion", "Vino blanco de cosecha especial");
        valoresProducto2.put("precio", 24.99);
        valoresProducto2.put("cantidad", 500);

        db.insert("productos", null, valoresProducto1);


        // Inserción de cliente
        // Cliente 1
        ContentValues valoresCliente1 = new ContentValues();
        valoresCliente1.put("nombre_fiscal", "Bodegas Vinícolas S.A.");
        valoresCliente1.put("nombre_empresa", "Vinícolas");
        valoresCliente1.put("calle", "Mirabel");
        valoresCliente1.put("numero", 25);
        valoresCliente1.put("cp", 47001);
        valoresCliente1.put("ciudad", "Valladolid");

        db.insert("clientes", null, valoresCliente1);

        // Cliente 2
        ContentValues valoresCliente2 = new ContentValues();
        valoresCliente2.put("nombre_fiscal", "Bodega Elegante S.L.");
        valoresCliente2.put("nombre_empresa", "Vinos Elegantes");
        valoresCliente2.put("calle", "Cerrada");
        valoresCliente2.put("numero", 4);
        valoresCliente2.put("cp", 47002);
        valoresCliente2.put("ciudad", "Valladolid");

        db.insert("clientes", null, valoresCliente2);

        // Pedido 1
        ContentValues valoresPedido = new ContentValues();
        valoresPedido.put("id_producto", 1);
        valoresPedido.put("cantidad", 2);
        valoresPedido.put("id_cliente", 1);

        db.insert("pedidos", null, valoresPedido);

        // Pedido 2
        ContentValues valoresPedido2 = new ContentValues();
        valoresPedido2.put("id_producto", 2);
        valoresPedido2.put("cantidad", 10);
        valoresPedido2.put("id_cliente", 2);

        db.insert("pedidos", null, valoresPedido2);

        //Usuario 1 (jesus) de cliente 1
        ContentValues valoresUsuario = new ContentValues();
        valoresUsuario.put("id_usuario", "jesusm");
        valoresUsuario.put("contrasena", "123");
        valoresUsuario.put("isEmpleado", 0);  // 1 para true, 0 para false
        valoresUsuario.put("id_cliente", 1);

        db.insert("usuarios", null, valoresUsuario);

        //Usuario 2 (Raul)
        ContentValues valoresUsuario2 = new ContentValues();
        valoresUsuario2.put("id_usuario", "raulp");
        valoresUsuario2.put("contrasena", "123");
        valoresUsuario2.put("isEmpleado", 1);  // 1 para true, 0 para false

        db.insert("usuarios", null, valoresUsuario2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS pedidos");
        db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    public void insertarProductos(String nombre,int cantidad, double precio, String descripcion, byte[] imagen){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("cantidad", cantidad);
        values.put("imagen", imagen);
        db.insert("productos", null, values);

        db.close();

    }

    public void insertarClientes(String nombreFiscal, String nombreEmpresa, String calle, int numero, int cp, String ciudad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nombre_fiscal", nombreFiscal);
        values.put("nombre_empresa", nombreEmpresa);
        values.put("calle", calle);
        values.put("numero", numero);
        values.put("cp", cp);
        values.put("ciudad", ciudad);

        db.insert("clientes", null, values);
        db.close();
    }


    @SuppressLint("Range")
    public List<Cliente> obtenerDatosClientes(){
        List<Cliente> listaClientes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String query = "Select * from clientes";
        cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){

            do{

                int id = cursor.getInt(cursor.getColumnIndex("id_cliente"));
                String nombreFiscal = cursor.getString(cursor.getColumnIndex("nombre_fiscal"));
                String nombreEmpresa = cursor.getString(cursor.getColumnIndex("nombre_empresa"));
                String calle = cursor.getString(cursor.getColumnIndex("calle"));
                int numero = cursor.getInt(cursor.getColumnIndex("numero"));
                int cp = cursor.getInt(cursor.getColumnIndex("cp"));
                String ciudad = cursor.getString(cursor.getColumnIndex("ciudad"));

                // Crear un objeto Cliente
                Cliente cliente = new Cliente(id, nombreFiscal, nombreEmpresa, calle, numero, cp, ciudad);
                listaClientes.add(cliente);

            }while(cursor.moveToNext());

        }

        db.close();

        return listaClientes;
    }

    public void insertarUsuarios(String id_usuario, String contrasena, boolean isEmpleado, int idCliente) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_usuario", id_usuario);
        values.put("contrasena", contrasena);
        values.put("isEmpleado", isEmpleado ? 1 : 0); // Convertir booleano a entero
        values.put("id_cliente", idCliente);
        db.insert("usuarios", null, values);
        db.close();

    }

    public void registro (String id_usuario, String contrasena, int idCliente) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id_usuario", id_usuario);
        values.put("contrasena", contrasena);
        values.put("isEmpleado", 0); //No es empleado
        values.put("id_cliente", idCliente);

        db.insert("usuarios", null, values);
        db.close();

    }

    public Boolean existeCliente(int id_cliente) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Boolean existe = false;

        try {
            db = this.getReadableDatabase();

            String query = "SELECT * FROM clientes WHERE id_cliente = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(id_cliente)});

            if (cursor != null && cursor.moveToFirst()) {
                existe = true;
            }
        } catch (Exception e) {
            // Manejar excepciones (puedes imprimir el error para depuración)
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return existe;
    }

    public List<Usuario> obtenerDatosUsuarios(){
        List<Usuario> listaUsuarios = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String query = "SELECT * FROM usuarios";
        cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){

            do{
                @SuppressLint("Range") String idUsuario = cursor.getString(cursor.getColumnIndex("id_usuario"));
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

    @SuppressLint("Range")
    public Usuario obtenerUsuario(String id_usuario) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM usuarios WHERE id_usuario = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id_usuario});

        Usuario usuario = null;

        if (cursor != null && cursor.moveToFirst()) {
            String contrasena = cursor.getString(cursor.getColumnIndex("contrasena"));
            boolean isEmpleado = cursor.getInt(cursor.getColumnIndex("isEmpleado")) == 1;
            int id_cliente = cursor.getInt(cursor.getColumnIndex("id_cliente"));

            // Crear un objeto Usuario
            usuario = new Usuario(id_usuario, contrasena, isEmpleado, id_cliente);

            cursor.close();
        }

        db.close();
        return usuario;
    }


    public Boolean existeUsuario(String id_usuario) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Boolean existe = false;

        try {
            db = this.getReadableDatabase();

            String query = "SELECT * FROM usuarios WHERE id_usuario = ?";
            cursor = db.rawQuery(query, new String[]{id_usuario});

            if (cursor != null && cursor.moveToFirst()) {
                existe = true;
            }
        } catch (Exception e) {
            // Manejar excepciones (puedes imprimir el error para depuración)
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return existe;
    }

    @SuppressLint("Range")
    public Boolean comprobarContrasena(String id_usuario, String contrasenaIn) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT contrasena FROM usuarios WHERE id_usuario = ?";

        Cursor cursor = db.rawQuery(query, new String[]{id_usuario});
        String contrasena = null;
        Boolean isCorrecta = false;

        if (cursor != null && cursor.moveToFirst()) {
            contrasena = cursor.getString(cursor.getColumnIndex("contrasena"));
        }
        cursor.close();

        db.close();

        if (contrasenaIn.equals(contrasena)){
            isCorrecta = true;
        }

        return isCorrecta;
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

    @SuppressLint("Range")
    public List<Pedido> obtenerDatosPedidos(){
        List<Pedido> listaPedidos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String query = "Select * from pedidos";
        cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){

            do{

                int id_pedido = cursor.getInt(cursor.getColumnIndex("id_pedido"));
                int id_producto = cursor.getInt(cursor.getColumnIndex("id_producto"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                int id_cliente = cursor.getInt(cursor.getColumnIndex("id_cliente"));

                Pedido pedido = new Pedido(id_pedido, id_producto, cantidad, id_cliente);
                listaPedidos.add(pedido);

            }while(cursor.moveToNext());

        }

        db.close();

        return listaPedidos;
    }

    @SuppressLint("Range")
    public List<Pedido> obtenerDatosPedidosUsuario(String id_usuario){
        List<Pedido> listaPedidos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        String query = "Select * from pedidos Where id_cliente = (Select id_cliente from usuarios where id_usuario = ?)";
        cursor = db.rawQuery(query, new String[]{id_usuario});

        if(cursor != null && cursor.moveToFirst()){

            do{

                int id_pedido = cursor.getInt(cursor.getColumnIndex("id_pedido"));
                int id_producto = cursor.getInt(cursor.getColumnIndex("id_producto"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                int id_cliente = cursor.getInt(cursor.getColumnIndex("id_cliente"));

                Pedido pedido = new Pedido(id_pedido, id_producto, cantidad, id_cliente);
                listaPedidos.add(pedido);

            }while(cursor.moveToNext());
            cursor.close();

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
                @SuppressLint("Range") int cantidad = Integer.parseInt(cursor.getString(cursor.getColumnIndex("cantidad")));
                @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                @SuppressLint("Range") byte[] imagen = cursor.getBlob(cursor.getColumnIndex("imagen"));

                Producto producto = new Producto(id, nombre, precio, cantidad, descripcion, imagen);
                listaProductos.add(producto);

            }while(cursor.moveToNext());

        }

        db.close();

        return listaProductos;
    }


    public void modificarProducto(int id, String nombre, double precio, String descripcion, byte[] imagen){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id_producto", id);
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        //values.put("cantidad", cantidad);
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

    @SuppressLint("Range")
    public Cliente obtenerCliente(int id_cliente) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM clientes WHERE id_cliente = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id_cliente)});

        Cliente cliente = null;

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id_cliente"));
            String nombreFiscal = cursor.getString(cursor.getColumnIndex("nombre_fiscal"));
            String nombreEmpresa = cursor.getString(cursor.getColumnIndex("nombre_empresa"));
            String calle = cursor.getString(cursor.getColumnIndex("calle"));
            int numero = cursor.getInt(cursor.getColumnIndex("numero"));
            int cp = cursor.getInt(cursor.getColumnIndex("cp"));
            String ciudad = cursor.getString(cursor.getColumnIndex("ciudad"));

            // Crear un objeto Cliente
            cliente = new Cliente(id, nombreFiscal, nombreEmpresa, calle, numero, cp, ciudad);

            cursor.close();
        }

        db.close();
        return cliente;
    }

    public Producto obtenerProductoPorNombre(String nombreBuscar) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM productos WHERE nombre = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombreBuscar});

        Producto producto = null;

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id_producto"));
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            @SuppressLint("Range") double precio = Double.parseDouble(cursor.getString(cursor.getColumnIndex("precio")));
            @SuppressLint("Range") int cantidad = Integer.parseInt(cursor.getString(cursor.getColumnIndex("cantidad")));
            @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
            @SuppressLint("Range") byte[] imagen = cursor.getBlob(cursor.getColumnIndex("imagen"));

            producto = new Producto(id, nombre, precio, cantidad, descripcion, imagen);
            cursor.close();
        }

        db.close();
        return producto;
    }



}
