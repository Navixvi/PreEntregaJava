package com.techlab.pedidos;

import com.techlab.productos.Producto;
import com.techlab.excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private List<Pedido> pedidos;

    public PedidoService() {
        pedidos = new ArrayList<>();
    }

    public void crearPedido(List<LineaPedido> lineas) throws StockInsuficienteException {
        // Validar stock antes de crear pedido
        for (LineaPedido linea : lineas) {
            Producto prod = linea.getProducto();
            if (prod.getStock() < linea.getCantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para el producto: " + prod.getNombre());
            }
        }

        // Descontar stock
        for (LineaPedido linea : lineas) {
            Producto prod = linea.getProducto();
            prod.setStock(prod.getStock() - linea.getCantidad());
        }

        // Crear pedido y agregarlo a la lista
        Pedido pedido = new Pedido();
        for (LineaPedido linea : lineas) {
            pedido.agregarLinea(linea);
        }
        pedidos.add(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }
}
