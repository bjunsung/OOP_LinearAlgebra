package tensor;

public class TensorInvalidDimensionException extends RuntimeException {
    public TensorInvalidDimensionException(String message) {
        super(message);
    }
}
