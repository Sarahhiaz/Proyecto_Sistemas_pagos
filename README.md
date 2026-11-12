# Sistema de Procesamiento de Pagos 💳 💵 🧾 💻

Sistema de Procesamiento de Pagos es una aplicación desarrollada para facilitar el registro de ventas y el procesamiento de diferentes formas de pago dentro de un mismo sistema. La aplicación permite realizar pagos en efectivo, tarjeta, transferencia bancaria y medios electrónicos, aplicando validaciones específicas para cada método antes de completar una transacción.

El sistema también permite llevar un control de las transacciones realizadas, identificar pagos aprobados o rechazados y generar un resumen general de las operaciones. Para su desarrollo se aplicaron diferentes conceptos de Programación Orientada a Objetos, como clases, objetos, interfaces, herencia, polimorfismo, encapsulamiento y manejo de excepciones.

## Para comenzar ▶️

Para ejecutar correctamente el proyecto es necesario descargar o clonar el repositorio y abrirlo utilizando Apache NetBeans.

El sistema cuenta con los siguientes módulos principales:

* Registro de ventas 🧾
* Procesamiento de pagos 💳
* Pago en efectivo 💵
* Pago con tarjeta 💳
* Transferencia bancaria 🏦
* Pago electrónico 💻
* Consulta de transacciones 🔎
* Resumen de pagos 📊
* Validación de datos ingresados ✅

## Requisitos 👀

```text
Java JDK 21
Apache NetBeans 22
Sistema operativo compatible con Java
Git para control de versiones
GitHub para almacenamiento del repositorio
MongoDB para la persistencia de información
```

## Funcionalidades principales ⚙️

* Registrar ventas utilizando un código único.
* Validar que los campos obligatorios no se encuentren vacíos.
* Verificar que el importe de una venta sea numérico y mayor que cero.
* Seleccionar únicamente los métodos de pago disponibles en el sistema.
* Procesar pagos mediante efectivo, tarjeta, transferencia bancaria o pago electrónico.
* Validar los datos específicos correspondientes a cada método de pago.
* Verificar que el importe del pago corresponda al importe pendiente de la venta.
* Evitar el procesamiento de ventas inexistentes.
* Evitar que una venta que ya fue pagada pueda procesarse nuevamente.
* Verificar que el dinero recibido en efectivo sea suficiente para cubrir el importe de la venta.
* Registrar pagos aprobados y rechazados.
* Evitar que un pago rechazado cambie una venta al estado de pagada.
* Consultar las transacciones realizadas.
* Generar un resumen de los pagos registrados.

## Validaciones implementadas ✅

El sistema realiza diferentes comprobaciones antes de procesar una venta o un pago con el propósito de mantener la consistencia de la información.

Entre las principales validaciones se encuentran:

* El código de la venta debe ser único.
* La descripción y los campos obligatorios no pueden estar vacíos.
* No se aceptan campos que contengan únicamente espacios en blanco.
* El importe debe ser numérico y mayor que cero.
* El método de pago debe ser una de las opciones permitidas.
* Los datos específicos de cada método deben cumplir con el formato establecido.
* El número de tarjeta debe cumplir con el formato definido para las pruebas.
* El correo electrónico debe contener un formato válido.
* Los datos de transferencia bancaria deben estar completos.
* El importe del pago debe coincidir con el importe de la venta.
* El efectivo recibido debe ser suficiente para realizar el pago.
* Una venta pagada no puede volver a procesarse.
* Un pago rechazado no cambia el estado de la venta a pagada.
* Los datos utilizados durante las pruebas deben ser ficticios y no contener información financiera real.



## Desarrolladores 👥

* **Chavarria Diaz, Cristian Sarahi** - [Sarahhiaz 🐙](https://github.com/Sarahhiaz)
* **Lopez Hernandez, Cristian Alexander** - [Lh21012-ux 🐙](https://github.com/Lh21012-ux)

## Herramientas utilizadas 🔨

* [Apache NetBeans](https://netbeans.apache.org/) - Entorno de desarrollo utilizado para programar y organizar el proyecto en Java.
* [Java JDK 21](https://www.oracle.com/java/) - Plataforma utilizada para el desarrollo y ejecución de la aplicación.
* [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/) - Biblioteca utilizada para desarrollar las interfaces gráficas del sistema.
* [MongoDB](https://www.mongodb.com/) - Base de datos utilizada para almacenar la información del proyecto.
* [Git](https://git-scm.com/) - Herramienta utilizada para llevar el control de versiones del código fuente.
* [GitHub](https://github.com/) - Plataforma utilizada para almacenar el repositorio y registrar las contribuciones realizadas por los integrantes.
* [StarUML](https://staruml.io/) - Herramienta utilizada para apoyar el diseño de diagramas UML.
* [draw.io](https://app.diagrams.net/) - Herramienta utilizada para elaborar diagramas y representar gráficamente el funcionamiento del sistema.



## Enlaces de interés 🔎

* Repositorio de GitHub: agregar enlace del repositorio del proyecto.
* Documento técnico: agregar enlace cuando el documento se encuentre disponible.
* Manual de usuario: agregar enlace cuando el manual se encuentre disponible.
* Video de demostración: agregar enlace cuando el video se encuentre disponible.

## Licencia :closed_lock_with_key 🔐:


<a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/"><img alt="Licencia Creative Commons" style="border-width:0" src="https://i.creativecommons.org/l/by-nc/4.0/88x31.png" /></a><br />Esta obra está bajo una <a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/">Licencia Creative Commons Atribución-NoComercial 4.0 Internacional</a>.

Mira el archivo [LICENSE]() para mas detalles :scroll:

---

**Sistema de Procesamiento de Pagos - Proyecto de Programación Orientada a Objetos 💳**
