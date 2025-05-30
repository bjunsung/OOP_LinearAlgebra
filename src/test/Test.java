package test;
import tensor.*;


public class Test {
    static String indent = "\t\t";
    public static void main(String[] args) {
        //no.01 값을 지정하여 스칼라를 생성할 수 있다.
        Scalar scalar1 = Factory.createScalar("3.14");
        System.out.println("no.01 | Scalar 값 생성 : " + scalar1);

        try {
            Scalar wrongScalar = Factory.createScalar("a");
        } catch (TensorInvalidInputException e) {
            System.out.println("예외처리 | 잘못된 Scalar 생성 입력 : " + e.getMessage());
        }

        //no.02
        Scalar scalar2 = Factory.createRandomScalar("-10.0", "10.0");
        System.out.println("no.02 | Random Scalar 생성 : " + scalar2);

        try {
            Scalar wrongRandomScalar = Factory.createRandomScalar("10.0", "-10.0");
        } catch (TensorInvalidInputException e) {
            System.out.println("예외처리 | 잘못된 Scalar 범위 : " + e.getMessage());
        }

        //no.03 | 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터를 생성 할 수 있다.
        Vector vector = Factory.createVector(5, "1");
        System.out.println("no.03 | 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터를 생성 할 수 있다 : " + vector);

        try {
            Vector wrongVector = Factory.createVector(-1, "1");
        } catch (TensorInvalidDimensionException e) {
            System.out.println("예외처리 | 음수 차원 벡터 생성 : " + e.getMessage());
        }

        //no.04 | i 이상 j 미만의 무작위 값을 요소로 하는 n-차원 벡터를 생성할 수 있다.
        vector = Factory.createRandomVector(5, "-5.0", "5.0");
        System.out.println("no.04 | Random Vector 생성 : " + vector);

        //no.05 | 1차원 배열로부터 n-차원 벡터를 생성할 수 있다.
        String[] array = {"1.0", "2.0", "3.0", "4.0", "5.0"};
        vector = Factory.createVectorFromArray(array);
        System.out.println("no.05 | 배열 기반 Vector 생성 : " + vector);

        //no.06 | 지정한 하나의 값을 모든 요소의 값으로 하는 mxn 행렬을 생성할 수 있다.
        Matrix matrix = Factory.createMatrix(3, 5, "1.0");
        System.out.println("no.06 | 지정한 하나의 값을 모든 요소의 값으로 채워진 Matrix 생성 :\n"+matrix);

        //no.07 | i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬을 생성할 수 있다.
        matrix = Factory.createRandomMatrix(3, 5, "-5.0", "5.0");
        System.out.println("no.07 | Random Matrix 생성 (-5.0 ~ 5.0) :\n" + matrix);

        //no.08 | csv 파일로부터 m x n 행렬을 생성할 수 있다.
        System.out.println("no.08 | csv 파일로부터 m x n 행렬을 생성할 수 있다.");
        String filepath = "src/test/3by3matrix.csv";
        try {
            System.out.print("1. matrix from csv file(" + filepath + ")\n");
            matrix = Factory.createMatrixFromCsv(filepath);
            System.out.println(matrix);
        } catch (TensorInvalidInputException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        try {
            filepath = "src/test/3by3matrix_InvalidValue.csv";
            System.out.println("2. matrix from csv file(" + filepath + ")");
            matrix = Factory.createMatrixFromCsv(filepath);
            System.out.println(matrix);
        } catch (TensorInvalidInputException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        try {
            filepath = "src/test/3by3matrix_InvalidRange.csv";
            System.out.println("3. matrix from csv file(" + filepath + ")");
            matrix = Factory.createMatrixFromCsv(filepath);
            System.out.println(matrix);
        } catch (TensorInvalidInputException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        //no.09 | 2차원 배열로부터 mxn 행렬을 생성할 수 있다.
        String[][] array2d = {{"1.0", "2.0"}, {"3.0", "4.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println("no.09 | 배열 기반 Matrix 생성 :\n" + matrix);

        try {
            String[][] irregularArray = {{"1", "2"}, {"3"}};
            Factory.createMatrixFromArray(irregularArray);
        } catch (TensorInvalidInputException e) {
            System.out.println("예외처리 | ireegular Array : " + e.getMessage());
        }

        //no.10 | 단위행렬을 생성할 수 있다.
        matrix = Factory.createIdentityMatrix(3);
        System.out.println("no.10 | 단위행렬 생성 :\n" + matrix);

        try {
            Matrix wrongMatrix = Factory.createIdentityMatrix(-1);
        } catch (TensorInvalidDimensionException e) {
            System.out.println("예외처리 | 음수 차원 단위행렬 : " + e.getMessage());
        }

        //no.11v | 특정 위치의 요소를 지정/조회할 수 있다.
        String[] input11v = {"1", "2", "3", "4", "5"};
        String[] result11v = {"1", "2", "3", "4", "1"};
        vector = Factory.createVectorFromArray(input11v);
        vector.setVectorElement(4, Factory.createScalar("1"));
        System.out.println("no.11v | 요소 변경 후 Vector 전체 비교 : " + vector.equals(Factory.createVectorFromArray(result11v)));
        System.out.println("no.11v | 특정 요소 값 확인 (index 1) : " + vector.getVectorElement(1).equals(Factory.createScalar("2")));

        try {
            System.out.println(vector.getVectorElement(5));
        } catch (TensorInvalidIndexException e) {
            System.out.println("예외처리 | 벡터 인덱스 범위 초과 접근 : " + e.getMessage());
        }

        //no.11m | 특정 위치의 요소를 지정/조회할 수 있다.
        String[][] input11m = {{"1", "2"}, {"3", "4"}};
        String[][] result11m = {{"1", "2"}, {"3", "1"}};
        matrix = Factory.createMatrixFromArray(input11m);
        matrix.setMatrixElement(1, 1, Factory.createScalar("1"));
        System.out.println("no.11m | 요소 변경 후 Matrix 전체 비교 : " + matrix.equals(Factory.createMatrixFromArray(result11m)));
        System.out.println("no.11m | 특정 요소 값 확인 index (0, 1) : " + matrix.getMatrixElement(0, 1).equals(Factory.createScalar("3")));

        //no.12 | Scalar 값을 지정/조회 할 수 있다.
        Scalar scalar = Factory.createScalar("10.0");
        scalar.setValue("5");
        System.out.println("no.12 | Scalar 값 변경 후 확인 : " + scalar.equals(Factory.createScalar("5")));
        System.out.println("no.12 | Scalar 값 출력 : " + scalar.getValue());

        //no.13v | 벡터의 크기 정보를 조회할 수 있다.
        vector = Factory.createVector(5, "1.0");
        System.out.println("no.13v | Vector 크기 확인 : " + (vector.getVectorSize() == 5));

        //no.13m | 행렬의 크기 정보를 조회할 수 있다.
        matrix = Factory.createMatrix(2, 3, "1.0");
        System.out.println("no.13m | Matrix row/column size 확인 : " + (matrix.getMatrixRowCount() == 2 && matrix.getMatrixColumnCount() == 3));

        //no.14s | 객체를 콘솔에 출력할 수 있다. (스칼라)
        scalar = Factory.createScalar("-5.0");
        System.out.println("no.14s | Scalar toString 오버라이딩 출력 : " + scalar);

        //no.14v | 객체를 콘솔에 출력할 수 있다. (벡터)
        vector = Factory.createVector(5, "-5.0");
        System.out.println("no.14v | Vector toString 오버라이딩 출력 : " + vector);

        //no.14m | 객체를 콘솔에 출력할 수 있다. (행렬)
        matrix = Factory.createMatrix(3, 5, "0.0");
        System.out.println("no.14m | Matrix toString 오버라이딩 출력 : \n" + matrix);

        //no.15s | 객체의 동등성 판단을 할 수 있다. (스칼라)
        scalar1 = Factory.createScalar("1");
        scalar2 = Factory.createScalar("1");
        Scalar scalar3 = Factory.createScalar("2");
        System.out.println("no.15s | Scalar 동일성 비교 - " + scalar1.equals(scalar2));
        System.out.println("no.15s | Scalar 동일성 비교 - " + scalar1.equals(scalar3));

        //no.15v | 객체의 동등성 판단을 할 수 있다. (벡터)
        Vector vector1 = Factory.createVector(5, "1.0");
        Vector vector2 = Factory.createVector(5, "1.0");
        Vector vector3 = Factory.createVector(6, "1.0");
        System.out.println("no.15v | Vector 동일성 비교 - " + vector1.equals(vector2));
        System.out.println("no.15v | Vector 동일성 비교 - " + vector1.equals(vector3));

        //no.15m | 객체의 동등성 판단을 할 수 있다. (행렬)
        Matrix matrix1 = Factory.createMatrix(2, 2, "1.0");
        Matrix matrix2 = Factory.createMatrix(2, 2, "1.0");
        Matrix matrix3 = Factory.createMatrix(2, 2, "-1.0");
        System.out.println("no.15m | Matrix 동일성 비교 - " + matrix1.equals(matrix2));
        System.out.println("no.15m | Matrix 동일성 비교 - " + matrix1.equals(matrix3));

        //no.16 | 값의 대소 비교를 할 수 있다.
        scalar1 = Factory.createScalar("1");
        scalar3 = Factory.createScalar("2");
        System.out.println("no.16 | Scalar 크기 비교 - : " + (scalar1.compareTo(scalar3) < 0));

        //no.17s | 객체 복제를 할 수 있다. (스칼라)
        scalar1 = Factory.createScalar("99");
        scalar2 = scalar1.clone();
        System.out.println("no.17 | Scalar 복제 비교 : " + scalar1.equals(scalar2));
        System.out.println("no.17 | Scalar 복제 비교 : " + (scalar1 == scalar2));

        //no.17v | 객체 복제를 할 수 있다. (벡터)
        vector1 = Factory.createVector(5, "2.0");
        vector2 = vector1.clone();
        System.out.println("no.17v | Vector 복제 비교 : " + vector1.equals(vector2));
        System.out.println("no.17v | Vector 복제 비교 : " + (vector1 == vector2));

        //no.17m | 객체 복제를 할 수 있다. (행렬)
        matrix1 = Factory.createMatrix(2, 2, "3.0");
        matrix2 = matrix1.clone();
        System.out.println("no.17m | Matrix 복제 비교 : " + matrix1.equals(matrix2));
        System.out.println("no.17m | Matrix 복제 비교 : " + (matrix1 == matrix2));


        //no.18 | 스칼라는 다른 스칼라와 덧셈이 가능하다.
        scalar1 = Factory.createScalar("1");
        scalar2 = Factory.createScalar("2");
        scalar1.add(scalar2);
        System.out.println("no.18 | Scalar 덧셈 결과 확인 (1 + 2 = 3) : " + scalar1.equals(Factory.createScalar("3")));

        //no.19 | 스칼라는 다른 스칼라와 곱셈이 가능하다.
        scalar1 = Factory.createScalar("3");
        scalar2 = Factory.createScalar("2");
        scalar1.multiply(scalar2);
        System.out.println("no.19 | Scalar 곱셈 결과 확인 (3 * 2 = 6) : " + scalar1.equals(Factory.createScalar("6")));

        //no.20 | 벡터는 다른 벡터와 덧셈이 가능하다.
        vector1 = Factory.createVector(5, "-1.0");
        vector2 = Factory.createVector(5, "1.0");
        vector1.add(vector2);
        System.out.println("no.20 | Vector 덧셈 결과 확인 (-1 + 1 = 0) : " + vector1.equals(Factory.createVector(5, "0.0")));

        //no.21 | 벡터는 다른 스칼라와 곱셈이 가능하다.
        Scalar weight = Factory.createScalar("-1.0");
        vector = Factory.createVector(5, "10.0");
        vector.multiply(weight);
        System.out.println("no.21 | Vector * Scalar 곱셈 결과 확인 (10 * -1 = -10) : " + vector.equals(Factory.createVector(5, "-10.000")));

        //no.22 | 행렬은 크기가 같은 다른 행렬과 덧셈이 가능하다.
        matrix1 = Factory.createMatrix(2, 2, "-1.0");
        matrix2 = Factory.createIdentityMatrix(2);
        matrix1.add(matrix2);
        System.out.println("no.22 | Matrix 덧셈 결과 확인 (2x2) (-1 + I) = [[0 -1] [-1 0]] : "
                + matrix1.equals(Factory.createMatrixFromArray(new String[][]{{"0.0", "-1.0"}, {"-1.0", "0.0"}})));

        try {
            Matrix m1 = Factory.createMatrix(2, 3, "1.0");
            Matrix m2 = Factory.createMatrix(3, 2, "1.0");
            m1.add(m2);
        } catch (TensorSizeMismatchException e) {
            System.out.println("예외처리 | 행렬 덧셈 크기 불일치 : " + e.getMessage());
        }

        //no.23 | 행렬은 다른 행렬과 곱셈이 가능하다.
        matrix1 = Factory.createMatrix(2, 3, "1.0");
        matrix2 = Factory.createMatrix(3, 2, "1.0");
        matrix1.multiplyRight(matrix2);
        System.out.println("no.23 | Matrix 곱셈 결과 확인 [[3 3] [3 3]] : "
                + matrix1.equals(Factory.createMatrix(2, 2, "3")));


        try {
            Matrix m1 = Factory.createMatrix(2, 3, "1.0");
            Matrix m2 = Factory.createMatrix(4, 2, "1.0");
            m1.multiply(m2);
        } catch (MatrixMulMismatchException e) {
            System.out.println("예외처리 | 행렬 곱셈 크기 불일치 : " + e.getMessage());
        }


        //no.24 | 전달받은 두 스칼라의 덧셈이 가능하다.
        scalar1 = Factory.createScalar("3");
        scalar2 = Factory.createScalar("7");
        System.out.println("no.24 | static Scalar 덧셈 결과 (3 + 7 = 10) : " + Tensors.add(scalar1, scalar2).equals(Factory.createScalar("10")));

        //no.25 | 전달받은 두 스칼라의 곱셈이 가능하다.
        System.out.println("no.25 | static Scalar 곱셈 결과 (3 * 7 = 21) : " + Tensors.multiply(scalar1, scalar2).equals(Factory.createScalar("21")));

        //no.26 | 전달받은 두 벡터의 덧셈이 가능하다.
        vector1 = Factory.createVector(3, "1.0");
        vector2 = Factory.createVector(3, "2.0");
        System.out.println("no.26 | static Vector 덧셈 결과 ((1 1 1) + (2 2 2) = (3 3 3)) : " + Tensors.add(vector1, vector2).equals(Factory.createVector(3, "3.0")));

        //no.27 | 전달받은 두 스칼라와 벡터의 곱셈이 가능하다.
        vector3 = Factory.createVector(3, "1.0");
        scalar = Factory.createScalar("2.0");
        System.out.println("no.27 | static Vector*Scalar 곱셈 결과 ((1 1 1) * 2 = (2 2 2)) : " + Tensors.multiply(scalar, vector3).equals(Factory.createVector(3, "2.0")));

        //no.28 | 전달받은 같은 크기의 두 행렬 덧셈이 가능하다.
        matrix1 = Factory.createMatrix(2, 2, "1.0");
        matrix2 = Factory.createIdentityMatrix(2);
        System.out.println("no.28 | static Matrix 덧셈 결과 : " + Tensors.add(matrix1, matrix2).equals(Factory.createMatrixFromArray(new String[][]{{"2.0", "1.0"}, {"1.0", "2.0"}})));

        //no.29 | 전달받은 두 행렬의 곱셈이 가능하다.
        String[][] array1 = {{"1.0", "2.0", "3.0"}, {"-1.0", "-2.0", "-3.0"}};
        String[][] array2 = {{"1.0", "1.0"}, {"1.0", "1.0"}, {"1.0", "1.0"}};
        matrix1 = Factory.createMatrixFromArray(array1);
        matrix2 = Factory.createMatrixFromArray(array2);
        System.out.println("no.29 | static Matrix 곱셈 결과 (2x3 * 3x2) 값 확인 : " + Tensors.multiply(matrix1, matrix2).equals(Factory.createMatrixFromArray(new String[][]{{"6.0", "6.0"}, {"-6.0", "-6.0"}})));

        //no.30 | n-차원 벡터 객체는 자신으로부터 nx1 행렬을 생성하여 반환할 수 있다.
        String[] vectorArray = {"1.0", "2.0", "3.0"};
        vector = Factory.createVectorFromArray(vectorArray);
        matrix1 = vector.toColumnMatrix();
        System.out.println("no.30 | Vector -> Column Matrix 변환 확인 : " + matrix1.equals(Factory.createMatrixFromArray(new String[][]{{"1.0"}, {"2.0"}, {"3.0"}})));

        //no.31 | n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.
        matrix2 = vector.toRowMatrix();
        System.out.println("no.31 | Vector -> Row Matrix 변환 확인 : " + matrix2.equals(Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "3.0"}})));

        //no.32 | 행렬은 다른 행렬과 가로로 합쳐질 수 있다.
        matrix1 = Factory.createMatrix(2, 2, "1.0");
        matrix2 = Factory.createMatrix(2, 1, "2.0");
        System.out.println("no.32 | Matrix 가로 연결 결과 확인 : " + Tensors.concatColumns(matrix1, matrix2).equals(
                Factory.createMatrixFromArray(new String[][]{{"1.0", "1.0", "2.0"}, {"1.0", "1.0", "2.0"}})));

        //no.33 | 행렬은 다른 행렬과 세로로 합쳐질 수 있다.
        matrix1 = Factory.createMatrix(1, 2, "1.0");
        matrix2 = Factory.createMatrix(1, 2, "2.0");
        System.out.println("no.33 | Matrix 세로 연결 결과 확인 : " + Tensors.concatRows(matrix1, matrix2).equals(
                Factory.createMatrixFromArray(new String[][]{{"1.0", "1.0"}, {"2.0", "2.0"}})));

        //no.34 | 행렬은 특정 행을 벡터 형태로 추출해 줄 수 있다.
        array2d = new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        vector = matrix.extractRow(1);
        System.out.println("no.34 | 특정 행 추출 결과 확인 : " + vector.equals(Factory.createVectorFromArray(new String[]{"3.0", "4.0"})));

        //no.35 | 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다.
        vector = matrix.extractColumn(0);
        System.out.println("no.35 | 특정 열 추출 결과 확인 : " + vector.equals(Factory.createVectorFromArray(new String[]{"1.0", "3.0"})));

        //no.36 | 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        Matrix sub = matrix.subMatrix(0, 0, 1, 1);
        System.out.println("no.36 | 부분 행렬 추출 결과 확인 : " + sub.equals(Factory.createMatrixFromArray(new String[][]{{"2.0"}})));

        //no.37 | 행렬은 특정 범위의 minor 행렬을 추출 해 줄 수 있다.
        Matrix minor = matrix.minor(0, 0);
        System.out.println("no.37 | Minor 행렬 추출 결과 확인 : " + minor.equals(Factory.createMatrixFromArray(new String[][]{{"4.0"}})));

        //no.38 | 행렬은 전치행렬을 구해줄 수 있다.
        Matrix transpose = matrix.transpose();
        System.out.println("no.38 | 전치 행렬 결과 확인 : " + transpose.equals(Factory.createMatrixFromArray(new String[][]{{"1.0", "3.0"}, {"2.0", "4.0"}})));

        //no.39 | 행렬은 대각 요소의 합을 구해줄 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "0.0"}, {"0.0", "2.0"}});
        System.out.println("no.39 | 대각 합(trace) 확인 : " + matrix.trace().equals(Factory.createScalar("3.0")));

        //no.40 | 행렬은 자신이 정사각 행렬인지 여부를 판별해 줄 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        System.out.println("no.40 | 정사각 행렬 여부 확인 : " + matrix.isSquare());

        //no.41 | 행렬은 자신이 상삼각 행렬인지 여부를 판별해 줄 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"0.0", "3.0"}});
        System.out.println("no.41 | 상삼각 행렬 여부 확인 : " + matrix.isUpperTriangular());

        //no.42 | 행렬은 자신이 하삼각 행렬인지 여부를 판별해 줄 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "0.0"}, {"2.0", "3.0"}});
        System.out.println("no.42 | 하삼각 행렬 여부 확인 : " + matrix.isLowerTriangular());

        //no.43 | 행렬은 자신이 단위 행렬인지 여부를 판별해 줄 수 있다.
        matrix = Factory.createIdentityMatrix(2);
        System.out.println("no.43 | 단위 행렬 여부 확인 : " + matrix.isIdentityMatrix());

        //no.44 | 행렬은 자신이 영 행렬인지 여부를 판별해 줄 수 있다.
        matrix = Factory.createMatrix(2, 2, "0.0");
        System.out.println("no.44 | 영 행렬 여부 확인 : " + matrix.isZeroMatrix());

        //no.45 | 행렬은 특정 두 행의 위치를 맞교환할 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        matrix.swapRows(0, 1);
        System.out.println("no.45 | 행 교환 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"3.0", "4.0"}, {"1.0", "2.0"}})));

        //no.46 | 행렬은 특정 두 열의 위치를 맞교환할 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        matrix.swapColumns(0, 1);
        System.out.println("no.46 | 열 교환 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"2.0", "1.0"}, {"4.0", "3.0"}})));

        //no.47 | 행렬은 특정 행에 스칼라배 할 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        scalar = Factory.createScalar("-1.0");
        matrix.scaleRow(1, scalar);
        System.out.println("no.47 | 행 스칼라 배 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"-3.0", "-4.0"}})));

        //no.48 | 행렬은 특정 열에 스칼라배 할 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        matrix.scaleColumn(1, scalar);
        System.out.println("no.48 | 열 스칼라 배 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"1.0", "-2.0"}, {"3.0", "-4.0"}})));

        //no.49 | 행렬은 특정 행에 다른 행의 상수배를 더할 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        scalar = Factory.createScalar("-1.0");
        matrix.addScaledRow(0, 1, scalar);
        System.out.println("no.49 | 행 상수배 덧셈 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"-2.0", "-2.0"}, {"3.0", "4.0"}})));

        //no.50 | 행렬은 특정 열에 다른 열의 상수배를 더할 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        scalar = Factory.createScalar("-1.0");
        matrix.addScaledColumn(0, 1, scalar);
        System.out.println("no.50 | 열 상수배 덧셈 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"-1.0", "2.0"}, {"-1.0", "4.0"}})));

        //no.51 | 행렬은 자신으로부터 RREF 행렬을 구해서 반환해줄 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        Matrix rref = matrix.toRref();
        System.out.println("no.51 | RREF 변환 결과 확인 : " + rref.isRref());
        System.out.println(rref);

        //no.52 | 행렬은 자신이 RREF 행렬인지 여부를 판별해 줄 수 있다.
        System.out.println("no.52 | RREF 여부 확인 : " + rref.isRref());

        //no.53 | 행렬은 자신의 행렬식을 구해줄 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        System.out.println("no.53 | 행렬식 확인 (1*4 - 2*3 = -2) : " + matrix.determinant().equals(Factory.createScalar("-2.0")));

        //no.54  | 행렬은 자신의 역행렬을 구해줄 수 있다.
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}});
        Matrix expectedInv = Factory.createMatrixFromArray(new String[][]{{"-2", "1"}, {"1.5", "-0.5"}});

        Matrix inv = matrix.inverse();
        System.out.println("no.54 | 역행렬 계산 결과 확인 : " + inv.equals(expectedInv));

        try {
            matrix = Factory.createMatrixFromArray(new String[][]{{"3.0", "4.0", "7.0"}, {"3.0", "2.0", "6.0"}});
            Matrix unexpectedInv = matrix.inverse();
        } catch (MatrixNonSquareException | TensorArithmeticException e) {
            System.out.println("예외처리: " + e.getMessage());
        }

        try {
            matrix = Factory.createMatrix(5, 5, "0");
            Matrix unexpectedInv = matrix.inverse();
        } catch (MatrixNonSquareException | TensorArithmeticException e) {
            System.out.println("예외처리: " + e.getMessage());
        }
    }
}