import java.net.*;

import java.util.Scanner;

public class Cliente {


    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        DatagramSocket socket = new DatagramSocket();

        socket.setSoTimeout(3000); // 3 segundos timeout


        String dominio = sc.nextLine();  // Cambia aquí para probar otros dominios

        String mensaje = "QUERY " + dominio;


        InetAddress servidor = InetAddress.getByName("localhost");

        byte[] buffer = mensaje.getBytes();


        DatagramPacket request = new DatagramPacket(buffer, buffer.length, servidor, 5000);

        socket.send(request);


        try {

            byte[] buf = new byte[1024];

            DatagramPacket response = new DatagramPacket(buf, buf.length);

            socket.receive(response);


            String respuesta = new String(response.getData(), 0, response.getLength());

            System.out.println("Respuesta: " + respuesta);


        } catch (SocketTimeoutException e) {

            System.out.println("Error: no se recibió respuesta del servidor.");

        }


        socket.close();

    }

}
