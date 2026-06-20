package com.example.testeableapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.example.testeableapp.model.MenuData
import org.junit.Rule
import org.junit.Test

class RestaurantOrderTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    //  Mensaje de pedido vacío visible al inicio

    @Test
    fun mensajePedidoVacio_esVisibleAlIniciarLaApp() {
        composeTestRule.onNodeWithTag("emptyOrderMessage")
            .performScrollTo()
            .assertIsDisplayed()
    }

    //  Todos los items del menú visibles

    @Test
    fun todosLosItemsDelMenu_sonVisibles() {
        MenuData.items.forEach { item ->
            composeTestRule.onNodeWithTag("menuItem_${item.id}")
                .performScrollTo()
                .assertIsDisplayed()
            composeTestRule.onNodeWithTag("menuItemName_${item.id}")
                .assertTextEquals(item.name)
        }
    }

    //  El total general se actualiza

    @Test
    fun totalGeneral_seActualizaAlAgregarItems() {
        // Agregamos "Patatas Bravas" (id 1, 5.50€)
        composeTestRule.onNodeWithTag("addButton_1")
            .performScrollTo()
            .performClick()

        composeTestRule.onNodeWithTag("totalValue")
            .performScrollTo()
            .assertTextEquals("5.50 €")

        // Incrementamos para llegar a 2 unidades = 11.00€
        composeTestRule.onNodeWithTag("incrementOrderItem_1")
            .performScrollTo()
            .performClick()

        composeTestRule.onNodeWithTag("totalValue")
            .performScrollTo()
            .assertTextEquals("11.00 €")
    }


    // PRUEBAS DE UI ADICIONALES



    @Test
    fun alAgregarItem_desapareceMensajeVacioYApareceBotonPedido() {
        composeTestRule.onNodeWithTag("addButton_1")
            .performScrollTo()
            .performClick()

        composeTestRule.onNodeWithTag("emptyOrderMessage").assertDoesNotExist()

        composeTestRule.onNodeWithTag("placeOrderButton")
            .performScrollTo()
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("orderItem_1")
            .performScrollTo()
            .assertIsDisplayed()
    }


    @Test
    fun flujoCompletoDeRealizarPedido_muestraDialogoYReiniciaCarrito() {
        composeTestRule.onNodeWithTag("addButton_5") // Agua mineral 1.50€
            .performScrollTo()
            .performClick()
        composeTestRule.onNodeWithTag("addButton_5") // x2 = 3.00€
            .performScrollTo()
            .performClick()

        composeTestRule.onNodeWithTag("placeOrderButton")
            .performScrollTo()
            .performClick()

        composeTestRule.onNodeWithTag("confirmationDialog").assertIsDisplayed()

        composeTestRule.onNodeWithTag("confirmationOkButton").performClick()

        composeTestRule.onNodeWithTag("emptyOrderMessage")
            .performScrollTo()
            .assertIsDisplayed()
    }
}