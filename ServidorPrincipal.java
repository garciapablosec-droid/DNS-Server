import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorPrincipal {

    public static String consultar(String host, int puerto, String mensaje) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(2000); // 2 segundos por servidor

            byte[] buffer = mensaje.getBytes();
            InetAddress dir = InetAddress.getByName(host);

            DatagramPacket request = new DatagramPacket(buffer, buffer.length, dir, puerto);
            socket.send(request);

            byte[] buf = new byte[1024];
            DatagramPacket response = new DatagramPacket(buf, buf.length);
            socket.receive(response);

            socket.close();
            return new String(response.getData(), 0, response.getLength());

        } catch (Exception e) {
            return null; // timeout o error
        }
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(5000);
        System.out.println("Servidor principal en 5000");

        int[] servidores = {5001, 5002, 5003};

        while (true) {
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String query = new String(packet.getData(), 0, packet.getLength());
            String respuesta = null;

            for (int puerto : servidores) {
                String respServidor = consultar("localhost", puerto, query);

                if (respServidor != null && respServidor.startsWith("OK")) {
                    respuesta = respServidor; // solo asigna si es OK
                    break;
                }
                // si respServidor es null o NOT_FOUND, sigue al siguiente servidor
            }

            if (respuesta == null) {
                respuesta = "NOT_FOUND"; // ninguno respondió OK
            }

            byte[] respBytes = respuesta.getBytes();
            DatagramPacket response = new DatagramPacket(
                    respBytes, respBytes.length,
                    packet.getAddress(), packet.getPort()
            );
            socket.send(response);
        }
    }
}


