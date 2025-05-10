package tensor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Tensor {

    //no.24 전달받은 두 스칼라의 덧셈
    public static Scalar add(Scalar a, Scalar b) {
        Scalar newScalar = a.clone();
        newScalar.add(b);
        return newScalar;
    }

    //no.25 전달받은 두 스칼라의 곱셈
    public static Scalar multiply(Scalar a, Scalar b) {
        Scalar newScalar = a.clone();
        newScalar.multiply(b);
        return newScalar;
    }

    //no.26 전달받은 두 벡터의 덧셈
    public static Vector add(Vector a, Vector b) {
        if (a.getSize() != b.getSize()) {
            throw new TensorSizeMismatchException("Vectors have different sizes");
        }
        Vector newVector = a.clone();
        newVector.add(b);
        return newVector;
    }
    //no.27 전달받은 스칼라와 벡터의 곱셈
    public static Vector multiply(Scalar scalar, Vector vector) {
        Vector newVector = vector.clone();
        newVector.multiply(scalar);
        return newVector;
    }

    //no.28 전달받은 두 행렬의 덧셈
    public static Matrix add(Matrix a, Matrix b) {
        if(a.getRowCount() != b.getRowCount() || a.getColumnCount() != b.getColumnCount()){
            throw new TensorInvalidInputException("invalid matrix size");
        }
        Matrix newMatrix = a.clone();
        for(int i = 0; i < newMatrix.getRowCount(); ++i){
            for(int j = 0; j < newMatrix.getColumnCount(); ++j){
                newMatrix.getElement(i,j).add(b.getElement(i,j));
            }
        }
        return newMatrix;
    }

    //no.29 전달받은 두 행렬의 곱셈
    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getColumnCount() != b.getRowCount()){
            throw new TensorInvalidInputException("invalid matrix multiply size"
                    + "(" + a.getRowCount() + "x" + a.getColumnCount() + ")x(" + b.getRowCount() + "x" + b.getColumnCount() + ")");
        }
        Matrix newMatrix = a.clone();
        newMatrix.multiplyRight(b);
        return newMatrix;
    }

    //no.32 전달받은 두 행렬의 가로 합(concat columns)
    public static Matrix concatColumns(Matrix a, Matrix b) {
        Matrix concated = a.clone();
        concated.concatColumns(b);
        return concated;
    }

    //no.33 전달받은 두 행렬의 세로 합(concat rows)
    public static Matrix concatRows(Matrix a, Matrix b) {
        Matrix concated = a.clone();
        concated.concatRows(b);
        return concated;
    }


}
