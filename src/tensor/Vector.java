package tensor;

// Vector.java
public interface Vector extends Cloneable {
    Scalar getElement(int index);
    void setElement(int index, Scalar value);

    int getSize();

    Vector clone();

    void add(Vector other);       // non-static
    void multiply(Scalar scalar); // non-static

    Matrix toColumnMatrix();
    Matrix toRowMatrix();
}
