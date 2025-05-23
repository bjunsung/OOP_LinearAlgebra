// Matrix.java
package tensor;

public interface Matrix extends Cloneable {
    Scalar getMatrixElement(int row, int col);
    void setMatrixElement(int row, int col, Scalar value);

    int getMatrixRowCount();
    int getMatrixColumnCount();

    void add(Matrix other);       // non-static
    void multiplyRight(Matrix other);  // non-static
    void multiply(Matrix other);  // non-static

    Matrix clone();

    void concatColumns(Matrix other);
    void concatRows(Matrix other);

    Vector extractRow(int rowIndex);
    Vector extractColumn(int columnIndex);

    Matrix subMatrix(int startRow, int endRow, int startCol, int endCol);
    Matrix minor(int rowToExclude, int colToExclude);

    Matrix transpose();
    Scalar trace();

    boolean isSquare();
    boolean isUpperTriangular();
    boolean isLowerTriangular();
    boolean isIdentityMatrix();
    boolean isZeroMatrix();

    void swapRows(int row1, int row2);
    void swapColumns(int col1, int col2);

    void scaleRow(int row, Scalar scalar);
    void scaleColumn(int col, Scalar scalar);

    void addScaledRow(int targetRow, int sourceRow, Scalar scalar);
    void addScaledColumn(int targetCol, int sourceCol, Scalar scalar);

    Matrix toRref();
    boolean isRref();

    Scalar determinant();
    Matrix inverse();
}
