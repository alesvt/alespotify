# 🚀 Alespotify (Kotlin + JAVA)

[![Java API/Web](https://img.shields.io/badge/Java-Spring%20Boot-6DB33F?style=flat-square&logo=spring)](https://spring.io/)
[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?style=flat-square&logo=kotlin)](https://kotlinlang.org/lp/multiplatform/)
[![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android)](https://www.android.com/)
[![iOS](https://img.shields.io/badge/iOS-000000?style=flat-square&logo=apple)](https://www.apple.com/ios/swift/)
[![Desktop](https://img.shields.io/badge/Desktop-LightGray?style=flat-square&logo=java)](https://www.java.com/)

Una aplicación moderna construida con una API y web en Java (Spring Boot) y clientes nativos para Android, iOS y escritorio desarrollados con Kotlin Multiplatform.

## 💡 Descripción General

Este proyecto demuestra el poder de combinar la robustez y el ecosistema de Java con la flexibilidad y la eficiencia de Kotlin Multiplatform para crear una experiencia de usuario consistente y de alto rendimiento en diversas plataformas.

La **API y la interfaz web** están implementadas utilizando **Spring Boot**, proporcionando una base sólida y escalable para la lógica de negocio y la interacción con el usuario a través de navegadores web.

Los **clientes nativos** para **Android, iOS y escritorio** se construyen con **Kotlin Multiplatform**, lo que permite compartir una gran parte del código (lógica de negocio, modelos de datos, etc.) entre las diferentes plataformas, maximizando la eficiencia del desarrollo y manteniendo una coherencia en la funcionalidad.

## 🛠️ Tecnologías Utilizadas

**Backend & Web:**

* **Java:** Lenguaje de programación principal para la API y la web.
* **Spring Boot:** Framework de Java para un desarrollo rápido y sencillo de aplicaciones web y APIs.

**Mobile & Desktop:**

* **Kotlin:** Lenguaje de programación moderno y conciso.
* **Kotlin Multiplatform (KMP):** Framework de Kotlin que permite compartir código entre diferentes plataformas.
* **Android:** Plataforma móvil de Google.
* **iOS:** Plataforma móvil de Apple.
* **Compose Multiplatform:** Framework declarativo de UI para construir interfaces de usuario nativas en Android, iOS, desktop y web (puede que lo uses para la parte de escritorio).

## ⚙️ Cómo Empezar

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local:

### Requisitos Previos

Asegúrate de tener instaladas las siguientes herramientas:

* **Java Development Kit (JDK):** Versión compatible con Spring Boot.
* **Maven o Gradle:** Herramientas de gestión de dependencias para el proyecto Java.
* **Android Studio:** Para desarrollar y ejecutar la aplicación Android.
* **Xcode:** Para desarrollar y ejecutar la aplicación iOS (requiere macOS).
* **IntelliJ IDEA:** Recomendado para el desarrollo con Kotlin Multiplatform, con los plugins de Kotlin y Android instalados.
* **Kotlin:** El compilador de Kotlin se incluye generalmente con IntelliJ IDEA y Android Studio.

### Configuración

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/alesvt/alespotify
    cd alespotify/
    ```

2.  **Configurar el backend (API y Web - Java/Spring Boot):**
    * Requiere una base de datos en mongodb con las colecciones de `MongoColls`
    * Navega al directorio del backend (`alespotify/src`).
    * Configura las propiedades de la aplicación en `application.properties` (por ejemplo, la conexión a la base de datos).
    * Ejecuta la aplicación Spring Boot utilizando Maven o Gradle:
        ```bash
        ./mvnw spring-boot:run
        ```
    * La API y la interfaz web deberían estar accesibles en la dirección configurada (`http://localhost:8080`).

4.  **Configurar las aplicaciones cliente (Android, iOS, Desktop - Kotlin Multiplatform):**
    * Abre el proyecto raíz en Android Studio.
    * **Android:** Ejecuta la aplicación con la configuración `composeApp` en un emulador o dispositivo físico.
    * **iOS:** Abre el archivo `iosApp/iosApp.xcworkspace` en Xcode y ejecuta la aplicación en un simulador o dispositivo físico.
    * **Desktop:** Ejecuta la aplicación con la configuración `compose-multiplatform run` en Android Studio.

## 📂 Estructura del Proyecto
```
├── alespotify/             # Código fuente de la API y la aplicación web (Java/Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   └── test/
│   └── pom.xml          
├── alespotify-multiplatform/              
│   ├── composeApp/src
│   │   ├── commonMain/                      # Código compartido entre las plataformas (Kotlin Multiplatform)
│   │   ├── androidMain/                     # Código de cada parte específica
│   │   ├── desktopMain/
│   │   └── iosMain/
│   ├── build.gradle.kts
├── .gitignore
├── LICENSE
└── README.md
```

## ✨ ¡Gracias por explorar este proyecto! ✨
