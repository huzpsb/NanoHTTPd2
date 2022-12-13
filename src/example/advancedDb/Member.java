package example.advancedDb;

import nano.http.d2.json.NanoJSON;

import java.io.Serializable;

public class Member implements Serializable {
    private static final long serialVersionUID = 43432443L;
    private final int id;
    private final String occupation;

    public Member(int id, String occupation) {
        this.id = id;
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return NanoJSON.asJSON(this, this.getClass());
    }
}
