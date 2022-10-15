package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModel : ViewModel() {

    // Quantity of cupcakes in this order
    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> = _quantity

    // Cupcake flavor for this order
    private val _flavor = MutableLiveData<String>("")
    val flavor: LiveData<String> = _flavor

    // Pickup date
    private val _date = MutableLiveData<String>("")
    val date: LiveData<String> = _date

    // Possible date options
    val dateOptions: List<String> = getPickupOptions()

    // Price of the order so far
    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<Double> = _price


    /**
     * Set the quantity of cupcakes for this order.
     *
     * @param numberCupcakes to order
     */
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
    }

    /**
     * Set the flavor of cupcakes for this order. Only 1 flavor can be selected for the whole order.
     *
     * @param desiredFlavor is the cupcake flavor as a string
     */
    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    /**
     * Returns true if a flavor has not been selected for the order yet. Returns false otherwise.
     */
    fun hasNoFlavorSet(): Boolean {
        return  _flavor.value.isNullOrEmpty()
    }

    /**
     * Set the pickup date for this order.
     *
     * @param pickupDate to pickup order
     */
    fun setDate(pickupDate: String) {
        _date.value = pickupDate
    }

    /**
     * Returns a list of date options starting with the current date and the following 3 dates.
     */
    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()

        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        // This variable will contain the current date and time
        val calendar = Calendar.getInstance()

        // Create a list of dates starting with the current date and the following 3 dates
        repeat(4) {
            // Adds current date to option list
            options.add(formatter.format(calendar.time))
            // Adds one day to the current date
            calendar.add(Calendar.DATE, 1)
        }

        return options
    }
}