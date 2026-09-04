package com.staymate.booking.ui

import androidx.lifecycle.ViewModel
import com.staymate.booking.data.Booking
import com.staymate.booking.data.Filters
import com.staymate.booking.data.GenderPolicy
import com.staymate.booking.data.Property
import com.staymate.booking.data.PropertyType
import com.staymate.booking.data.RoomOption
import com.staymate.booking.data.StayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StayViewModel : ViewModel() {

    private val _filters = MutableStateFlow(Filters())
    val filters: StateFlow<Filters> = _filters.asStateFlow()

    val bookings: StateFlow<List<Booking>> = StayRepository.bookings

    val results: List<Property> get() = StayRepository.properties(_filters.value)

    fun onQueryChange(query: String) {
        _filters.value = _filters.value.copy(query = query)
    }

    fun onCityChange(city: String?) {
        _filters.value = _filters.value.copy(city = city.takeIf { it != _filters.value.city })
    }

    fun onTypeChange(type: PropertyType?) {
        _filters.value = _filters.value.copy(type = type.takeIf { it != _filters.value.type })
    }

    fun onGenderChange(gender: GenderPolicy?) {
        _filters.value = _filters.value.copy(gender = gender.takeIf { it != _filters.value.gender })
    }

    fun onMaxRentChange(maxRent: Int?) {
        _filters.value = _filters.value.copy(maxRent = maxRent)
    }

    fun clearFilters() {
        _filters.value = Filters(query = _filters.value.query)
    }

    fun property(id: String): Property? = StayRepository.property(id)

    fun confirmBooking(
        property: Property,
        room: RoomOption,
        guestName: String,
        phone: String,
        moveInDate: String,
        months: Int
    ): Booking = StayRepository.book(property, room, guestName, phone, moveInDate, months)

    fun cancelBooking(bookingId: String) = StayRepository.cancel(bookingId)
}
