package tensor;

import java.math.BigDecimal;

public interface Scalar extends Comparable<Scalar>, Cloneable {
    String getValue();
    void setValue(String value);
    int compareTo(Scalar other);
    Scalar clone();
    void add(Scalar other);
    void add(BigDecimal other);
    void multiply(Scalar other);

}
