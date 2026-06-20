package com.example.testeableapp

import org.junit.Test
import org.junit.Assert.*


class ExampleUnitTest {
    //  Agregar item al pedido
    @Test
    fun agregarItem_loAgregaAlPedidoConCantidadUno() {
        val viewModel = RestaurantViewModel()

        viewModel.addItem(1) // Patatas Bravas

        val cantidades = viewModel.quantities.value
        assertEquals(1, cantidades[1])
        assertTrue(viewModel.orderedItems.value.any { it.id == 1 })
    }


// Incrementar / Decrementar cantidad

    @Test
    fun incrementarItem_aumentaCantidadEnUno() {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1) // cantidad = 1

        viewModel.incrementItem(1) // cantidad = 2

        assertEquals(2, viewModel.quantities.value[1])
    }

    @Test
    fun decrementarItem_disminuyeCantidadEnUno() {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1)
        viewModel.incrementItem(1) // cantidad = 2

        viewModel.decrementItem(1) // cantidad = 1

        assertEquals(1, viewModel.quantities.value[1])
    }

    //  Eliminar item al decrementar desde 1

    @Test
    fun decrementarDesdeUno_eliminaElItemDelPedido() {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1) // cantidad = 1

        viewModel.decrementItem(1) // debe eliminarse, no quedar en 0

        assertNull(viewModel.quantities.value[1])
        assertFalse(viewModel.orderedItems.value.any { it.id == 1 })
        assertTrue(viewModel.isEmpty.value)
    }


    // Cálculo del total a pagar

    @Test
    fun calcularTotal_sumaCorrectamentePrecioPorCantidad() {
        val viewModel = RestaurantViewModel()

        viewModel.addItem(1) // Patatas Bravas 5.50
        viewModel.incrementItem(1) // x2 = 11.00
        viewModel.addItem(5) // Agua mineral 1.50 x1

        val totalEsperado = (5.50 * 2) + (1.50 * 1) // 12.50
        assertEquals(totalEsperado, viewModel.total.value, 0.001)
    }

    // PRUEBAS UNITARIAS ADICIONALES (análisis propio) - 2 pts


    @Test
    fun placeOrder_conPedidoVacio_noGeneraConfirmacion() {
        val viewModel = RestaurantViewModel()

        viewModel.placeOrder()

        assertNull(viewModel.confirmation.value)
    }

    @Test
    fun dismissConfirmation_reiniciaElPedidoCompleto() {
        val viewModel = RestaurantViewModel()
        viewModel.addItem(1)
        viewModel.addItem(5)
        viewModel.placeOrder()
        assertNotNull(viewModel.confirmation.value) // precondición

        viewModel.dismissConfirmation()

        assertNull(viewModel.confirmation.value)
        assertTrue(viewModel.quantities.value.isEmpty())
        assertTrue(viewModel.isEmpty.value)
        assertEquals(0.0, viewModel.total.value, 0.001)
    }
}