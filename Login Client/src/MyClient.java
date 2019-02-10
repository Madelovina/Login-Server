import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class MyClient {
    public boolean sendData(String str, String rl) {
        try {
            Socket s = new Socket();
            s.setSoTimeout(1000);
            final int timeOut = (int) TimeUnit.SECONDS.toMillis(1); // 5 sec wait period
            s.connect(new InetSocketAddress("localhost", 8888), timeOut);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF(rl + str);
            dout.flush();
            InputStream is = s.getInputStream();
            int i = is.read();
            s.close();
            return i == 1;
        } catch (Exception ex) {
            return false;
        }
    }
}
