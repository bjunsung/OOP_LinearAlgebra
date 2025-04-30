package tensor;
import java.math.BigDecimal;
import java.util.Random;

public class Factory {

    // Scalar 생성
    public static Scalar createScalar(String value) {
        return new ScalarImpl(value);
    }

    // 랜덤 Scalar 생성
    public static Scalar createRandomScalar(String min, String max) {
        BigDecimal minVal = new BigDecimal(min);
        BigDecimal maxVal = new BigDecimal(max);
        BigDecimal randomVal = minVal.add(new BigDecimal(Math.random()).multiply(maxVal.subtract(minVal)));
        return new ScalarImpl(randomVal.toString());
    }

    // 벡터 생성 (하나의 값)
    public static Vector createVector(int dimension, String value) {
        return new VectorImpl(dimension, value);
    }

    // 벡터 생성 (랜덤 값)
    public static Vector createRandomVector(int dimension, String min, String max) {
        return new VectorImpl(dimension, min, max);
    }

    // 벡터 생성 (배열)
    public static Vector createVectorFromArray(String[] values) {
        return new VectorImpl(values);
    }

    // 행렬 생성 (하나의 값)
    public static Matrix createMatrix(int rows, int cols, String value) {
        return new MatrixImpl(rows, cols, value);
    }

    // 행렬 생성 (랜덤 값)
    public static Matrix createRandomMatrix(int rows, int cols, String min, String max) {
        return new MatrixImpl(rows, cols, min, max);
    }

    // 행렬 생성 (CSV 파일)
    public static Matrix createMatrixFromCsv(String filepath) {
        return new MatrixImpl(filepath);
    }

    // 행렬 생성 (배열)
    public static Matrix createMatrixFromArray(String[][] values) {
        return new MatrixImpl(values);
    }

    // 단위 행렬 생성
    public static Matrix createIdentityMatrix(int size) {
        return new MatrixImpl(size);
    }
}
