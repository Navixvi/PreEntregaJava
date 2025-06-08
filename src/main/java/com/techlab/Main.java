package com.techlab;

import com.techlab.productos.Producto;
import com.techlab.productos.ProductoService;
import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.PedidoService;
import com.techlab.excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static ProductoService productoService = new ProductoService();
    private static PedidoService pedidoService = new PedidoService();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Elija una opción: ");

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> listarProductos();
                case 3 -> buscarActualizarProducto();
                case 4 -> eliminarProducto();
                case 5 -> crearPedido();
                case 6 -> listarPedidos();
                case 7 -> {
                    System.out.println("¡Gracias vuelva pronto!");
                    salir = true;
                }
                default -> System.out.println("Opción inválida, intente de nuevo.");
            }
        }
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=================================== SISTEMA DE GESTIÓN - TECHLAB ==================================");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
    }

    private static int leerEntero(String mensaje) {
        int num = -1;
        while (true) {
            try {
                System.out.print(mensaje);
                num = Integer.parseInt(sc.nextLine());
                if (num < 0) {
                    System.out.println("Ingrese un número positivo.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, ingrese un número entero.");
            }
        }
        return num;
    }

    private static double leerDouble(String mensaje) {
        double num;
        while (true) {
            try {
                System.out.print(mensaje);
                num = Double.parseDouble(sc.nextLine());
                if (num < 0) {
                    System.out.println("Ingrese un número positivo.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, ingrese un número decimal.");
            }
        }
        return num;
    }

    private static void agregarProducto() {
        System.out.print("Ingrese nombre del producto: ");
        String nombre = sc.nextLine();

        double precio = leerDouble("Ingrese precio del producto: ");
        int stock = leerEntero("Ingrese cantidad en stock: ");

        productoService.agregarProducto(nombre, precio, stock);
        System.out.println("Producto agregado con éxito.");
    }

    private static void listarProductos() {
        System.out.println("\n--- Lista de Productos ---");
        List<Producto> productos = productoService.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            for (Producto p : productos) {
                System.out.println(p);
            }
        }
    }

    private static void buscarActualizarProducto() {
        int id = leerEntero("Ingrese ID del producto a buscar: ");
        Producto producto = productoService.buscarPorId(id);

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.println("Producto encontrado: " + producto);

        System.out.print("Desea actualizar el precio? (s/n): ");
        String resp = sc.nextLine();
        Double nuevoPrecio = null;
        if (resp.equalsIgnoreCase("s")) {
            nuevoPrecio = leerDouble("Ingrese nuevo precio: ");
        }

        System.out.print("Desea actualizar el stock? (s/n): ");
        resp = sc.nextLine();
        Integer nuevoStock = null;
        if (resp.equalsIgnoreCase("s")) {
            nuevoStock = leerEntero("Ingrese nuevo stock: ");
        }

        boolean actualizado = productoService.actualizarProducto(id, nuevoPrecio, nuevoStock);
        if (actualizado) {
            System.out.println("Producto actualizado: " + productoService.buscarPorId(id));
        } else {
            System.out.println("No se pudo actualizar el producto.");
        }
    }

    private static void eliminarProducto() {
        int id = leerEntero("Ingrese ID del producto a eliminar: ");
        Producto producto = productoService.buscarPorId(id);

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.println("Producto encontrado: " + producto);
        System.out.print("Confirma eliminación? (s/n): ");
        String resp = sc.nextLine();
        if (resp.equalsIgnoreCase("s")) {
            boolean eliminado = productoService.eliminarProducto(id);
            if (eliminado) {
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("No se pudo eliminar el producto.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private static void crearPedido() {
        try {
            int cantProd = leerEntero("¿Cuántos productos desea agregar al pedido? ");
            if (cantProd <= 0) {
                System.out.println("Debe agregar al menos un producto.");
                return;
            }

            List<LineaPedido> lineas = new ArrayList<>();

            for (int i = 0; i < cantProd; i++) {
                int idProd = leerEntero("Ingrese ID del producto: ");
                Producto producto = productoService.buscarPorId(idProd);

                if (producto == null) {
                    System.out.println("Producto no encontrado.");
                    i--; // repetir iteración
                    continue;
                }

                int cantidad = leerEntero("Ingrese cantidad deseada: ");
                if (cantidad <= 0) {
                    System.out.println("Cantidad inválida.");
                    i--;
                    continue;
                }

                lineas.add(new LineaPedido(producto, cantidad));
            }

            pedidoService.crearPedido(lineas);
            System.out.println("Pedido creado con éxito.");
        } catch (StockInsuficienteException e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        }
    }

    private static void listarPedidos() {
        System.out.println("\n--- Lista de Pedidos ---");
        List<com.techlab.pedidos.Pedido> pedidos = pedidoService.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos realizados.");
        } else {
            for (var pedido : pedidos) {
                System.out.println(pedido);
            }
        }
    }
}
