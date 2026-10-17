package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.DetallePedido;
import com.pba.pbasistem.model.Producto;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetallePedidoMockitoTest {

    @Mock
    private Producto producto;

    @Test
    public void deberiaVerificarDescuentoDeStock() {
        DetallePedido detalle = crearDetalle(3);

        detalle.registrarVenta();

        verify(producto, times(1)).descontarStock(3);
        verify(producto, never()).devolverStock(anyInt());
        assertEquals(0, new BigDecimal("75.00")
            .compareTo(detalle.getSubtotal()));
    }

    @Test
    public void deberiaPropagarErrorCuandoNoHayStock() {
        DetallePedido detalle = crearDetalle(3);
        doThrow(new IllegalArgumentException("No hay stock suficiente"))
            .when(producto).descontarStock(3);

        assertThrows(
            IllegalArgumentException.class,
            detalle::registrarVenta);

        verify(producto, times(1)).descontarStock(3);
        verify(producto, never()).devolverStock(anyInt());
    }

    @Test
    public void deberiaVerificarDevolucionDeStockAlEliminar() {
        DetallePedido detalle = crearDetalle(3);
        detalle.registrarVenta();

        detalle.antesDeEliminar();

        verify(producto, times(1)).descontarStock(3);
        verify(producto, times(1)).devolverStock(3);
    }

    private DetallePedido crearDetalle(int cantidad) {
        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(new BigDecimal("25.00"));
        return detalle;
    }
}
