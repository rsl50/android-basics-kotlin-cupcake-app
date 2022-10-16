package com.example.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcake.model.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ViewModelTests {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes() {
        // Instantiate the viewModel
        val viewModel = OrderViewModel()

        // Observe the LiveData value that will be changed
        viewModel.quantity.observeForever{}
        viewModel.setQuantity(12)

        // Check if the value is correct
        assertEquals(12, viewModel.quantity.value)
    }

    @Test
    fun price_twelve_cupcakes() {
        // Instantiate the viewModel
        val viewModel = OrderViewModel()

        // Observe the LiveData value that will be changed, otherwise the test will fail because
        // the Transformation only is executed if needed
        viewModel.price.observeForever{}

        // Set test cupcake quantity
        viewModel.setQuantity(12)

        // Each cupcake costs $2.00 and same day pickup is selected by default addin $3.00, thus
        // we have 12 * 2 = 24 + 3 = 27
        assertEquals("R$ 27,00", viewModel.price.value)
    }

}
