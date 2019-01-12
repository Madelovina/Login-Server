public class Combo {


    String Username;
    String Password;
    String complete = Username + "" + Password;

    public Combo(String u, String p) {
        Username = u;
        Password = p;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        Username = complete.substring(0, complete.indexOf(":"));
        Password = complete.substring(complete.indexOf(":") + 1);
        this.complete = complete;
    }

    public String toString() {
        return "Username: " + Username + "\t" + "Password: " + Password;
    }
}
