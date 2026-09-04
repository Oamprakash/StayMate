package com.staymate.booking.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.staymate.booking.data.RoomType
import com.staymate.booking.data.StayRepository
import com.staymate.booking.ui.screens.BookingScreen
import com.staymate.booking.ui.screens.BookingSuccessScreen
import com.staymate.booking.ui.screens.HomeScreen
import com.staymate.booking.ui.screens.MyBookingsScreen
import com.staymate.booking.ui.screens.PropertyDetailScreen

private object Routes {
    const val HOME = "home"
    const val BOOKINGS = "bookings"
    const val DETAIL = "detail/{propertyId}"
    const val BOOK = "book/{propertyId}/{roomType}"
    const val SUCCESS = "success/{bookingId}"

    fun detail(propertyId: String) = "detail/$propertyId"
    fun book(propertyId: String, roomType: RoomType) = "book/$propertyId/${roomType.name}"
    fun success(bookingId: String) = "success/$bookingId"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StayApp() {
    val navController = rememberNavController()
    val viewModel: StayViewModel = viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute == Routes.HOME || currentRoute == Routes.BOOKINGS

    Scaffold(
        topBar = {
            if (currentRoute == Routes.BOOKINGS) {
                TopAppBar(title = { Text("My bookings") })
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Routes.HOME,
                        onClick = {
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.HOME) { inclusive = true }
                            }
                        },
                        icon = { Icon(Icons.Default.Explore, contentDescription = null) },
                        label = { Text("Explore") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Routes.BOOKINGS,
                        onClick = { navController.navigate(Routes.BOOKINGS) },
                        icon = { Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = null) },
                        label = { Text("Bookings") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    viewModel = viewModel,
                    onPropertyClick = { navController.navigate(Routes.detail(it)) }
                )
            }

            composable(Routes.BOOKINGS) {
                MyBookingsScreen(viewModel = viewModel)
            }

            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
            ) { entry ->
                val propertyId = entry.arguments?.getString("propertyId").orEmpty()
                val property = viewModel.property(propertyId)
                if (property == null) {
                    navController.popBackStack()
                } else {
                    PropertyDetailScreen(
                        property = property,
                        onBack = { navController.popBackStack() },
                        onBookRoom = { room ->
                            navController.navigate(Routes.book(property.id, room.type))
                        }
                    )
                }
            }

            composable(
                route = Routes.BOOK,
                arguments = listOf(
                    navArgument("propertyId") { type = NavType.StringType },
                    navArgument("roomType") { type = NavType.StringType }
                )
            ) { entry ->
                val propertyId = entry.arguments?.getString("propertyId").orEmpty()
                val roomType = RoomType.valueOf(entry.arguments?.getString("roomType") ?: RoomType.SINGLE.name)
                val property = viewModel.property(propertyId)
                val room = property?.rooms?.firstOrNull { it.type == roomType }
                if (property == null || room == null) {
                    navController.popBackStack()
                } else {
                    BookingScreen(
                        property = property,
                        room = room,
                        onBack = { navController.popBackStack() },
                        onConfirm = { name, phone, moveInDate, months ->
                            val booking = viewModel.confirmBooking(
                                property = property,
                                room = room,
                                guestName = name,
                                phone = phone,
                                moveInDate = moveInDate,
                                months = months
                            )
                            navController.navigate(Routes.success(booking.id)) {
                                popUpTo(Routes.HOME)
                            }
                        }
                    )
                }
            }

            composable(
                route = Routes.SUCCESS,
                arguments = listOf(navArgument("bookingId") { type = NavType.StringType })
            ) { entry ->
                val bookingId = entry.arguments?.getString("bookingId").orEmpty()
                val booking = StayRepository.bookings.value.firstOrNull { it.id == bookingId }
                if (booking == null) {
                    navController.popBackStack()
                } else {
                    BookingSuccessScreen(
                        booking = booking,
                        onViewBookings = {
                            navController.navigate(Routes.BOOKINGS) {
                                popUpTo(Routes.HOME)
                            }
                        },
                        onBackHome = {
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.HOME) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}
