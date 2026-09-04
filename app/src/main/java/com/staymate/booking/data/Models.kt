package com.staymate.booking.data

enum class PropertyType(val label: String) {
    HOSTEL("Hostel"),
    PG("PG")
}

enum class GenderPolicy(val label: String) {
    BOYS("Boys"),
    GIRLS("Girls"),
    CO_LIVING("Co-living")
}

enum class RoomType(val label: String, val occupancy: Int) {
    SINGLE("Single sharing", 1),
    DOUBLE("Double sharing", 2),
    TRIPLE("Triple sharing", 3)
}

data class RoomOption(
    val type: RoomType,
    val monthlyRent: Int,
    val bedsAvailable: Int
)

data class Property(
    val id: String,
    val name: String,
    val type: PropertyType,
    val gender: GenderPolicy,
    val locality: String,
    val city: String,
    val rating: Double,
    val reviewCount: Int,
    val amenities: List<String>,
    val rooms: List<RoomOption>,
    val securityDeposit: Int,
    val description: String,
    val accentColor: Long
) {
    val startingRent: Int get() = rooms.minOf { it.monthlyRent }
    val address: String get() = "$locality, $city"
}

data class Booking(
    val id: String,
    val property: Property,
    val room: RoomOption,
    val guestName: String,
    val phone: String,
    val moveInDate: String,
    val months: Int
) {
    val totalPayable: Int get() = room.monthlyRent * months + property.securityDeposit
}

data class Filters(
    val query: String = "",
    val city: String? = null,
    val type: PropertyType? = null,
    val gender: GenderPolicy? = null,
    val maxRent: Int? = null
)
