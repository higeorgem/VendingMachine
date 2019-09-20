package exception;

public class InsufficientProductQuantity extends Exception{
    public InsufficientProductQuantity() {
        super("Product out of stock");
    }
}
