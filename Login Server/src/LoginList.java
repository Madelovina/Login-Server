import java.util.ArrayList;

public class LoginList extends ArrayList<String> {

    public String getUsername(int i) {
        String out = this.get(i);
        return out.substring(0, out.indexOf(":"));
    }

    public String getPassword(int i) {
        String out = this.get(i);
        return out.substring(1 + out.indexOf(":"));
    }

    /**
     * Returns whether this contains an object.
     *
     * @param o The object.
     * @return Whether this contains o.
     */
    public boolean contains(Object o) {
        this.sort(null);
        if (this.size() >= 1) {
            if (this.get(0).equals(o)) {
                return true;
            } else if (this.size() <= 1) {
                return false;
            }

            String mid = this.get(this.size() / 2 - 1);

            if (mid.equals((String) o)) {
                return true;
            } else if (mid.compareTo((String) o) < 0) {
                LoginList meme = new LoginList();
                for (int i = this.lastIndexOf(mid) + 1; i < this
                        .size(); i++) {
                    meme.add(this.get(i));
                }
                return meme.contains(o);
            } else {
                LoginList meme = new LoginList();
                for (int i = 0; i < this.indexOf(mid); i++) {
                    meme.add(this.get(i));
                }
                return meme.contains(o);
            }
        }
        return false;
    }


    /**
     * Returns index of an element.
     *
     * @param o The object.
     * @return The index of o.
     */
    public int indexOf(String o) {
        int start = 0;
        int max = this.size() - 1;
        while (start <= max) {
            int mid = (start + max) / 2;
            if (o.compareToIgnoreCase(this.get(mid)) < 0) {
                max = mid - 1;
            } else if (o.compareToIgnoreCase(this.get(mid)) > 0) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }


    /**
     * Merges two SortedWordLists together.
     *
     * @param lol SortedWordList to merge.
     */
    public void merge(LoginList lol) {
        for (int i = 0; i < lol.size(); i++) {
            this.add(lol.get(i));
        }
    }


    /**
     * Sets the value of a word.
     *
     * @param i    Index.
     * @param word Word to set.
     * @return The old value.
     */
    public String set(int i, String word) {
        int start = i - 1;
        if (start < 0) {
            start = 0;
        }

        int end = i + 1;
        if (end >= this.size()) {
            end = this.size() - 1;
        }

        if (this.get(start).compareTo(word) >= 0
                || this.get(end).compareTo(word) <= 0) {
            throw new IllegalArgumentException("word =" + word + " i =" + i);
        }
        String ph = this.get(i);
        super.set(i, word);
        return ph;
    }


    /**
     * Adds a word to the list.
     *
     * @param i    Index.
     * @param word Word to add.
     */
    public void add(int i, String word) {
        int start = i - 1;
        if (start < 0) {
            start = 0;
        }

        if (i == this.size()) {
            if (this.get(start).compareTo(word) >= 0) {
                throw new IllegalArgumentException(
                        "word =" + word + " i =" + i);
            }
        } else if (start != 0) {
            if (this.get(start).compareTo(word) >= 0
                    || this.get(i).compareTo(word) <= 0) {
                throw new IllegalArgumentException(
                        "word =" + word + " i =" + i);
            }
        } else {
            if (this.get(i).compareTo(word) <= 0) {
                throw new IllegalArgumentException(
                        "word =" + word + " i =" + i);
            }
        }
        super.add(i, word);
    }


    /**
     * Adds a word.
     *
     * @param word Word to add.
     * @return Whether it added it or not.
     */
    public boolean add(String word) {
        if (this.contains(word)) {
            return false;
        }
        super.add(word);
        this.sort(null);
        return true;
    }


    public String toString() {
        String out = "";
        for (int i = 0; i < this.size(); i++) {
            out += "Username: " + getUsername(i) + "\t" + "Password: " + getPassword(i) + "\n";
        }
        return out;
    }

}
