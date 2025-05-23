package tensor;

// Vector.java
public interface Vector extends Cloneable {
    Scalar getVectorElement(int index);
    void setVectorElement(int index, Scalar value);

    int getVectorSize();

    Vector clone();

    void add(Vector other);       // non-static
    void multiply(Scalar scalar); // non-static

    Matrix toColumnMatrix();
    Matrix toRowMatrix();
}
