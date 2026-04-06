import java.io.BufferedReader;
import java.io.FileReader;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;


public class Servidor
{
    private static Map<String,String> tablaDns = new HashMap<String,String>();

    public static void cargarDns(String archivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            if(linea.trim().isEmpty()) continue; // Ignorar líneas vacías
            String[] partes = linea.split(",");
            if (partes.length < 2) continue; // Línea malformada
            tablaDns.put(partes[0].trim(), partes[1].trim());
        }
        br.close();
    }

    public static void main(String[] args)throws Exception{
      int puerto = Integer.parseInt(args[0]);
      String archivo = args[1];
      cargarDns(archivo);


      DatagramSocket socket = new DatagramSocket(puerto);
      System.out.println("Servidor conectado en puerto: " + puerto);

      while(true){
          byte[] buf = new byte[1024];
          DatagramPacket packet = new DatagramPacket(buf, buf.length);
          socket.receive(packet);

          String datos = new String(packet.getData(), 0, packet.getLength());

          String[] partes = datos.split(" ");
          String dominio = partes[1];

          String respuesta;

          if(tablaDns.containsKey(dominio)){
              respuesta = "OK " + tablaDns.get(dominio);
          } else {
              respuesta = "NOT_FOUND";
          }

          byte[] resp = respuesta.getBytes();

          DatagramPacket response = new DatagramPacket(
                  resp, resp.length,
                  packet.getAddress(), packet.getPort()
          );

          socket.send(response);




      }

    }
}
