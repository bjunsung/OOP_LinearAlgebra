package test;
import tensor.*;
import java.util.List;
import java.util.ArrayList;

class VectorAssistance{
    static void printVector(Vector vector){
        for (int i = 0; i < vector.getSize(); ++i){
            System.out.printf("%.2f", Double.parseDouble(vector.getElement(i).getValue()));
            if (i != vector.getSize() - 1) System.out.print(",  ");
        }
    }
    static void printVectorln(Vector vector){
        printVector(vector);
        System.out.println();
    }
}

class MatrixAssistance{
    static void printMatrix(Matrix matrix){
        printMatrix(matrix, "");
    }
    static void printMatrix(Matrix matrix, String indent){
        for(int i = 0; i < matrix.getRowCount(); ++i){
            System.out.print(indent);
            for (int j = 0; j < matrix.getColumnCount(); ++j) {
                System.out.printf("%7.2f", Double.parseDouble(matrix.getElement(i, j).getValue()));
            }
            if (i != matrix.getRowCount() - 1) System.out.println();
        }
    }
    static void printMatrixln(Matrix matrix){
        printMatrix(matrix, "");
        System.out.println();
    }
    static void printMatrixln(Matrix matrix, String indent){
        printMatrix(matrix, indent);
        System.out.println();
    }
}

