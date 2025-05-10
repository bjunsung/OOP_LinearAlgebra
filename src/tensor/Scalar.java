package tensor;

import java.math.BigDecimal;

public interface Scalar extends Comparable<Scalar>, Cloneable {
    static Scalar add(Scalar a, Scalar b){
        ScalarImpl scalar = (ScalarImpl) a.clone();
        scalar.add(b);
        return scalar;
    }

    static Scalar multiply(Scalar a, Scalar b)
    {
        ScalarImpl scalar = (ScalarImpl) a.clone();
        scalar.multiply(b);
        return scalar;
    }

    String getValue();
    void setValue(String value);
    int compareTo(Scalar other);
    Scalar clone();
    void add(Scalar other);
    void add(BigDecimal other);
    void multiply(Scalar other);

}
