package com.alespotify.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.alespotify.model.User
import com.alespotify.ui.MyColors
import com.alespotify.ui.navigation.LoginViewModel

@Composable
fun RegisterForm(
    loginViewModel: LoginViewModel,
    onRegisterSuccess: (User) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = nombre,
            singleLine = true,
            onValueChange = { nombre = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White
            ),
            label = { Text("Nombre") }
        )
        OutlinedTextField(
            value = email,
            singleLine = true,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White
            ),
            label = { Text("Email") }
        )
        OutlinedTextField(
            value = password,
            singleLine = true,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White
            ),
            label = { Text("Contraseña") },
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description =
                    if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description, tint = Color.White)
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (imageUri == null) "Seleccionar imagen" else "Imagen seleccionada",
                color = Color.White
            )
            Button(
                onClick = { /* todo */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = MyColors.primary)
            ) {
                Icon(
                    Icons.Filled.Image,
                    contentDescription = "Seleccionar imagen",
                    tint = Color.White
                )
            }
        }
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = "Imagen seleccionada",
            modifier = Modifier.size(80.dp).clip(CircleShape)
        )

        Button(
            onClick = {
                // Aquí deberías llamar a una función en tu ViewModel para registrar al usuario
                loginViewModel.registro(nombre, email, password, imageUri)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MyColors.secondary)
        ) {
            Text("Registrarse", color = Color.White)
        }

        if (loginViewModel.isLoading) {
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator()
        }

        loginViewModel.errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colors.error)
        }

        loginViewModel.registrationResult?.let { user ->
            LaunchedEffect(user) {
                onRegisterSuccess(user)
            }
        }
    }

}

@Composable
fun RegisterScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    onRegistrationSuccess: (User) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(MyColors.background).padding(top = 50.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(40.dp).clip(CircleShape)
                            .background(MyColors.secondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PersonAdd,
                            contentDescription = "Registro",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Alespotify",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Crear cuenta",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Introduce tus datos para registrarte",
                    color = MyColors.surface,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Formulario de Registro
                RegisterForm(loginViewModel, onRegistrationSuccess)

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { /* Navegar a la pantalla de inicio de sesión */ }) {
                    Text("¿Ya tienes una cuenta? Inicia sesión", color = MyColors.primary)
                }
            }
        }
    }
}