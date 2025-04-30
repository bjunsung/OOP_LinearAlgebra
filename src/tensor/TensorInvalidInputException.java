package tensor;

public class TensorInvalidInputException extends RuntimeException{
    public TensorInvalidInputException(String message){
        super(message);
    }
}


//TensorSizeMismatchException	벡터/행렬끼리 크기 다를 때 연산 시도
//TensorInvalidInputException	인덱스 잘못 지정, 잘못된 값 입력
//TensorArithmeticException	수학적으로 불가능한 연산 (예: 역행렬 없음)
//NonSquareMatrixException	정사각형이 아닌 행렬에 trace(), determinant() 호출
//MatrixMulMismatchException	행렬 곱셈 할 때 차원 조건 안 맞을 때