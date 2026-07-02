package com.pba.pbasistem.actions;

import com.pba.pbasistem.model.Pedido;
import com.pba.pbasistem.model.Postventa;
import com.pba.pbasistem.model.Producto;
import com.pba.pbasistem.service.ReporteService;
import java.util.List;
import java.util.Map;
import org.openxava.actions.BaseAction;
import org.openxava.jpa.XPersistence;

public class MostrarResumenReporteAction extends BaseAction {

    private final ReporteService reporteService = new ReporteService();
    private String tipoReporte;

    @Override
    public void execute() {
        if ("PEDIDOS".equals(tipoReporte)) {
            mostrarPedidosPorEstado();
        }
        else if ("PRODUCTOS".equals(tipoReporte)) {
            mostrarProductosMasVendidos();
        }
        else if ("RECLAMOS".equals(tipoReporte)) {
            mostrarReclamosPorEstado();
        }
        else {
            addError("tipo_reporte_no_valido");
        }
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    private void mostrarPedidosPorEstado() {
        List<Pedido> pedidos = XPersistence.getManager()
            .createQuery("from Pedido", Pedido.class)
            .getResultList();
        Map<Pedido.Estado, Integer> cantidades =
            reporteService.contarPedidosPorEstado(pedidos);

        for (Pedido.Estado estado : Pedido.Estado.values()) {
            addMessage(
                "reporte_estado_cantidad",
                estado,
                cantidades.get(estado)
            );
        }
    }

    private void mostrarProductosMasVendidos() {
        List<Producto> productos = XPersistence.getManager()
            .createQuery("from Producto", Producto.class)
            .getResultList();
        List<Producto> productosOrdenados =
            reporteService.obtenerProductosMasVendidos(productos);

        if (productosOrdenados.isEmpty()) {
            addMessage("reporte_sin_datos");
            return;
        }

        Producto primero = productosOrdenados.get(0);
        addMessage(
            "reporte_producto_mas_vendido",
            primero.getNombre(),
            primero.getCantidadVendida(),
            productosOrdenados.size()
        );
    }

    private void mostrarReclamosPorEstado() {
        List<Postventa> postventas = XPersistence.getManager()
            .createQuery("from Postventa", Postventa.class)
            .getResultList();
        Map<Postventa.Estado, Integer> cantidades =
            reporteService.contarReclamosPorEstado(postventas);

        for (Postventa.Estado estado : Postventa.Estado.values()) {
            addMessage(
                "reporte_estado_cantidad",
                estado,
                cantidades.get(estado)
            );
        }
    }
}
