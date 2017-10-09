import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;

public final class Ex2Client {
    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("18.221.102.182",38102)) {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            BufferedReader brIS = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            int inp1,inp2;
            byte[] list = new byte[100];
            // Sends out ints from 0-15 because of hex
            for(int i=0;i<100;i++){
                inp1=br.read();
                inp2=br.read();
                byte t1 = (byte) inp1;
                byte t2 = (byte) inp2;
                list[i]=temp;
            }
            CRC32 crc32 = new CRC32();
            crc32.update(list);
            out.println(crc32.getValue());
            System.out.println(br.readLine());
            is.close();
            isr.close();
            br.close();
            brIS.close();
            socket.close();
        }
    }
}
