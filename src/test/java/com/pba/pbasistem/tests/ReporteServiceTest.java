package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Pedido;
import com.pba.pbasistem.model.Postventa;
import com.pba.pbasistem.model.Producto;
import com.pba.pbasistem.service.ReporteService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReporteServiceTest {

    private final ReporteService reporte = new ReporteService();

    @Test
    public void deberiaContarPedidosPorEstado() {
        List<Pedido> pedidos = Arrays.asList(
            crearPedido(Pedido.Estado.PENDIENTE),
            crearPedido(Pedido.Estado.PENDIENTE),
            crearPedido(Pedido.Estado.PENDIENTE),
            crearPedido(Pedido.Estado.ENVIADO),
            crearPedido(Pedido.Estado.ENVIADO),
            crearPedido(Pedido.Estado.ENTREGADO)
        );

        Map<Pedido.Estado, Integer> resultado =
            reporte.contarPedidosPorEstado(pedidos);

        assertEquals(Integer.valueOf(3), resultado.get(Pedido.Estado.PENDIENTE));
        assertEquals(Integer.valueOf(2), resultado.get(Pedido.Estado.ENVIADO));
        assertEquals(Integer.valueOf(1), resultado.get(Pedido.Estado.ENTREGADO));
    }

    @Test
    public void deberiaOrdenarProductosMasVendidos() {
        Producto productoCon10Ventas = crearProducto("Producto 10", 10);
        Producto productoCon5Ventas = crearProducto("Producto 5", 5);
        Producto productoCon20Ventas = crearProducto("Producto 20", 20);

        List<Producto> resultado = reporte.obtenerProductosMasVendidos(
            Arrays.asList(
                productoCon10Ventas,
                productoCon5Ventas,
                productoCon20Ventas
            )
        );

        assertEquals(productoCon20Ventas, resultado.get(0));
        assertEquals(productoCon10Ventas, resultado.get(1));
        assertEquals(productoCon5Ventas, resultado.get(2));
    }

    @Test
    public void deberiaContarReclamosPendientesYResueltos() {
        List<Postventa> postventas = Arrays.asList(
            crearPostventa(Postventa.Tipo.RECLAMO, Postventa.Estado.PENDIENTE),
            crearPostventa(Postventa.Tipo.RECLAMO, Postventa.Estado.PENDIENTE),
            crearPostventa(Postventa.Tipo.RECLAMO, Postventa.Estado.RESUELTO),
            crearPostventa(Postventa.Tipo.CONSULTA, Postventa.Estado.PENDIENTE),
            crearPostventa(Postventa.Tipo.CONSULTA, Postventa.Estado.PENDIENTE),
            crearPostventa(Postventa.Tipo.CONSULTA, Postventa.Estado.PENDIENTE),
            crearPostventa(
                Postventa.Tipo.RESULTADO_DE_CAMPO,
                Postventa.Estado.RESUELTO
            )
        );

        Map<Postventa.Estado, Integer> resultado =
            reporte.contarReclamosPorEstado(postventas);

        assertEquals(
            Integer.valueOf(2),
            resultado.get(Postventa.Estado.PENDIENTE)
        );
        assertEquals(
            Integer.valueOf(1),
            resultado.get(Postventa.Estado.RESUELTO)
        );
    }

    private Pedido crearPedido(Pedido.Estado estado) {
        Pedido pedido = new Pedido();
        pedido.setEstado(estado);
        return pedido;
    }

    private Producto crearProducto(String nombre, int cantidadVendida) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCantidadVendida(cantidadVendida);
        return producto;
    }

    private Postventa crearPostventa(
        Postventa.Tipo tipo, Postventa.Estado estado) {
        Postventa postventa = new Postventa();
        postventa.setTipo(tipo);
        postventa.setEstado(estado);
        return postventa;
    }
}
