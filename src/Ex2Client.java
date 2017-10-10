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
            String cast1,cast2;
            byte[] list = new byte[100];
            for(int i=0;i<100;i++){
                inp1=br.read();
              //  cast1= ""+inp1;
                inp2=br.read();
              //  cast2= ""+inp2;
                byte t1 = (byte) inp1;
   //             System.out.println(inp1+","+t1);
                byte t2 = (byte) inp2;
            //    System.out.println(inp2+","+t2);
                list[i]=(byte) (t1+t2);
           //     System.out.println((byte) (t1+t2));
            }
            CRC32 crc32 = new CRC32();
            crc32.update(list);
            System.out.println(crc32.getValue());
            System.out.println((byte) crc32.getValue());
            out.println(crc32.getValue());
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