public class Test {
    public static void main(String[] args) {
        //no.01 | 값(String)을 지정하여 Scalar 생성할 수 있다.
        Scalar scalar1 = Factory.createScalar("3.14159265358979323846264338327950288419716939");

        //no.02 | -10.0 ~ 10.0 사이의 값을 가지는 Random Scalar 생성.
        Scalar scalar2 = Factory.createRandomScalar("-10.0", "10.0");
        System.out.println("no.01 | 값(String\"3.141592...\")을 지정하여 Scalar 생성할 수 있다.");
        System.out.printf("\t\tGiven(3.141592...) scalar1 = %.2f\n", Double.parseDouble(scalar1.getValue()));
        System.out.println();

        //no.02 | -10.0 ~ 10.0 사이의 값을 가지는 Random Scalar 생성.
        System.out.println("no.02 | -10.0 ~ 10.0 사이의 값을 가지는 Random Scalar 생성.");
        System.out.printf("\t\tRandom scalar2 = %.2f\n", Double.parseDouble(scalar2.getValue()));
        System.out.println();

        //no.03 | 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터를 생성 할 수 있다.
        Vector vector = Factory.createVector(5, "3.14159265358979323846264338327950288419716939");
        System.out.println("no.03 | 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터를 생성 할 수 있다.");
        System.out.print("\t\tGiven(3.141592...) vector = ");
        VectorAssistance.printVectorln(vector);
        System.out.println();

        //no.04 | i 이상 j 미만의 무작위 값을 요소로 하는 n-차원 벡터를 생성할 수 있다.
        vector = Factory.createRandomVector(5, "-5.0", "5.0");
        System.out.println("no.04 | i(-5.0) 이상 j(5.0) 미만의 무작위 값을 요소로 하는 n-차원 벡터를 생성할 수 있다.");
        System.out.print("\t\tRandom vector = ");
        VectorAssistance.printVectorln(vector);
        System.out.println();

        //no.05 | 1차원 배열로부터 n-차원 벡터를 생성할 수 있다.
        String[] array = {"1.0", "2.0", "3.0", "4.0", "5.0"};
        Vector vector3 = Factory.createVectorFromArray(array);
        System.out.println("no.05 | 1차원 배열로부터 n-차원 벡터를 생성할 수 있다.");
        System.out.print("\t\tGiven array({\"1.0\", \"2.0\", \"3.0\", \"4.0\", \"5.0\"}) to vector = ");
        VectorAssistance.printVectorln(vector3);
        System.out.println();

        //no.06 | 지정한 하나의 값을 모든 요소의 값으로 하는 mxn 행렬을 생성할 수 있다.
        Matrix matrix = Factory.createMatrix(3, 5, "0.01");
        System.out.println("no.06 | 지정한 하나의 값을 모든 요소의 값으로 하는 mxn 행렬을 생성할 수 있다.");
        System.out.println("\t\tGiven(0.01) 3x5 matrix = ");
        String indent = "\t\t\t\t\t\t\t";
        MatrixAssistance.printMatrixln(matrix, indent);
        System.out.println();

        //no.07 | i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬을 생성할 수 있다.
        matrix = Factory.createRandomMatrix(3, 5, "-5.0", "5.0");
        System.out.println("no.07 | i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬을 생성할 수 있다.");
        System.out.println("\t\tRandom(-5.0 ~ 5.0) 3x5 matrix = ");
        indent = "\t\t\t\t\t\t\t";
        MatrixAssistance.printMatrixln(matrix, indent);
        System.out.println();

        //no.08 | csv 파일로부터 m x n 행렬을 생성할 수 있다.
        System.out.println("no.08 | csv 파일로부터 m x n 행렬을 생성할 수 있다.");
        String filepath = "\\C:\\Users\\baejunsungssu\\Desktop\\3by3matrix.csv\\";
        try{
            matrix = Factory.createMatrixFromCsv(filepath);
            System.out.println("matrix from csv file("+filepath+")");
            indent = "\t\t\t\t\t\t\t";
            MatrixAssistance.printMatrixln(matrix, indent);
        }
        catch(TensorInvalidInputException e){}
        catch(IndexOutOfBoundsException e){System.out.println("\n"+e.getMessage());}
        catch(NumberFormatException e){System.out.println(e.getMessage());}
        System.out.println();

        //no.11v | 특정 위치의 요소를 지정/조회할 수 있다.
        System.out.println("no.11v | 특정 위치의 요소를 지정/조회할 수 있다.");
        vector = Factory.createRandomVector(5, "-5.0", "5.0");
        System.out.println("\t\tString value of 2nd element of 5-size Random vector = " + vector.getElement(2).getValue());
        System.out.println();

        //no.12 | Scalar 값을 지정/조회 할 수 있다.
        System.out.println("no.12 | Scalar 값을 지정/조회 할 수 있다.");
        Scalar scalar = Factory.createRandomScalar("-10.0", "10.0");
        System.out.printf("\t\tgetValue of Scalar type, String value of scalar = %s\n", scalar.getValue());
        System.out.println();

        //no.13v | 벡터의 크기 정보를 조회할 수 있다.
        System.out.println("no.13v | 벡터의 크기 정보를 조회할 수 있다.");
        vector = Factory.createRandomVector(5, "-5.0", "5.0");
        System.out.println("\t\tsize of vector = " + vector.getSize());
        System.out.println();

        //no.15 | 객체의 동등성 판단을 할 수 있다.
        System.out.println("no.15 | 객체의 동등성 판단을 할 수 있다.");
        System.out.printf("\t\tscalar1(%.2f) equal to scalar2(%.2f) : %b\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar2.getValue()), scalar1.equals(scalar2));
        Scalar scalar3 = Factory.createScalar(scalar1.getValue());
        System.out.printf("\t\tscalar3 = %.2f\n", Double.parseDouble(scalar3.getValue()));
        System.out.printf("\t\t-> scalar1(%.2f) equal to scalar3(%.2f) : %b\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar3.getValue()), scalar1.equals(scalar3));
        System.out.println();

        //no.16 | 값의 대소 비교를 할 수 있다.
        System.out.println("no.16 | 값의 대소 비교를 할 수 있다.");
        System.out.println("\t\tIs scalar1 bigger than scalar2?\n\t\t\tequal -> 0, scalar1 is bigger -> 1, scalar2 is bigger -> -1, result : "+ scalar1.compareTo(scalar2));
        System.out.println("\t\tIs scalar1 bigger than scalar3?\n\t\t\tequal -> 0, scalar1 is bigger -> 1, scalar2 is bigger -> -1, result : "+ scalar1.compareTo(scalar3));
        System.out.println();

        //no.18 | 스칼라는 다른 스칼라와 덧셈이 가능하다.
        scalar1.add(scalar2);
        System.out.println("no.18 | 스칼라는 다른 스칼라와 덧셈이 가능하다.");
        System.out.printf("\t\tadd scalar2 to scalar1, scalr1 value : %.2f\n", Double.parseDouble(scalar1.getValue()));
        System.out.println();

        //no.19 | 스칼라는 다른 스칼라와 곱셈이 가능하다.
        scalar1.multiply(scalar2);
        System.out.println("no.19 | 스칼라는 다른 스칼라와 곱셈이 가능하다.");
        System.out.printf("\t\tmultiply scalar2 to scalar1, scalar1 value : %.2f\n", Double.parseDouble(scalar1.getValue()));
        System.out.println();

        //no.20 | 벡터는 다른 벡터와 덧셈이 가능하다.
        System.out.println("no.20 | 벡터는 다른 벡터와 덧셈이 가능하다.");
        Vector vector1 = Factory.createRandomVector(5, "-5.0", "5.0");
        Vector vector2 = Factory.createRandomVector(5, "-5.0", "5.0");
        vector1.add(vector2);
        System.out.print("\t\tadd vector2 to vector1, vector1 = ");
        VectorAssistance.printVectorln(vector1);
        System.out.println();

        //no.21 | 벡터는 다른 스칼라와 곱셈이 가능하다.
        System.out.println("no.21 | 벡터는 다른 스칼라와 곱셈이 가능하다.");
        Scalar weight = Factory.createScalar("-1.0");
        vector1.multiply(weight);
        System.out.printf("\t\tmultiply scalar(%.2f) to vector1, vector1 = ", Double.parseDouble(weight.getValue()));
        VectorAssistance.printVectorln(vector1);
        System.out.println();

        //no.24 | 전달받은 두 스칼라의 덧셈이 가능하다.
        System.out.println("no.24 | 전달받은 두 스칼라의 덧셈이 가능하다.");
        System.out.printf("\t\t(static method) add operation of two given scalars : scalar1(%.2f) + scalar2(%.2f) : %.2f\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar2.getValue()), Double.parseDouble(Tensor.add(scalar1, scalar2).getValue()));
        System.out.println();

        //no.25 | 전달받은 두 스칼라의 곱셈이 가능하다.
        System.out.println("no.25 | 전달받은 두 스칼라의 곱셈이 가능하다.");
        System.out.printf("\t\t(static method) multiply operation of two given scalars : scalar1(%.2f) x scalar2(%.2f) : %.2f\n",
                Double.parseDouble(scalar1.getValue()), Double.parseDouble(scalar2.getValue()), Double.parseDouble(Tensor.multiply(scalar1, scalar2).getValue()));
        System.out.println();

        //no.26 | 전달받은 두 벡터의 덧셈이 가능하다.
        System.out.println("no.26 | 전달받은 두 벡터의 덧셈이 가능하다.");
        System.out.println("\t\t(static method) add operation of two given vector : vector1 + vector2");
        System.out.print("\t\tvector1 \t\t  = ");
        VectorAssistance.printVectorln(vector1);
        System.out.print("\t\tvector2 \t\t  = ");
        VectorAssistance.printVectorln(vector2);
        System.out.print("\t\tvector1 + vector2 = ");
        VectorAssistance.printVectorln(Tensor.add(vector1, vector2));
        System.out.println();







    }
}