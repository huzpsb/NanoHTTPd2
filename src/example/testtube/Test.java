package example.testtube;

import example.advancedDb.Member;
import nano.http.d2.json.NanoJSON;

public class Test {
    public static void main(String[] args) {
        String[] qwq = {"aa", null, "bb"};
        System.out.println(NanoJSON.asJSON(new Result("Success", 0, qwq, new Member(33, "Monitor")), Result.class));
    }
}
