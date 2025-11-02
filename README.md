# 💚 VidaSalud App

**VidaSalud App** es una aplicación móvil de bienestar y salud desarrollada en **Kotlin** y **Jetpack Compose**.

Permite a los usuarios registrarse y hacer seguimiento de sus métricas diarias (pasos, peso, sueño, calorías), mostrando un resumen personalizado de su progreso.

---

## ✨ Características Principales

* **Autenticación Sólida (MVVM + Firebase):** Flujo completo de registro y login con validación de campos, manejo de estado y conexión a **Firebase Authentication** y **Cloud Firestore**.
* **Manejo de Perfil:** Pantalla dedicada para visualizar datos de usuario, y **permitir la selección de foto de perfil con acceso a la Galería de Fotos** (usando **Coil** y **Firestore URI**), además de la funcionalidad de cerrar sesión.
* **Registro de Datos Diarios:** Pantalla de estadísticas con **formulario para registrar** peso, calorías, sueño y pasos. La interfaz incluye una **vista de Calendario** para seleccionar y persistir los datos por fecha en Firestore.
* **Diseño Moderno:** Interfaz de usuario declarativa basada en **Jetpack Compose**, con navegación inferior de 4 pestañas (Resumen, Datos, Comunidad, Perfil).

---

## 🛠️ Tecnologías y Arquitectura

| Categoría | Elementos Clave |
| :--- | :--- |
| **Lenguaje/UI** | **Kotlin** (100% nativo) y **Jetpack Compose** (declarativo). |
| **Arquitectura** | **MVVM** (Model-View-ViewModel) para una separación limpia de la lógica. |
| **Backend** | **Firebase Authentication** (gestión de usuarios) y **Cloud Firestore** (persistencia de datos de usuario y registros). |
| **Persistencia Local** | Uso de **Android URI Permissions** para mantener el acceso a la foto de perfil del usuario. |
| **Librerías Clave** | **Jetpack Navigation**, **Coil** (carga de imágenes). |

---

## 🚧 Trabajo en Progreso

El foco de trabajo pendiente es la **Integración de Sensores de Salud**, incluyendo:

* Conexión al **Contador de Pasos** del dispositivo.
* Implementación de la API de **Health Connect** para obtener datos de actividad física.

---

## 🧠 Autores

**Francisco Vera** | **Rodrigo Vargas**
📍 Duoc UC — Sede Puerto Montt
📘 Curso: DSY1105 - Desarrollo de Aplicaciones Móviles
