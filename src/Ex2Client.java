import java.io.*;
import java.net.Socket;
import java.util.zip.CRC32;

public final class Ex2Client {
    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("18.221.102.182",38102)) {
            System.out.println("Connected to server.");
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            BufferedReader brIS = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            int inp1,inp2;
            byte[] list = new byte[100];
            for(int i=0;i<100;i++){
                inp1=(br.read()* 16);
                inp2=br.read();
                list[i]=(byte) (inp1+inp2);
            }
            CRC32 crc32 = new CRC32();
            crc32.update(list,0,list.length);;
            String hexRes = Integer.toHexString((int)crc32.getValue());
            System.out.println("Generated CRC32: "+hexRes);
            list = hexStringToByteArray(hexRes);
            for(int i=0;i<list.length;i++){
                out.println(list[i]);
            }
            if(br.read()==1)
                System.out.println("Response good");
            else
                System.out.println("Response bad");
            is.close();
            isr.close();
            br.close();
            brIS.close();
            socket.close();
            System.out.println("Disconnected from server.");
        }
    }
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
