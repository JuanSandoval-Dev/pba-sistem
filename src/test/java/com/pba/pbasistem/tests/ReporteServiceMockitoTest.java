package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Pedido;
import com.pba.pbasistem.model.Postventa;
import com.pba.pbasistem.model.Producto;
import com.pba.pbasistem.service.ReporteService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceMockitoTest {

    @Mock
    private Pedido pedidoPendiente;

    @Mock
    private Pedido pedidoEnviado;

    @Mock
    private Producto productoConCincoVentas;

    @Mock
    private Producto productoConDiezVentas;

    @Mock
    private Postventa reclamo;

    @Mock
    private Postventa consulta;

    private final ReporteService reporteService = new ReporteService();

    @Test
    public void deberiaContarPedidosPorEstadoConDatosSimulados() {
        when(pedidoPendiente.getEstado())
            .thenReturn(Pedido.Estado.PENDIENTE);
        when(pedidoEnviado.getEstado())
            .thenReturn(Pedido.Estado.ENVIADO);

        Map<Pedido.Estado, Integer> resultado =
            reporteService.contarPedidosPorEstado(
                Arrays.asList(pedidoPendiente, pedidoEnviado));

        assertEquals(Integer.valueOf(1),
            resultado.get(Pedido.Estado.PENDIENTE));
        assertEquals(Integer.valueOf(1),
            resultado.get(Pedido.Estado.ENVIADO));
        verify(pedidoPendiente, times(2)).getEstado();
        verify(pedidoEnviado, times(2)).getEstado();
    }

    @Test
    public void deberiaOrdenarProductosConDatosSimulados() {
        when(productoConCincoVentas.getCantidadVendida()).thenReturn(5);
        when(productoConDiezVentas.getCantidadVendida()).thenReturn(10);

        List<Producto> resultado =
            reporteService.obtenerProductosMasVendidos(
                Arrays.asList(
                    productoConCincoVentas,
                    productoConDiezVentas));

        assertSame(productoConDiezVentas, resultado.get(0));
        assertSame(productoConCincoVentas, resultado.get(1));
        verify(productoConCincoVentas, atLeastOnce()).getCantidadVendida();
        verify(productoConDiezVentas, atLeastOnce()).getCantidadVendida();
    }

    @Test
    public void deberiaIgnorarConsultasAlContarReclamos() {
        when(reclamo.getTipo()).thenReturn(Postventa.Tipo.RECLAMO);
        when(reclamo.getEstado()).thenReturn(Postventa.Estado.PENDIENTE);
        when(consulta.getTipo()).thenReturn(Postventa.Tipo.CONSULTA);

        Map<Postventa.Estado, Integer> resultado =
            reporteService.contarReclamosPorEstado(
                Arrays.asList(reclamo, consulta));

        assertEquals(Integer.valueOf(1),
            resultado.get(Postventa.Estado.PENDIENTE));
        verify(reclamo, times(2)).getEstado();
        verify(consulta, never()).getEstado();
    }
}
