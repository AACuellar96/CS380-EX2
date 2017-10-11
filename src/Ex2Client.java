import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;
import java.nio.ByteBuffer;

public final class Ex2Client {
    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("18.221.102.182",38102)) {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            BufferedReader brIS = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            int inp1,inp2;
            String cast1,cast2;
            byte[] list = new byte[100];
            for(int i=0;i<100;i++){
                inp1=(br.read()* 16);
                inp2=br.read();
                list[i]=(byte) (inp1+inp2);
            }
            CRC32 crc32 = new CRC32();
            crc32.update(list,0,list.length);
            System.out.println(crc32.getValue());
            String hexRes = Integer.toHexString((int)crc32.getValue());
            System.out.println(hexRes);
            if(br.read()==1)
                System.out.println("Response good");
            else
                System.out.println("Response bad");
            is.close();
            isr.close();
            br.close();
            brIS.close();
            socket.close();
        }
    }
}
