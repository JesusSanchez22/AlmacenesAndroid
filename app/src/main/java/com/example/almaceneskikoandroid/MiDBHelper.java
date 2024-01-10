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
    private static final int DATABASE_VERSION = 3;
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

        String createTableProductos = "CREATE TABLE productos(id_producto integer, nombre varchar(50), descripcion varchar(500) DEFAULT NULL, precio decimal(10,2), cantidad integer, imagen BLOB, PRIMARY KEY (id_producto, cantidad))";
        db.execSQL(createTableProductos);

        String createTablePedidos = "CREATE TABLE pedidos(id_pedido integer, id_producto integer, cantidad integer, id_cliente integer)";
        db.execSQL(createTablePedidos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS pedidos");
        onCreate(db);
    }

    public void insertarProductos(int id, String nombre, double precio, int cantidad, String descripcion, byte[] imagen){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_producto", id);
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("cantidad", cantidad);
        values.put("imagen", imagen);
        db.insert("productos", null, values);
        db.close();

    }


    public void insertarPedidos(int id_pedido, int id_producto, int cantidad, int id_cliente){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_pedido", id_pedido);
        values.put("id_producto", id_producto);
        values.put("cantidad", cantidad);
        values.put("id_cliente", id_cliente);
        db.insert("pedidos", null, values);
        db.close();


    }

    public List<Pedido> obtenerDatosPedidos(){
        List<Pedido> listaPedidos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

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
        Cursor cursor = null;

        String query = "Select * from productos";
        cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()){

            do{

                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id_producto"));
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                @SuppressLint("Range") double precio = Double.parseDouble(cursor.getString(cursor.getColumnIndex("precio")));
                @SuppressLint("Range") int cantidad = Integer.parseInt(cursor.getString(cursor.getColumnIndex("cantidad")));
                @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                @SuppressLint("Range") byte[] imagen = cursor.getString(cursor.getColumnIndex("descripcion")).getBytes();

                Producto producto = new Producto(id, nombre, precio, cantidad, descripcion, imagen);
                listaProductos.add(producto);

            }while(cursor.moveToNext());

        }

        db.close();

        return listaProductos;


    }

}
