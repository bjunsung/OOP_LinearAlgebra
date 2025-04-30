package tensor;

public interface Scalar extends Comparable<Scalar>, Cloneable {
    String getValue();
    void setValue(String value);
    int compareTo(Scalar other);
    Scalar clone();
    boolean equals(Object obj);
    void add(Scalar other);
    void multiply(Scalar other);
}
