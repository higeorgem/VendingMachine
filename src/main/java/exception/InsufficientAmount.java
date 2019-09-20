package exception;

public class InsufficientAmount extends Exception {
    public InsufficientAmount() {
        super("Money is less than required amount");
    }
}
