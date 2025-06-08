package com.techlab.pedidos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contador = 1;

    private int id;
    private List<LineaPedido> lineas;

    public Pedido() {
        this.id = contador++;
        this.lineas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea);
    }

    public double calcularTotal() {
        double total = 0;
        for (LineaPedido linea : lineas) {
            total += linea.getSubtotal();
        }
        return total;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido ID: ").append(id).append("\n");
        for (LineaPedido linea : lineas) {
            sb.append(linea).append("\n");
        }
        sb.append(String.format("Total: $%.2f\n", calcularTotal()));
        return sb.toString();
    }
}
