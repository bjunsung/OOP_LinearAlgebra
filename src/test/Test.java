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
        System.out.println(indent+"identity matrix of size 3 =");
        System.out.println(matrix.toString(indentForMatrix));
        System.out.println();

        //no.11v | 특정 위치의 요소를 지정/조회할 수 있다.
        System.out.println("no.11v | 특정 위치의 요소를 지정/조회할 수 있다. (벡터)");
        vector = Factory.createRandomVector(5, "-5.0", "5.0");
        System.out.println(indent+"String value of 2nd element of 5-size Random vector = " + vector.getElement(2).toString(true));
        System.out.println();

        //no.11m | 특정 위치의 요소를 지정/조회할 수 있다.
        System.out.println("no.11m | 특정 위치의 요소를 지정/조회할 수 있다. (행렬)");
        matrix = Factory.createRandomMatrix(2, 2, "-5.0", "5.0");
        System.out.println(indent+"String value of (0, 1) element of 2x2 random value matrix : " + matrix.getElement(0, 1).toString(true));
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
        System.out.println(indent + "print String value of 5-size random Vector : " + vector.toString(true));
        System.out.println();

        //no.14m | 객체를 콘솔에 출력할 수 있다. (행렬)
        System.out.println("no.14m | 객체를 콘솔에 출력할 수 있다. (행렬)");
        matrix = Factory.createRandomMatrix(3, 5, "0.0", "100.0");
        System.out.println(indent + "print 3x5 size random matrix");
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
        System.out.printf(indent+"(static method) add operation of two given scalars : scalar1(%.2f) + scalar2(%.2f) : %.2f\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar2.getValue()), Double.parseDouble(Tensor.add(scalar1, scalar2).getValue()));
        System.out.println();

        //no.25 | 전달받은 두 스칼라의 곱셈이 가능하다.
        System.out.println("no.25 | 전달받은 두 스칼라의 곱셈이 가능하다.");
        System.out.printf(indent+"(static method) multiply operation of two given scalars : scalar1(%.2f) x scalar2(%.2f) : %.2f\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar2.getValue()), Double.parseDouble(Tensor.multiply(scalar1, scalar2).getValue()));
        System.out.println();

        //no.26 | 전달받은 두 벡터의 덧셈이 가능하다.
        System.out.println("no.26 | 전달받은 두 벡터의 덧셈이 가능하다.");
        System.out.println(indent+"(static method) add operation of two given vectors : vector1 + vector2");
        System.out.print(indent+"vector1 \t\t  = ");
        System.out.println(vector1.toString(true));
        System.out.print(indent+"vector2 \t\t  = ");
        System.out.println(vector2.toString(true));
        System.out.print(indent+"vector1 + vector2 = ");
        System.out.println(Tensor.add(vector1, vector2).toString(true));
        System.out.println();

    }
}