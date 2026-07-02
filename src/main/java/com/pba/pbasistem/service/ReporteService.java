package com.pba.pbasistem.service;

import com.pba.pbasistem.model.Pedido;
import com.pba.pbasistem.model.Postventa;
import com.pba.pbasistem.model.Producto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReporteService {

    public Map<Pedido.Estado, Integer> contarPedidosPorEstado(List<Pedido> pedidos) {
        Objects.requireNonNull(pedidos, "La lista de pedidos no puede ser nula");

        Map<Pedido.Estado, Integer> pedidosPorEstado =
            new EnumMap<>(Pedido.Estado.class);
        for (Pedido.Estado estado : Pedido.Estado.values()) {
            pedidosPorEstado.put(estado, 0);
        }

        for (Pedido pedido : pedidos) {
            if (pedido != null && pedido.getEstado() != null) {
                Pedido.Estado estado = pedido.getEstado();
                pedidosPorEstado.put(estado, pedidosPorEstado.get(estado) + 1);
            }
        }
        return pedidosPorEstado;
    }

    public List<Producto> obtenerProductosMasVendidos(List<Producto> productos) {
        Objects.requireNonNull(productos, "La lista de productos no puede ser nula");

        List<Producto> productosOrdenados = new ArrayList<>(productos);
        productosOrdenados.sort(
            Comparator.comparingInt(Producto::getCantidadVendida).reversed());
        return productosOrdenados;
    }

    public Map<Postventa.Estado, Integer> contarReclamosPorEstado(
        List<Postventa> postventas) {
        Objects.requireNonNull(postventas, "La lista de postventas no puede ser nula");

        Map<Postventa.Estado, Integer> reclamosPorEstado =
            new EnumMap<>(Postventa.Estado.class);
        for (Postventa.Estado estado : Postventa.Estado.values()) {
            reclamosPorEstado.put(estado, 0);
        }

        for (Postventa postventa : postventas) {
            if (esReclamoConEstado(postventa)) {
                Postventa.Estado estado = postventa.getEstado();
                reclamosPorEstado.put(estado, reclamosPorEstado.get(estado) + 1);
            }
        }
        return reclamosPorEstado;
    }

    private boolean esReclamoConEstado(Postventa postventa) {
        return postventa != null
            && postventa.getTipo() == Postventa.Tipo.RECLAMO
            && postventa.getEstado() != null;
    }
}
