import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

// jar cfve server.jar s s.class s$1.class LoginChecker.class LoginList.class

public class s {
    static File websiteFile = new File("D:\\xampp\\htdocs\\index.html");
    static PrintWriter out;
    static BufferedReader br;

    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();
    static JScrollPane pane;
    static JTextArea label = new JTextArea("Server is on. ", 10, 20);
    static LoginChecker loginChecker = new LoginChecker("http://localhost:6969"); // http://yak5.ddns.net/


    public static void main(String[] args) {
        UI();

        String str = "";
        while (!str.equals("quit")) {
            try {
                ServerSocket ss = new ServerSocket(8888);
                Socket s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                str = (String) dis.readUTF();
                if (str.charAt(0) == 'r')
                    handleRegister(str);
                else if (str.charAt(0) == 'l')
                    handleLogin(str, s);
                ss.close();
            } catch (EOFException e) {
                System.out.println(e);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private static void UI() {
        label.setEditable(false);
        label.setLineWrap(true);
        pane = new JScrollPane(label);
        pane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JButton closeButton = new JButton("Close");
        closeButton.setSize(new Dimension(250, closeButton.getHeight()));
        frame.setSize(new Dimension(250, 235));
        panel.add(pane);
        panel.add(closeButton);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });

        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static void handleRegister(String str) {
        try {
            label.setText(str.substring(1));
            panel.repaint();
            ArrayList<String> doc = getOld(websiteFile);
            boolean contains = doc.contains("=" + str.substring(1));
            System.out.println(contains);
            newDoc(doc, str.substring(1), contains);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    private static void handleLogin(String str, Socket s) {
        try {
            int out;
            loginChecker = new LoginChecker("http://localhost:6969"); // http://yak5.ddns.net/
            if (loginChecker.comboCheck(str.substring(1))) {
                out = 1;
            } else
                out = 0;
            OutputStream os = s.getOutputStream();
            os.write(out);
            os.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private static ArrayList<String> getOld(File file) throws FileNotFoundException {
        ArrayList<String> out = new ArrayList<String>();
        br = new BufferedReader(new FileReader(file));
        String str = "";
        while (str != null) {
            try {
                str = br.readLine();
                if (!out.contains(str))
                    out.add(str);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        try {
            br.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return out;
    }

    private static void newDoc(ArrayList<String> doc, String str, boolean bool) {
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(websiteFile)));
            for (int i = 0; i < doc.size(); i++) {
                if (doc.get(i) != null) {
                    out.println(doc.get(i));
                }
                if (!bool && doc.get(i).contains("=") && doc.get(i).contains(":")) {
                    out.println("=" + str);
                    bool = true;
                }
            }
            out.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}