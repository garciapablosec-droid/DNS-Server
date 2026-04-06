# DNS-Server

## Descripción
Este proyecto implementa un **sistema DNS distribuido simplificado** usando **sockets UDP** en Java.  
Permite que un cliente consulte dominios a un **servidor principal**, que a su vez consulta múltiples **servidores secundarios** para resolver direcciones IP.

---

## Funcionalidades

- Resolución de dominios mediante **UDP**
- Servidor principal que reenvía consultas a varios servidores secundarios
- Servidores secundarios con tablas DNS locales cargadas desde archivos
- Cliente que envía consultas de dominios y recibe la IP correspondiente
- Manejo de respuestas `OK` o `NOT_FOUND`
- Timeout configurable para evitar bloqueos si un servidor no responde

---

## Tecnologías utilizadas

- **Java** – Lenguaje de programación del sistema
- **UDP DatagramSocket** – Comunicación entre cliente y servidores
- **HashMap** – Almacenamiento de tabla DNS local en cada servidor
- **Lectura de archivos** – Para cargar la tabla DNS de cada servidor
- **Scanner** – Entrada de usuario desde consola

---

## Estructura del proyecto

- `Cliente.java` – Envía consultas de dominio al servidor principal y muestra la respuesta
- `ServidorPrincipal.java` – Recibe consultas de clientes y las reenvía a los servidores secundarios
- `Servidor.java` – Servidor secundario que tiene una tabla DNS local y responde con la IP correspondiente
- **Archivos de tabla DNS** – Ejemplo: `dns.txt` con líneas como:

---

## Instalación y ejecución
## Instalación y ejecución


# 1. Clonar o descargar el proyecto en tu máquina

# 2. Compilar todos los archivos Java
javac Cliente.java Servidor.java ServidorPrincipal.java

# 3. Ejecutar los servidores secundarios (puertos 5001, 5002, 5003)
java Servidor 5001 dns1.txt
java Servidor 5002 dns2.txt
java Servidor 5003 dns3.txt

# 4. Ejecutar el servidor principal (puerto 5000)
java ServidorPrincipal

# 5. Ejecutar el cliente y escribir el dominio que deseas consultar
java Cliente
