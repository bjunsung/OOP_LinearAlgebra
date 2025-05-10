package tensor;

public class TensorInvalidIndexException extends RuntimeException {
  public TensorInvalidIndexException(String message) {
    super(message);
  }
}
