package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/** Price for a single cupcake */
private const val PRICE_PER_CUPCAKE = 2.00

/** Additional cost for same day pickup of an order */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {

    // Quantity of cupcakes in this order
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    // Cupcake flavor for this order
    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    // Flag to indicate if Special Flavor is selected
    private val _isSpecialFlavorSelected = MutableLiveData<Boolean>()

    // Pickup date
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    // Possible date options
    val dateOptions: List<String> = getPickupOptions()

    // Price of the order so far
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        // Format the price into the local currency and return this as LiveData<String>
        NumberFormat.getCurrencyInstance().format(it)
    }

    init {
        // Set initial values for the order
        resetOrder()
    }

    /**
     * Set the quantity of cupcakes for this order.
     *
     * @param numberCupcakes to order
     */
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
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
     * Set the Special flavor selected state. Special flavor doesn't allow same day pickup
     *
     * @param isSelected true if Special flavor selected, false otherwise
     */
    fun setSpecialFlavorSelected(isSelected: Boolean) {
        _isSpecialFlavorSelected.value = isSelected
    }

    /**
     * Returns true if Special flavor has been selected. Returns false otherwise.
     */
    fun isSpecialFlavorSelected(): Boolean? {
        return _isSpecialFlavorSelected.value
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
        updatePrice()
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

    /**
     * Updates the price based on the order details.
     */
    private fun updatePrice() {
        // Calculate price as quantity * PRICE_PER_CUPCAKE, if quantity.value is null set value
        // to 0, hence, price is 0.
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE

        // If the user selected the first option (today) for pickup, add the surcharge
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    /**
     * Reset the order by using initial default values for the quantity, flavor, date, and price.
     */
    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _isSpecialFlavorSelected.value = false
        _date.value = dateOptions[0]
        _price.value = 0.0
    }
}