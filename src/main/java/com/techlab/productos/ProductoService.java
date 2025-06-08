package com.techlab.productos;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    private List<Producto> productos;

    public ProductoService() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(String nombre, double precio, int stock) {
        Producto nuevoProducto = new Producto(nombre, precio, stock);
        productos.add(nuevoProducto);
    }

    public List<Producto> listarProductos() {
        return productos;
    }

    // Buscar por ID
    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null; // No encontrado
    }

    // Buscar por nombre (retorna el primero que coincida)
    public Producto buscarPorNombre(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null; // No encontrado
    }

    // Actualizar precio o stock de un producto (si es válido)
    public boolean actualizarProducto(int id, Double nuevoPrecio, Integer nuevoStock) {
        Producto producto = buscarPorId(id);
        if (producto == null) {
            return false;
        }

        if (nuevoPrecio != null && nuevoPrecio >= 0) {
            producto.setPrecio(nuevoPrecio);
        }

        if (nuevoStock != null && nuevoStock >= 0) {
            producto.setStock(nuevoStock);
        }

        return true;
    }

    // Eliminar producto por ID
    public boolean eliminarProducto(int id) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            productos.remove(producto);
            return true;
        }
        return false;
    }
}
