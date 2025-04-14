package com.alespotify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.compose.composable
import com.alespotify.model.Artist
import com.alespotify.model.Cancion
import com.alespotify.model.User
import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.ImagenDesdeApiScreen
// import com.alespotify.ui.screens.DatosScreen
import com.alespotify.ui.screens.LoginScreen
import com.alespotify.ui.screens.MainView
import com.alespotify.ui.screens.PlayScreen
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = DestinosNavegacion.LoginScreen.route,
    ) {
        composable(DestinosNavegacion.LoginScreen.route) { LoginScreen(navController) }
        composable(DestinosNavegacion.app.route) { /*APP(TODOS LOS PARAMETROS QUE LLEVE)*/ }
        composable(DestinosNavegacion.login.route) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""

            // hago la petición de login
            // si recibo codigo 200, paso el usuario (ahora paso cancion para probar)
            //Login()
            ImagenDesdeApiScreen()
           // DatosScreen()
            /*
//            PlayScreen(user = User(
//                ObjectId(),
//                "",
//                "",
//                "ales"),
//                song =
//                Cancion(ObjectId(),
//                    "",
//                    listOf(
//                        Artist(
//                            ObjectId(),
//                            "maiquel yacson",
//                            "imagen",
//                            "descrip",
//                            emptyList(),
//                            emptyList()
//                        )
//                    ),
//                    null,
//                    emptyList<String>(),
//                    250, "",
//                    "https://img.freepik.com/vector-gratis/casa-encantadora-ilustracion-arbol_1308-176337.jpg?semt=ais_country_boost&w=740",
//                    20, 1)
//
//                )
*/
            /* login funcion (llamada api y tal) */
        }
        composable(DestinosNavegacion.android.route) {
           //s DatosAndroid()
        }

    }
}

