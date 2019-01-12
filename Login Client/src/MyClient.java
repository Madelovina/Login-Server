import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

public class MyClient {
    public boolean sendData(String str, String rl) {
        try {
            Socket s = new Socket();
            s.setSoTimeout(1000);
            final int timeOut = (int) TimeUnit.SECONDS.toMillis(1); // 5 sec wait period
            s.connect(new InetSocketAddress("localhost", 31337), timeOut);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF(rl + str);
            dout.flush();
            if (rl.equals("l")) {
                InputStream is = s.getInputStream();
                int i = is.read();
                if (i == 1)
                    return true;
                return false;
            }
            s.close();
            return true;
        } catch (ConnectException ex) {
            return false;
        } catch (SocketException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
