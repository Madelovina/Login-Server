import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

// jar cfve server.jar s s.class s$1.class LoginChecker.class LoginList.class

public class s {

    private static HashMap<String, String> pwMap = new HashMap<>();

    private static JFrame frame = new JFrame();
    private static JPanel panel = new JPanel();
    private static JTextArea label = new JTextArea("Server is on. ", 10, 20);

    public static void main(String[] args) throws Exception {
        UI();

        try {
            loadHashMap(pwMap);
        } catch (FileNotFoundException ex) {
            pushHashMap(pwMap);
        }

        String str = "";
        while (!str.equals("quit")) {
            try {
                ServerSocket ss = new ServerSocket(8888);
                Socket s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                str = dis.readUTF();
                if (str.charAt(0) == 'r')
                    handleRegister(str, s);
                else if (str.charAt(0) == 'l')
                    handleLogin(str, s);
                ss.close();
            } catch (EOFException e) {
                System.out.println(e.toString());
            }
        }
    }

    private static void UI() {
        label.setEditable(false);
        label.setLineWrap(true);
        JScrollPane pane = new JScrollPane(label);
        pane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(250, closeButton.getHeight()));
        frame.setSize(new Dimension(250, 235));
        panel.add(pane);
        panel.add(closeButton);

        closeButton.addActionListener(e -> {
            frame.dispose();
            System.exit(0);
        });

        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static void handleRegister(String str, Socket s) {
        try {
            label.setText(str.substring(1));
            panel.repaint();
            OutputStream os = s.getOutputStream();
            if (pwMap.containsKey(str.substring(1, str.indexOf(":")))) {
                os.write(0);
            } else {
                os.write(1);
                pwMap.put(str.substring(1, str.indexOf(":")), str.substring(str.indexOf(":") + 1));
                pushHashMap(pwMap);
            }
            os.flush();
            os.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static void handleLogin(String str, Socket s) {
        try {
            OutputStream os = s.getOutputStream();
            if (pwMap.containsKey(str.substring(1, str.indexOf(":"))) && pwMap.get(str.substring(1, str.indexOf(":"))).equals(str.substring(str.indexOf(":") + 1))) {
                os.write(1);
            } else {
                os.write(0);
            }
            os.flush();
            os.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private static void loadHashMap(HashMap<String, String> hm) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("data.properties"));
        for (String key : properties.stringPropertyNames()) {
            hm.put(key, properties.get(key).toString());
        }
    }

    private static void pushHashMap(HashMap<String, String> hm) throws Exception {
        Properties properties = new Properties();
        for (HashMap.Entry<String, String> entry : hm.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }
        properties.store(new FileOutputStream("data.properties"), null);
    }
}