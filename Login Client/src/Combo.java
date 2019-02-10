public class Combo {


    private String Username;
    private String Password;
    private String complete;

    public Combo(String u, String p) {
        Username = u;
        Password = p;
        complete = Username + ":" + Password;
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
