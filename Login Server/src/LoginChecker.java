import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class LoginChecker {

    LoginList combos = new LoginList();

    String site;

    public LoginChecker(String s) {
        site = s;
        try {
            URL url = new URL(site);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = in.readLine();
            while (str != null) {
                String meme = removeHtmlTags(str);
                if (meme.charAt(0) == '=') {
                    combos.add(meme.substring(1));
                }
                try {
                    str = in.readLine();
                } catch (NullPointerException ex) {
                    break;
                }
            }
            in.close();

        } catch (MalformedURLException e) {
        } catch (ConnectException ex) {
        } catch (IOException e) {
        }

    }

    public boolean comboCheck(String s) {
        return combos.contains(s);
    }


    private String removeHtmlTags(String str) {
        char[] letters = str.toCharArray();

        ArrayList<String> key = new ArrayList<String>();

        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == '<') {
                for (int j = i + 1; j < letters.length; j++) {
                    if (letters[j] == '<') {
                        i = j - 1;
                        break;
                    } else if (letters[j] == '>' && j > i + 1) {
                        String x = "";
                        for (int k = i + 1; k < j; k++) {
                            x += letters[k];
                        }
                        for (int k = 0; k < x.length(); k++) {
                            if (!Character.isLetter(x.charAt(k))) {
                                x = "";
                                break;
                            }
                        }
                        if (!x.equals("")) {
                            key.add(x);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < key.size(); i++) {
            if (str.contains("</" + key.get(i) + ">")) {
                int index = str.indexOf("<" + key.get(i) + ">");
                str = str.replace(str.substring(index,
                        index + key.get(i).length() + 2), "");
                int index2 = str.indexOf("</" + key.get(i) + ">");
                str = str.replace(str.substring(index2,
                        index2 + key.get(i).length() + 3),
                        "");
            }
        }
        return str;
    }
}
