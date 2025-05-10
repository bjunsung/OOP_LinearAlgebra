package test;
import tensor.*;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;



public class Test {
    static String indentForMatrix = "\t\t\t\t\t\t\t";
    static String indent = "\t\t";
    public static void main(String[] args) {
        //no.01 | 값(String)을 지정하여 Scalar 생성할 수 있다.
        Scalar scalar1 = Factory.createScalar("3.14159265358979323846264338327950288419716939");

        //no.02 | -10.0 ~ 10.0 사이의 값을 가지는 Random Scalar 생성.
        Scalar scalar2 = Factory.createRandomScalar("-10.0", "10.0");
        System.out.println("no.01 | 값(String\"3.141592...\")을 지정하여 Scalar 생성할 수 있다.");
        System.out.printf(indent+"Given(3.141592...) scalar1 = %s\n", scalar1.toString(true));
        System.out.println();

        //no.02 | -10.0 ~ 10.0 사이의 값을 가지는 Random Scalar 생성.
        System.out.println("no.02 | -10.0 ~ 10.0 사이의 값을 가지는 Random Scalar 생성.");
        System.out.printf(indent+"Random scalar2 = %s\n", scalar2.toString(true));
        System.out.println();

        //no.03 | 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터를 생성 할 수 있다.
        Vector vector = Factory.createVector(5, "3.14159265358979323846264338327950288419716939");
        System.out.println("no.03 | 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터를 생성 할 수 있다.");
        System.out.print(indent+"Given(3.141592...) vector = ");
        System.out.println(vector.toString(true));
        System.out.println();

        //no.04 | i 이상 j 미만의 무작위 값을 요소로 하는 n-차원 벡터를 생성할 수 있다.
        vector = Factory.createRandomVector(5, "-5.0", "5.0");
        System.out.println("no.04 | i(-5.0) 이상 j(5.0) 미만의 무작위 값을 요소로 하는 n-차원 벡터를 생성할 수 있다.");
        System.out.print(indent+"Random vector = ");
        System.out.println(vector.toString(true));
        System.out.println();

        //no.05 | 1차원 배열로부터 n-차원 벡터를 생성할 수 있다.
        String[] array = {"1.0", "2.0", "3.0", "4.0", "5.0"};
        vector = Factory.createVectorFromArray(array);
        System.out.println("no.05 | 1차원 배열로부터 n-차원 벡터를 생성할 수 있다.");
        System.out.print(indent+"Given array({\"1.0\", \"2.0\", \"3.0\", \"4.0\", \"5.0\"}) to vector = ");
        System.out.println(vector.toString(true));
        System.out.println();

        //no.06 | 지정한 하나의 값을 모든 요소의 값으로 하는 mxn 행렬을 생성할 수 있다.
        Matrix matrix = Factory.createMatrix(3, 5, "0.01");
        System.out.println("no.06 | 지정한 하나의 값을 모든 요소의 값으로 하는 mxn 행렬을 생성할 수 있다.");
        System.out.println(indent+"Given(0.01) 3x5 matrix = ");
        System.out.println(matrix.toString(indentForMatrix));
        System.out.println();

        //no.07 | i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬을 생성할 수 있다.
        matrix = Factory.createRandomMatrix(3, 5, "-5.0", "5.0");
        System.out.println("no.07 | i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬을 생성할 수 있다.");
        System.out.println(indent+"Random(-5.0 ~ 5.0) 3x5 matrix = ");
        System.out.println(matrix.toString(indentForMatrix));
        System.out.println();

        //no.08 | csv 파일로부터 m x n 행렬을 생성할 수 있다.
        System.out.println("no.08 | csv 파일로부터 m x n 행렬을 생성할 수 있다.");
        String filepath = "\\C:\\Users\\baejunsung\\Desktop\\3by3matrix.csv\\";
        try{
            matrix = Factory.createMatrixFromCsv(filepath);
            System.out.println(indent+"matrix from csv file("+filepath+")");
            System.out.println(matrix.toString(indentForMatrix));
        }
        catch(TensorInvalidInputException e){}
        catch(IndexOutOfBoundsException e){System.out.println("\n"+e.getMessage());}
        catch(NumberFormatException e){System.out.println(e.getMessage());}
        System.out.println();

        //no.09 | 2차원 배열로부터 mxn 행렬을 생성할 수 있다.
        String[][] array2d = {{"1.0", "2.0"}, {"3.0", "4.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println("no.09 | 2차원 배열로부터 mxn 행렬을 생성할 수 있다.");
        System.out.println(indent+"Given 2 dimention array : {{\"1.0\", \"2.0\"}, {\"3.0\", \"4.0\"}}");
        System.out.println(matrix.toString(indentForMatrix));
        System.out.println();

        //no.10 | 단위행렬을 생성할 수 있다.
        System.out.println("no.10 | 단위행렬을 생성할 수 있다.");
        matrix = Factory.createIdentityMatrix(3);
        System.out.println(indent+"3x3 dimension identity matrix =");
        System.out.println(matrix.toString(indentForMatrix));
        System.out.println();

        //no.11v | 특정 위치의 요소를 지정/조회할 수 있다.
        System.out.println("no.11v | 특정 위치의 요소를 지정/조회할 수 있다. (벡터)");
        vector = Factory.createRandomVector(5, "-5.0", "5.0");
        System.out.println(indent+"String value of 2nd element of 5-dimension Random vector = " + vector.getElement(2).toString(true));
        System.out.println();

        //no.11m | 특정 위치의 요소를 지정/조회할 수 있다.
        System.out.println("no.11m | 특정 위치의 요소를 지정/조회할 수 있다. (행렬)");
        matrix = Factory.createRandomMatrix(2, 2, "-5.0", "5.0");
        System.out.println(indent+"String value of (0, 1) element of 2x2 dimension random value matrix : " + matrix.getElement(0, 1).toString(true));
        System.out.println();

        //no.12 | Scalar 값을 지정/조회 할 수 있다.
        System.out.println("no.12 | Scalar 값을 지정/조회 할 수 있다.");
        Scalar scalar = Factory.createScalar("10.0");
        System.out.printf(indent+"getValue of Scalar type, String value of scalar = %s\n", scalar.toString(true));
        System.out.println();

        //no.13v | 벡터의 크기 정보를 조회할 수 있다.
        System.out.println("no.13v | 벡터의 크기 정보를 조회할 수 있다.");
        vector = Factory.createRandomVector(5, "-5.0", "5.0");
        System.out.println(indent+"size of vector = " + vector.getSize());
        System.out.println();

        //no.13m | 행렬의 크기 정보를 조회할 수 있다.
        System.out.println("no.13m | 행렬의 크기 정보를 조회할 수 있다.");
        System.out.println(indent + "get row/column size of 2x2 matrix");
        System.out.println(indent + "row size : " + matrix.getRowCount());
        System.out.println(indent + "column size : " + matrix.getColumnCount());
        System.out.println();

        //no.14s | 객체를 콘솔에 출력할 수 있다. (스칼라)
        System.out.println("no.14s | 객체를 콘솔에 출력할 수 있다. (스칼라)");
        scalar = Factory.createRandomScalar("-5.0", "5.0");
        System.out.println(indent + "print String value of random scalar : " + scalar.toString(true));
        System.out.println();

        //no.14v | 객체를 콘솔에 출력할 수 있다. (벡터)
        System.out.println("no.14v | 객체를 콘솔에 출력할 수 있다. (벡터)");
        vector = Factory.createRandomVector(5, "-5.0", "5.0");
        System.out.println(indent + "print String value of 5-dimension random Vector : " + vector.toString(true));
        System.out.println();

        //no.14m | 객체를 콘솔에 출력할 수 있다. (행렬)
        System.out.println("no.14m | 객체를 콘솔에 출력할 수 있다. (행렬)");
        matrix = Factory.createRandomMatrix(3, 5, "0.0", "100.0");
        System.out.println(indent + "print 3x5 dimension random matrix");
        System.out.println(matrix.toString(indentForMatrix));
        System.out.println();

        //no.15s | 객체의 동등성 판단을 할 수 있다. (스칼라)
        System.out.println("no.15s | 객체의 동등성 판단을 할 수 있다. (스칼라)");
        System.out.printf(indent+"scalar1(%.2f) equal to scalar2(%.2f) : %b\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar2.getValue()), scalar1.equals(scalar2));
        Scalar scalar3 = Factory.createScalar(scalar1.getValue());
        System.out.printf(indent+"scalar3 = %.2f\n", Double.parseDouble(scalar3.getValue()));
        System.out.printf(indent+"-> scalar1(%.2f) equal to scalar3(%.2f) : %b\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar3.getValue()), scalar1.equals(scalar3));
        System.out.println();

        //no.15v | 객체의 동등성 판단을 할 수 있다. (벡터)
        System.out.println("no.15v | 객체의 동등성 판단을 할 수 있다. (벡터)");
        Vector vector1 = Factory.createVector(5, "1.0");
        Vector vector2 = Factory.createVector(5, "1.0");
        Vector vector3 = Factory.createVector(6, "1.0");
        System.out.println(indent + "vector1 (" + vector1.toString(true) + ") equal to\n" + indent + "vector2 (" + vector2.toString(true) + ") : " + vector1.equals(vector2));
        System.out.println(indent + "vector1 equal to\n" + indent + "vector3 (" + vector3.toString(true) + ") : " + vector1.equals(vector3));
        System.out.println();

        //no.15m | 객체의 동등성 판단을 할 수 있다. (행렬)
        System.out.println("no.15m | 객체의 동등성 판단을 할 수 있다. (행렬)");
        Matrix matrix1 = Factory.createMatrix(2, 2, "1.0");
        Matrix matrix2 = Factory.createMatrix(2, 2, "1.0");
        Matrix matrix3 = Factory.createMatrix(2, 2, "-1.0");
        System.out.println(indent + "matrix1 =");
        System.out.println(matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix2 =");
        System.out.println(matrix2.toString(indentForMatrix));
        System.out.println(indent + "matrix3 =");
        System.out.println(matrix3.toString(indentForMatrix));
        System.out.println(indent + "matrix1 equal to matrix2 : " + matrix1.equals(matrix2));
        System.out.println(indent + "matrix1 equal to matrix3 : " + matrix1.equals(matrix3));
        System.out.println();

        //no.16 | 값의 대소 비교를 할 수 있다.
        System.out.println("no.16 | 값의 대소 비교를 할 수 있다.");
        System.out.println(indent+"Is scalar1 bigger than scalar2?\n\t\t\tequal -> 0, scalar1 is bigger -> 1, scalar2 is bigger -> -1, result : "+ scalar1.compareTo(scalar2));
        System.out.println(indent+"Is scalar1 bigger than scalar3?\n\t\t\tequal -> 0, scalar1 is bigger -> 1, scalar2 is bigger -> -1, result : "+ scalar1.compareTo(scalar3));
        System.out.println();

        //no.17s | 객체 복제를 할 수 있다. (스칼라)
        System.out.println("no.17 | 객체 복제를 할 수 있다. (스칼라)");
        scalar1 = Factory.createScalar("99");
        scalar2 = scalar1.clone();
        System.out.println(indent + "scalar1 : " + scalar1.toString(true));
        System.out.println(indent + "clone scalar1 into scalar2");
        System.out.println(indent + "scalar2 : " + scalar2.toString(true));
        System.out.println();

        //no.17v | 객체 복제를 할 수 있다. (벡터)
        System.out.println("no.17v | 객체 복제를 할 수 있다. (벡터)");
        vector1 = Factory.createRandomVector(5, "0.0", "10.0");
        vector2 = vector1.clone();
        System.out.println(indent + "vector1 : " + vector1.toString(true));
        System.out.println(indent + "clone vector1 into vector2");
        System.out.println(indent + "vector2 : " + vector2.toString(true));
        System.out.println();

        //no.17m | 객체 복제를 할 수 있다. (행렬)
        System.out.println("no.17m | 객체 복제를 할 수 있다. (행렬)");
        matrix1 = Factory.createRandomMatrix(2, 2, "0.0", "10.0");
        matrix2 = matrix1.clone();
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "clone matrix1 into matrix2");
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println();


        //no.18 | 스칼라는 다른 스칼라와 덧셈이 가능하다.
        scalar1.add(scalar2);
        System.out.println("no.18 | 스칼라는 다른 스칼라와 덧셈이 가능하다.");
        System.out.printf(indent+"add scalar2 to scalar1, scalr1 value : %.2f\n", Double.parseDouble(scalar1.getValue()));
        System.out.println();

        //no.19 | 스칼라는 다른 스칼라와 곱셈이 가능하다.
        scalar1.multiply(scalar2);
        System.out.println("no.19 | 스칼라는 다른 스칼라와 곱셈이 가능하다.");
        System.out.printf(indent+"multiply scalar2 to scalar1, scalar1 value : %.2f\n", Double.parseDouble(scalar1.getValue()));
        System.out.println();

        //no.20 | 벡터는 다른 벡터와 덧셈이 가능하다.
        System.out.println("no.20 | 벡터는 다른 벡터와 덧셈이 가능하다.");
        vector1 = Factory.createRandomVector(5, "-5.0", "5.0");
        vector2 = Factory.createRandomVector(5, "-5.0", "5.0");
        vector1.add(vector2);
        System.out.print(indent+"add vector2 to vector1, vector1 = ");
        System.out.println(vector1.toString(true));
        System.out.println();

        //no.21 | 벡터는 다른 스칼라와 곱셈이 가능하다.
        System.out.println("no.21 | 벡터는 다른 스칼라와 곱셈이 가능하다.");
        Scalar weight = Factory.createScalar("-1.0");
        vector = Factory.createVector(5, "10.0");
        vector.multiply(weight);
        System.out.printf(indent+"multiply scalar(%.2f) to vector1, vector1 = ", Double.parseDouble(weight.getValue()));
        System.out.println(vector.toString(true));
        System.out.println();

        //no.22 | 행렬은 크기가 같은 다른 행렬과 덧셈이 가능하다.
        System.out.println("no.22 | 행렬은 크기가 같은 다른 행렬과 덧셈이 가능하다.");
        matrix1 = Factory.createMatrix(2, 2, "-1.0");
        matrix2 = Factory.createIdentityMatrix(2);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        matrix1.add(matrix2);
        System.out.println(indent + "add matrix2 to matrix1\n" + matrix1.toString(indentForMatrix));
        System.out.println();

        //no.23 | 행렬은 다른 행렬과 곱셈이 가능하다. (mxn)x(nxl)
        System.out.println("no.23 | 행렬은 다른 행렬과 곱셈이 가능하다. (mxn)x(nxl)");
        matrix1 = Factory.createRandomMatrix(2, 4, "-5.0", "5.0");
        matrix2 = Factory.createRandomMatrix(4, 2, "-5.0", "5.0");
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "multiply matrix2 to matrix1 (matrix1 x matrix2)");
        matrix1.multiplyRight(matrix2);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println();

        //no.24 | 전달받은 두 스칼라의 덧셈이 가능하다.
        System.out.println("no.24 | 전달받은 두 스칼라의 덧셈이 가능하다.");
        System.out.printf("(static method) add operation of two given scalars : scalar1(%.2f) + scalar2(%.2f) : %.2f\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar2.getValue()), Double.parseDouble(Tensor.add(scalar1, scalar2).getValue()));
        System.out.println();

        //no.25 | 전달받은 두 스칼라의 곱셈이 가능하다.
        System.out.println("no.25 | 전달받은 두 스칼라의 곱셈이 가능하다.");
        System.out.printf("(static method) multiply operation of two given scalars : scalar1(%.2f) x scalar2(%.2f) : %.2f\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar2.getValue()), Double.parseDouble(Tensor.multiply(scalar1, scalar2).getValue()));
        System.out.println();

        //no.26 | 전달받은 두 벡터의 덧셈이 가능하다.
        System.out.println("no.26 | 전달받은 두 벡터의 덧셈이 가능하다.");
        System.out.println("(static method) add operation of two given vectors : vector1 + vector2");
        vector1 = Factory.createVector(5, "1.0");
        vector2 = Factory.createVector(5, "-1.0");
        System.out.print(indent+"vector1 \t\t  = ");
        System.out.println(vector1.toString(true));
        System.out.print(indent+"vector2 \t\t  = ");
        System.out.println(vector2.toString(true));
        System.out.print(indent+"vector1 + vector2 = ");
        System.out.println(Tensor.add(vector1, vector2).toString(true));
        System.out.println();

        //no.27 | 전달받은 두 스칼라와 벡터의 곱셈이 가능하다.
        System.out.println("no.27 | 전달받은 두 스칼라와 벡터의 곱셈이 가능하다.");
        System.out.println("(static method) multiply operation of given scalar & vector : scalar x vector");
        vector = Factory.createVector(5, "1.0");
        scalar = Factory.createScalar("-1.0");
        System.out.println(indent + "vector = \t\t\t" + vector.toString(true));
        System.out.println(indent + "scalar = \t\t\t" + scalar.toString(true));
        System.out.println(indent + "multiply scalar to vector");
        System.out.println(indent + "scalar x vector = \t" + Tensor.multiply(scalar, vector).toString(true));
        System.out.println();


        //no.28 | 전달받은 같은 크기의 두 행렬 덧셈이 가능하다.
        System.out.println("no.28 | 전달받은 같은 크기의 두 행렬 덧셈이 가능하다.");
        System.out.println("(static method) add operation of two given equal dimension matrix : matrix1 + matrix2");
        matrix1 = Factory.createMatrix(2, 2, "1.0");
        matrix2 = Factory.createIdentityMatrix(2);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "add matrix2 to matrix1 (matrix1 + matrix2) =\n" + Tensor.add(matrix1, matrix2).toString(indentForMatrix));
        System.out.println();

        //no.29 | 전달받은 두 행렬의 곱셈이 가능하다. (mxn)x(nxl)
        System.out.println("no.29 | 전달받은 두 행렬의 곱셈이 가능하다. (mxn)x(nxl)");
        System.out.println("(static method) add operation of two given matrix : matrix1 x matrix2 (mxn)x(nxl)");
        String[][] array1 = {{"1.0", "2.0", "3.0"}, {"-1.0", "-2.0", "-3.0"}};
        String[][] array2 = {{"1.0", "1.2"}, {"1.4", "1.6"}, {"1.8", "2.0"}};
        matrix1 = Factory.createMatrixFromArray(array1);
        matrix2 = Factory.createMatrixFromArray(array2);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "multiply matrix2 to matrix1 (matrix1 x matrix2) =\n" + Tensor.multiply(matrix1, matrix2).toString(indentForMatrix));

        //no.30 | n-차원 벡터 객체는 자신으로부터 nx1 행렬을 생성하여 반환할 수 있다.
        System.out.println("no.30 | n-차원 벡터 객체는 자신으로부터 nx1 행렬을 생성하여 반환할 수 있다.");
        array = new String[]{"1.0", "2.0", "3.0", "4.0", "5.0"};
        vector = Factory.createVectorFromArray(array);
        System.out.println(indent + "vector = " + vector.toString(true));
        System.out.println(indent + "vector to nx1 matrix");
        matrix = vector.toColumnMatrix();
        System.out.println(matrix.toString(indentForMatrix));
        System.out.println();

        //no.31 | n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.
        System.out.println("no.31 | n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.");
        array = new String[]{"1.0", "2.0", "3.0", "4.0", "5.0"};
        vector = Factory.createVectorFromArray(array);
        System.out.println(indent + "vector = " + vector.toString(true));
        System.out.println(indent + "vector to 1xn matrix");
        matrix = vector.toRowMatrix();
        System.out.println(matrix.toString(indentForMatrix));
        System.out.println();

        //no.32 | 행렬은 다른 행렬과 가로로 합쳐질 수 있다.
        System.out.println("no.32 | 행렬은 다른 행렬과 가로로 합쳐질 수 있다.");
        System.out.println("(static method) concat operation of two given matrix : matrix1, matrix2 (kxm) concat (kxn)");
        matrix1 = Factory.createMatrix(3, 2, "1.0");
        matrix2 = Factory.createMatrix(3, 1, "2.0");
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "(concat-rows) matrix1, matrix2\n" + Tensor.concatColumns(matrix1, matrix2).toString(indentForMatrix));
        System.out.println();

        //no.33 | 행렬은 다른 행렬과 세로로 합쳐질 수 있다.
        System.out.println("no.33 | 행렬은 다른 행렬과 세로로 합쳐질 수 있다.");
        System.out.println("(static method) concat operation of two given matrix : matrix1, matrix2 (mxk) concat (nxk)");
        matrix1 = Factory.createMatrix(2, 3, "1.0");
        matrix2 = Factory.createMatrix(1, 3, "2.0");
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "(concat-columns) matrix1, matrix2\n" + Tensor.concatRows(matrix1, matrix2).toString(indentForMatrix));
        System.out.println();

        //no.34 | 행렬은 특정 행을 벡터 형태로 추출해 줄 수 있다.
        System.out.println("no.34 | 행렬은 특정 행을 벡터 형태로 추출해 줄 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        System.out.println(indent + "get 2nd row vector from matrix");
        vector = matrix.extractRow(1);
        System.out.println(indent + "vector = " + vector.toString(true));
        System.out.println();

        //no.35 | 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다.
        System.out.println("no.35 | 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        System.out.println(indent + "get 2nd column vector from matrix");
        vector = matrix.extractColumn(1);
        System.out.println(indent + "vector = " + vector.toString(true));
        System.out.println();

        //no.36 | 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
        System.out.println("no.36 | 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        System.out.println(indent + "get 2x1(The fartest bottom right of matrix) submatrix from matrix");
        Matrix submatrix = matrix.subMatrix(1, 2, 2, 2);
        System.out.println(indent + "submatrix =\n" + submatrix.toString(indentForMatrix));
        System.out.println();

        //no.37 | 행렬은 특정 범위의 minor 행렬을 추출 해 줄 수 있다.
        System.out.println("no.37 | 행렬은 특정 범위의 minor 행렬을 추출 해 줄 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        System.out.println(indent + "get minor(1,1) matrix from matrix");
        Matrix minorMatrix = matrix.minor(1, 1);
        System.out.println(indent + "minor matrix =\n" + minorMatrix.toString(indentForMatrix));
        System.out.println();

        //no.38 | 행렬은 전치행렬을 구해줄 수 있다.
        System.out.println("no.38 | 행렬은 전치행렬을 구해줄 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        Matrix transpose = matrix.transpose();
        System.out.println(indent + "transpose matrix =\n" + transpose.toString(indentForMatrix));
        System.out.println();

        //no.39 | 행렬은 대각 요소의 합을 구해줄 수 있다.
        System.out.println("no.39 | 행렬은 대각 요소의 합을 구해줄 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        System.out.println(indent + "trace(matrix) = " + matrix.trace());
        System.out.println();

        //no.40 | 행렬은 자신이 정사각 행렬인지 여부를 판별해 줄 수 있다.
        System.out.println("no.40 | 행렬은 자신이 정사각 행렬인지 여부를 판별해 줄 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}};
        String[][] array2dother = new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}};
        matrix1 = Factory.createMatrixFromArray(array2d);
        matrix2 = Factory.createMatrixFromArray(array2dother);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix1 is square matrix : " + matrix1.isSquare());
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "matrix2 is square matrix : " + matrix2.isSquare());
        System.out.println();

        //no.41 | 행렬은 자신이 상삼각 행렬인지 여부를 판별해 줄 수 있다.
        System.out.println("no.41 | 행렬은 자신이 상삼각 행렬인지 여부를 판별해 줄 수 있다.");
        array2d = new String[][]{{"1.11", "2.22", "3.33"}, {"0.0", "5.55", "6.66"}, {"0.0", "0.0", "9.99"}};
        array2dother = new String[][]{{"1.11", "0.0", "0.0"}, {"4.44", "5.55", "0.0"}, {"7.77", "8.88", "9.99"}};
        matrix1 = Factory.createMatrixFromArray(array2d);
        matrix2 = Factory.createMatrixFromArray(array2dother);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix1 is upper triangular matrix : " + matrix1.isUpperTriangular());
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "matrix2 is upper triangular matrix : " + matrix2.isUpperTriangular());
        System.out.println();

        //no.42 | 행렬은 자신이 하삼각 행렬인지 여부를 판별해 줄 수 있다.
        System.out.println("no.42 | 행렬은 자신이 하삼각 행렬인지 여부를 판별해 줄 수 있다.");
        array2d = new String[][]{{"1.11", "2.22", "3.33"}, {"0.0", "5.55", "6.66"}, {"0.0", "0.0", "9.99"}};
        array2dother = new String[][]{{"1.11", "0.0", "0.0"}, {"4.44", "5.55", "0.0"}, {"7.77", "8.88", "9.99"}};
        matrix1 = Factory.createMatrixFromArray(array2d);
        matrix2 = Factory.createMatrixFromArray(array2dother);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix1 is lower triangular matrix : " + matrix1.isLowerTriangular());
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "matrix2 is lower triangular matrix : " + matrix2.isLowerTriangular());
        System.out.println();

        //no.43 | 행렬은 자신이 단위 행렬인지 여부를 판별해 줄 수 있다.
        System.out.println("no.43 | 행렬은 자신이 단위 행렬인지 여부를 판별해 줄 수 있다.");
        array2d = new String[][]{{"1.0", "0.0", "0.0"}, {"0.0", "1.0", "0.0"}, {"0.0", "0.0", "1.0"}};
        array2dother = new String[][]{{"99.9", "0.0", "0.0"}, {"0.0", "1.0", "0.0"}, {"0.0", "0.0", "1.0"}};
        matrix1 = Factory.createMatrixFromArray(array2d);
        matrix2 = Factory.createMatrixFromArray(array2dother);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix1 is identity matrix : " + matrix1.isIdentityMatrix());
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "matrix2 is identity matrix : " + matrix2.isIdentityMatrix());
        System.out.println();

        //no.44 | 행렬은 자신이 영 행렬인지 여부를 판별해 줄 수 있다.
        System.out.println("no.44 | 행렬은 자신이 영 행렬인지 여부를 판별해 줄 수 있다.");
        array2d = new String[][]{{"0.0", "0.0", "0.0"}, {"0.0", "0.0", "0.0"}, {"0.0", "0.0", "0.0"}};
        array2dother = new String[][]{{"99.9", "0.0", "0.0"}, {"0.0", "0.0", "0.0"}, {"0.0", "0.0", "0.0"}};
        matrix1 = Factory.createMatrixFromArray(array2d);
        matrix2 = Factory.createMatrixFromArray(array2dother);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix1 is zero matrix : " + matrix1.isZeroMatrix());
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "matrix2 is zero matrix : " + matrix2.isZeroMatrix());
        System.out.println();

        //no.45 | 행렬은 특정 두 행의 위치를 맞교환할 수 있다.
        System.out.println("no.45 | 행렬은 특정 두 행의 위치를 맞교환할 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        System.out.println(indent + "swap 2nd row and 3rd row");
        matrix.swapRows(1, 2);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));

        //no.46 | 행렬은 특정 두 열의 위치를 맞교환할 수 있다.
        System.out.println("no.45 | 행렬은 특정 두 열의 위치를 맞교환할 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        System.out.println(indent + "swap 2nd column and 3rd column");
        matrix.swapColumns(1, 2);
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));

