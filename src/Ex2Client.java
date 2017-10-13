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
            PrintStream out = new PrintStream((socket.getOutputStream()),true,"UTF-8");
            int inp1,inp2;
            byte[] list = new byte[100];
            int[] list2 = new int[200];
            int j=0;
            for(int i=0;i<100;i++){
                inp1=br.read();
                inp2=br.read();
                list[i]=(byte) ((inp1*16)+inp2);
                list2[j]=inp1;
                j++;
                list2[j]=inp2;
                j++;
            }
            System.out.println("Received bytes:");
            for(int i=0;i<200;i+=20){
                System.out.println(" "+(Integer.toHexString(list2[i])+Integer.toHexString(list2[i+1])+Integer.toHexString(list2[i+2])+Integer.toHexString(list2[i+3])+Integer.toHexString(list2[i+4])
                        +Integer.toHexString(list2[i+5])+Integer.toHexString(list2[i+6])+Integer.toHexString(list2[i+7])+Integer.toHexString(list2[i+8])+Integer.toHexString(list2[i+9])+
                        Integer.toHexString(list2[i+10])+Integer.toHexString(list2[i+11])+Integer.toHexString(list2[i+12])+Integer.toHexString(list2[i+13])
                        +Integer.toHexString(list2[i+14])+Integer.toHexString(list2[i+15])+Integer.toHexString(list2[i+16])
                        +Integer.toHexString(list2[i+17])+Integer.toHexString(list2[i+18])+Integer.toHexString(list2[i+19])).toUpperCase());
            }
            CRC32 crc32  = new CRC32();
            crc32.update(list,0,list.length);;
            String hexRes = Integer.toHexString((int)crc32.getValue());
            System.out.println("Generated CRC32: "+hexRes.toUpperCase());
            list = new byte[4];
            if(hexRes.length()>7){
                list[0]=(byte) Integer.parseInt(hexRes.substring(0,2).toUpperCase(),16);
                list[1]=(byte) Integer.parseInt(hexRes.substring(2,4).toUpperCase(),16);
                list[2]=(byte) Integer.parseInt(hexRes.substring(4,6).toUpperCase(),16);
                list[3]=(byte) Integer.parseInt(hexRes.substring(6).toUpperCase(),16);
            }
            else{
                list[0]=(byte) Integer.parseInt(hexRes.substring(0,1).toUpperCase(),16);
                list[1]=(byte) Integer.parseInt(hexRes.substring(1,3).toUpperCase(),16);
                list[2]=(byte) Integer.parseInt(hexRes.substring(3,5).toUpperCase(),16);
                list[3]=(byte) Integer.parseInt(hexRes.substring(5).toUpperCase(),16);
            }
            out.write(list);
            if(br.read()==1)
                System.out.println("Response good.");
            else
                System.out.println("Response bad.");
            is.close();
            isr.close();
            br.close();
            brIS.close();
            socket.close();
            System.out.println("Disconnected from server.");
        }
    }
}
