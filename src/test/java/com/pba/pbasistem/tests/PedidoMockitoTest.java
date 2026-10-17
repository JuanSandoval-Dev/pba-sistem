package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.DetallePedido;
import com.pba.pbasistem.model.Pedido;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoMockitoTest {

    @Mock
    private DetallePedido primerDetalle;

    @Mock
    private DetallePedido segundoDetalle;

    @Test
    public void deberiaCalcularTotalConDetallesMockeados() {
        when(primerDetalle.getSubtotal())
            .thenReturn(new BigDecimal("20.00"));
        when(segundoDetalle.getSubtotal())
            .thenReturn(new BigDecimal("35.00"));

        Pedido pedido = new Pedido();
        pedido.setDetalles(Arrays.asList(primerDetalle, segundoDetalle));

        pedido.calcularTotal();

        assertEquals(0, new BigDecimal("55.00")
            .compareTo(pedido.getTotal()));
        verify(primerDetalle, times(2)).getSubtotal();
        verify(segundoDetalle, times(2)).getSubtotal();
    }
}
