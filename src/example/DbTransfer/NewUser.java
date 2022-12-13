package example.DbTransfer;

import java.io.Serializable;

// As you can see in this example, new class may:
// - have new fields
// - remove old fields
// - have a different package
// - have a different name
// And must:
// - have a different serialVersionUID

// Do not:
// Directly modify the old class
// Rename , change the package of the old class

public class NewUser implements Serializable {
    private static final long serialVersionUID = 43432325L;
    public String home;
    public String gender = "legacy";
}
