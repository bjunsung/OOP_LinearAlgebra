package tensor;

// Vector.java
public interface Vector extends Cloneable {
    static Vector add(Vector a, Vector b){
        VectorImpl vector = (VectorImpl) a.clone();
        vector.add(b);
        return vector;
    }

    static Vector multiply(Scalar scalar, Vector vector){
        VectorImpl newVector = (VectorImpl) vector.clone();
        newVector.multiply(scalar);
        return newVector;
    }

    Scalar getElement(int index);
    void setElement(int index, Scalar value);

    int getSize();

    Vector clone();

    void add(Vector other);       // non-static
    void multiply(Scalar scalar); // non-static

    Matrix toColumnMatrix();
    Matrix toRowMatrix();

    String toString(boolean rounding);
}
