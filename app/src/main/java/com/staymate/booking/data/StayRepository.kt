package com.staymate.booking.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object StayRepository {

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    fun properties(filters: Filters): List<Property> = MockProperties.all.filter { property ->
        val query = filters.query.trim()
        val matchesQuery = query.isEmpty() ||
            property.name.contains(query, ignoreCase = true) ||
            property.locality.contains(query, ignoreCase = true) ||
            property.city.contains(query, ignoreCase = true)

        matchesQuery &&
            (filters.city == null || property.city == filters.city) &&
            (filters.type == null || property.type == filters.type) &&
            (filters.gender == null || property.gender == filters.gender) &&
            (filters.maxRent == null || property.startingRent <= filters.maxRent)
    }.sortedByDescending { it.rating }

    fun property(id: String): Property? = MockProperties.all.firstOrNull { it.id == id }

    fun book(
        property: Property,
        room: RoomOption,
        guestName: String,
        phone: String,
        moveInDate: String,
        months: Int
    ): Booking {
        val booking = Booking(
            id = "BK${(1000..9999).random()}",
            property = property,
            room = room,
            guestName = guestName,
            phone = phone,
            moveInDate = moveInDate,
            months = months
        )
        _bookings.value = _bookings.value + booking
        return booking
    }

    fun cancel(bookingId: String) {
        _bookings.value = _bookings.value.filterNot { it.id == bookingId }
    }
}
