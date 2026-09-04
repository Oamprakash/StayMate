package com.staymate.booking.data

object MockProperties {

    val all: List<Property> = listOf(
        Property(
            id = "p1",
            name = "Sunrise Boys Hostel",
            type = PropertyType.HOSTEL,
            gender = GenderPolicy.BOYS,
            locality = "Kothrud",
            city = "Pune",
            rating = 4.5,
            reviewCount = 128,
            amenities = listOf("WiFi", "Meals", "Laundry", "Power backup", "CCTV"),
            rooms = listOf(
                RoomOption(RoomType.SINGLE, 9500, 2),
                RoomOption(RoomType.DOUBLE, 6800, 5),
                RoomOption(RoomType.TRIPLE, 5200, 8)
            ),
            securityDeposit = 10000,
            description = "Walking distance from the university gate. Home-style meals thrice a day, " +
                "study hall open till midnight and 24x7 warden support.",
            accentColor = 0xFF1F6F5C
        ),
        Property(
            id = "p2",
            name = "Lotus Girls PG",
            type = PropertyType.PG,
            gender = GenderPolicy.GIRLS,
            locality = "HSR Layout",
            city = "Bengaluru",
            rating = 4.7,
            reviewCount = 214,
            amenities = listOf("WiFi", "Meals", "AC", "Housekeeping", "Biometric entry"),
            rooms = listOf(
                RoomOption(RoomType.SINGLE, 15500, 1),
                RoomOption(RoomType.DOUBLE, 11000, 4)
            ),
            securityDeposit = 20000,
            description = "Fully furnished PG for working women with biometric access, daily housekeeping " +
                "and a rooftop common area.",
            accentColor = 0xFF8E3B7A
        ),
        Property(
            id = "p3",
            name = "Urban Nest Co-living",
            type = PropertyType.PG,
            gender = GenderPolicy.CO_LIVING,
            locality = "Powai",
            city = "Mumbai",
            rating = 4.3,
            reviewCount = 96,
            amenities = listOf("WiFi", "Gym", "Housekeeping", "Parking", "Lift"),
            rooms = listOf(
                RoomOption(RoomType.SINGLE, 18000, 3),
                RoomOption(RoomType.DOUBLE, 12500, 2),
                RoomOption(RoomType.TRIPLE, 9500, 6)
            ),
            securityDeposit = 25000,
            description = "Modern co-living close to the tech park. Community events every weekend and " +
                "flexible one-month notice period.",
            accentColor = 0xFF2A5C9A
        ),
        Property(
            id = "p4",
            name = "Green Valley Hostel",
            type = PropertyType.HOSTEL,
            gender = GenderPolicy.BOYS,
            locality = "Vijay Nagar",
            city = "Indore",
            rating = 4.1,
            reviewCount = 64,
            amenities = listOf("WiFi", "Meals", "Study room", "Water purifier"),
            rooms = listOf(
                RoomOption(RoomType.DOUBLE, 5500, 6),
                RoomOption(RoomType.TRIPLE, 4200, 9)
            ),
            securityDeposit = 6000,
            description = "Budget friendly hostel for students preparing for competitive exams. " +
                "Quiet hours enforced from 10 PM.",
            accentColor = 0xFF4C7A2E
        ),
        Property(
            id = "p5",
            name = "Serene Ladies Hostel",
            type = PropertyType.HOSTEL,
            gender = GenderPolicy.GIRLS,
            locality = "Anna Nagar",
            city = "Chennai",
            rating = 4.6,
            reviewCount = 152,
            amenities = listOf("WiFi", "Meals", "AC", "Laundry", "CCTV", "Warden"),
            rooms = listOf(
                RoomOption(RoomType.SINGLE, 12000, 2),
                RoomOption(RoomType.DOUBLE, 8500, 3),
                RoomOption(RoomType.TRIPLE, 6500, 4)
            ),
            securityDeposit = 12000,
            description = "Safe and secure hostel with a resident warden, curfew at 10 PM and " +
                "South Indian vegetarian meals included.",
            accentColor = 0xFFB4552B
        ),
        Property(
            id = "p6",
            name = "Skyline Executive PG",
            type = PropertyType.PG,
            gender = GenderPolicy.CO_LIVING,
            locality = "Sector 62",
            city = "Noida",
            rating = 4.2,
            reviewCount = 78,
            amenities = listOf("WiFi", "AC", "Meals", "Parking", "Power backup"),
            rooms = listOf(
                RoomOption(RoomType.SINGLE, 13500, 4),
                RoomOption(RoomType.DOUBLE, 9000, 5)
            ),
            securityDeposit = 15000,
            description = "Executive PG for working professionals with dedicated work desks in every " +
                "room and a fully equipped shared kitchen.",
            accentColor = 0xFF5B4B8A
        )
    )

    val cities: List<String> = all.map { it.city }.distinct().sorted()
}
