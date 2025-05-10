package tensor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Tensor {

    //no.24 전달받은 두 스칼라의 덧셈
    public static Scalar add(Scalar a, Scalar b) {
        return ScalarImpl.add(a, b);
    }

    //no.25 전달받은 두 스칼라의 곱셈
    public static Scalar multiply(Scalar a, Scalar b) {
        return ScalarImpl.multiply(a, b);
    }

    //no.26 전달받은 두 벡터의 덧셈
    public static Vector add(Vector a, Vector b) {
        return VectorImpl.add(a, b);
    }
    //no.27 전달받은 스칼라와 벡터의 곱셈
    public static Vector multiply(Scalar scalar, Vector vector) {
        return VectorImpl.multiply(scalar, vector);
    }

    //no.28 전달받은 두 행렬의 덧셈
    public static Matrix add(Matrix a, Matrix b) {
        return MatrixImpl.add(a, b);
    }

    //no.29 전달받은 두 행렬의 곱셈
    public static Matrix multiply(Matrix a, Matrix b) {
        return MatrixImpl.multiply(a, b);
    }

    //no.32 전달받은 두 행렬의 가로 합(concat columns)
    public static Matrix concatColumns(Matrix a, Matrix b) {
        return MatrixImpl.concatColumns(a, b);
    }

    //no.33 전달받은 두 행렬의 세로 합(concat rows)
    public static Matrix concatRows(Matrix a, Matrix b) {
        return MatrixImpl.concatRows(a, b);
    }

}
