package tensor;

public class TensorSizeMismatchException extends RuntimeException{
    public TensorSizeMismatchException(String message){
        super(message);
    }
}
