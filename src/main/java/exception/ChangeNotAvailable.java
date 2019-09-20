package exception;

public class ChangeNotAvailable extends Exception {
    public ChangeNotAvailable() {
        super("That Change is not available");
    }
}
