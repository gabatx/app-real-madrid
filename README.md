## app-real-madrid
Proyecto primer trimestre de Android.

La aplicación cuenta con dos actividades principales, una de inicio de sesión y una de inicio que aloja los fragmentos que representan las diferentes secciones de la aplicación.

Actividad de inicio de sesión:

    Proporciona la opción de inicio de sesión a través de nombre de usuario y contraseña.
    Ofrece la opción de acceso para invitados.
    Recordatorio de inicio de sesión: Si el inicio de sesión es exitoso, se guarda el nombre de usuario en las preferencias.

Actividad de inicio:

    Incluye un menú lateral con el nombre de usuario, fecha y opciones.

Fragmento de inicio:

    Muestra un mensaje de bienvenida que incluye el nombre de usuario con el que se ha iniciado sesión y una imagen.

Fragmento de equipo:

    Presenta una lista de datos de la clase con diferentes tipos de información en un formato de cuadrícula.

Vista detallada:

    Al presionar un elemento en la vista, se abre otro fragmento para mostrar toda la información del elemento.
    Se transmiten todos los datos a través de Parcelable.
    El fragmento se integra en la navegación, permitiendo una retroalimentación.

Fragmento de galería:

    La galería muestra una serie de imágenes con paginación, utilizando un ViewPager.
    Se utiliza un RecyclerView horizontal.

Fragmento de contacto:

    El fragmento de contacto consta de una serie de botones de contacto y un formulario de contacto.
    El formulario de contacto envía un correo electrónico a una dirección específica.
    El formulario solo permite el envío si todos los campos están completos.
    Cuando todo está correcto, se muestra un cuadro de diálogo para confirmar el envío. Si se confirma, se abre la aplicación de correo electrónico con todos los datos proporcionados por el usuario para enviarlos a las direcciones especificadas.
    Los campos del formulario deben cumplir con ciertos requisitos. De lo contrario, no se podrán enviar.
    Botón de correo electrónico, al presionarlo abre la aplicación de correo electrónico con la dirección especificada.
    Botón de teléfono, realiza una llamada a un número específico.
    Botón de WhatsApp, abre una conversación con un número específico.