        //no.47 | 행렬은 특정 행에 스칼라배 할 수 있다.
        System.out.println("no.47 | 행렬은 특정 행에 스칼라배 할 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        scalar = Factory.createScalar("-1.0");
        System.out.println(indent + "value of scalar = " + scalar.toString());
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        matrix.scaleRow(1, scalar);
        System.out.println(indent + "scale(-1) on 2nd row of matrix =\n" + matrix.toString(indentForMatrix));

        //no.48 | 행렬은 특정 열에 스칼라배 할 수 있다.
        System.out.println("no.48 | 행렬은 특정 열에 스칼라배 할 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        scalar = Factory.createScalar("-1.0");
        System.out.println(indent + "value of scalar = " + scalar.toString());
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        matrix.scaleColumn(1, scalar);
        System.out.println(indent + "scale(-1) on 2nd column of matrix =\n" + matrix.toString(indentForMatrix));

        //no.49 | 행렬은 특정 행에 다른 행의 상수배를 더할 수 있다.
        System.out.println("no.49 | 행렬은 특정 행에 다른 행의 상수배를 더할 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        scalar = Factory.createScalar("-1.0");
        System.out.println(indent + "value of scalar = " + scalar.toString());
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        matrix.addScaledRow(0, 1, scalar);
        System.out.println(indent + "add scaled 2nd row to 1st row. R1 + (-1)R2");
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        System.out.println();

        //no.50 | 행렬은 특정 열에 다른 열의 상수배를 더할 수 있다.
        System.out.println("no.50 | 행렬은 특정 열에 다른 열의 상수배를 더할 수 있다.");
        array2d = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        scalar = Factory.createScalar("-1.0");
        System.out.println(indent + "value of scalar = " + scalar.toString());
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        matrix.addScaledColumn(0, 1, scalar);
        System.out.println(indent + "add scaled 2nd column to 1st column. C1 + (-1)C2");
        System.out.println(indent + "matrix =\n" + matrix.toString(indentForMatrix));
        System.out.println();

        //no.51 | 행렬은 자신으로부터 RREF 행렬을 구해서 반환해줄 수 있다.
        System.out.println("no.51 | 행렬은 자신으로부터 RREF 행렬을 구해서 반환해줄 수 있다.");
        array2d = new String[][]{{"2.0", "0.0", "1.0"}, {"3.0", "4.0", "5.0"}, {"1.0", "0.0", "1.0"}};
        array2dother = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix1 = Factory.createMatrixFromArray(array2d);
        matrix2 = Factory.createMatrixFromArray(array2dother);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        Matrix rref1 = matrix1.toRref();
        System.out.println(indent + "RREF(Reduced Row Echelon Form) of matrix1 =\n" + rref1.toString(indentForMatrix));
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        Matrix rref2 = matrix2.toRref();
        System.out.println(indent + "RREF(Reduced Row Echelon Form) of matrix2 =\n" + rref2.toString(indentForMatrix));
        System.out.println();

        //no.52 | 행렬은 자신이 RREF 행렬인지 여부를 판별해 줄 수 있다.
        System.out.println("no.52 | 행렬은 자신이 RREF 행렬인지 여부를 판별해 줄 수 있다.");
        array2d = new String[][]{{"1.0", "0.0", "0.0"}, {"0.0", "1.0", "1.0"}, {"0.0", "0.0", "0.0"}};
        array2dother =new String[][]{{"0.0", "1.0", "0.0"}, {"1.0", "0.0", "0.0"}, {"0.0", "0.0", "1.0"}};
        matrix1 = Factory.createMatrixFromArray(array2d);
        matrix2 = Factory.createMatrixFromArray(array2dother);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "matrix1 is RREF(Reduced Row Echelon Form) : " + matrix1.isRref());
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "matrix2 is RREF(Reduced Row Echelon Form) : " + matrix2.isRref());
        System.out.println();

        //no.53 | 행렬은 자신의 행렬식을 구해줄 수 있다.
        System.out.println("no.53 | 행렬은 자신의 행렬식을 구해줄 수 있다.");
        array2d = new String[][]{{"1.0", "2.0"}, {"3.0", "4.0"}};
        matrix1 = Factory.createMatrixFromArray(array2d);
        array2dother = new String[][]{{"1.0", "2.0", "3.0"}, {"4.0", "5.0", "6.0"}, {"7.0", "8.0", "9.0"}};
        matrix2 = Factory.createMatrixFromArray(array2dother);
        array2d = new String[][]{{"1.0", "2.0", "3.0", "4.0"}, {"2.0", "1.0", "5.0", "6.0"}, {"3.0", "0.0", "1.0", "7.0"}, {"4.0", "2.0", "6.0", "5.0"}};
        matrix3 = Factory.createMatrixFromArray(array2d);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        System.out.println(indent + "det(matrix1) = " + matrix1.determinant().toString(true));
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        System.out.println(indent + "det(matrix2) = " + matrix2.determinant().toString(true));
        System.out.println(indent + "matrix3 =\n" + matrix3.toString(indentForMatrix));
        System.out.println(indent + "det(matrix3) = " + matrix3.determinant().toString(true));
        System.out.println();

        //no.54  | 행렬은 자신의 역행렬을 구해줄 수 있다.
        System.out.println("no.54 | 행렬은 자신의 역행렬을 구해줄 수 있다.");
        array2d = new String[][]{{"2.0", "1.0", "3.0"}, {"1.0", "0.0", "1.0"}, {"0.0", "1.0", "2.0"}};
        matrix1 = Factory.createMatrixFromArray(array2d);
        array2dother = new String[][]{{"1.0", "1.0", "1.0"}, {"2.0", "2.0", "2.0"}, {"3.0", "4.0", "5.0"}};
        matrix2 = Factory.createMatrixFromArray(array2dother);
        System.out.println(indent + "matrix1 =\n" + matrix1.toString(indentForMatrix));
        try{
        System.out.println(indent + "inverse matrix of matrix1 =\n" + matrix1.inverse().toString(indentForMatrix));
        }catch(NonSquareMatrixException e){
            System.out.println(e.getMessage());
        }
        catch(TensorArithmeticException e){
            System.out.println(e.getMessage());
        }
        System.out.println(indent + "matrix2 =\n" + matrix2.toString(indentForMatrix));
        try{
            System.out.println(indent + "inverse matrix of matrix2 =\n" + matrix2.inverse().toString(indentForMatrix));
        }catch(NonSquareMatrixException e){
            System.out.println(e.getMessage());
        }
        catch(TensorArithmeticException e){
            System.out.println(e.getMessage());
        }

    }
}