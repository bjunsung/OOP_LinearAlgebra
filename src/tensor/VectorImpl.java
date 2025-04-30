package tensor;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import tensor.Vector;

class VectorImpl implements Vector{
    List<Scalar> elements;
    VectorImpl(){
        elements = new ArrayList<>();
    }

    VectorImpl(int dimension, String value){
        elements = new ArrayList<>();
        for(int i = 0; i < dimension; ++i)
            elements.add(new ScalarImpl(value));
    }

    VectorImpl(int dimension, String min, String max){
        elements = new ArrayList<>();
        BigDecimal minValue = new BigDecimal(min);
        BigDecimal maxValue = new BigDecimal(max);
        Random random = new Random();
        for(int i = 0; i < dimension; ++i){
            BigDecimal randomValue = minValue.add(
                    new BigDecimal(Math.random()).multiply(maxValue.subtract(minValue))
            );
            elements.add(new ScalarImpl(randomValue.toString()));
        }
    }

    VectorImpl(String[] values){
        elements = new ArrayList<>();
        for (String value : values){
            elements.add(new ScalarImpl(value));
        }
    }

    public Scalar getElement(int index){
        if (index < 0 || index >= elements.size())
            throw new TensorInvalidInputException("Index out of bounds: " + index);
        return elements.get(index);
    }

    public void setElement(int index, Scalar value){
        if (index < 0 || index >= elements.size())
            throw new TensorInvalidInputException("Index out of bounds: " + index);
        elements.set(index, value);
    }

    public int getSize(){
        return elements.size();
    }

    public Vector clone() {
        List<Scalar> newElements = new ArrayList<>();
        for (Scalar scalar : this.elements) {
            newElements.add(scalar.clone());
        }
        VectorImpl cloned = new VectorImpl();
        cloned.elements = newElements;
        return cloned;
    }

    public boolean equals(Object obj){
        if ( this == obj ) return true;
        if ( obj == null || this.getClass() != obj.getClass()) return false;
        VectorImpl other = (VectorImpl) obj;
        if (this.getSize() != other.getSize()) return false;
        for (int i = 0; i < this.getSize(); ++i){
            if (!this.getElement(i).equals(other.getElement(i))) return false;
        }
        return true;
    }

    public void add(Vector other){
        if (this.getSize() != other.getSize())
            throw new TensorSizeMismatchException("can not operate vector addition : size is different");
        for (int i = 0; i < this.getSize(); ++i){
            this.getElement(i).add(other.getElement(i));
        }
    }

    public void multiply(Scalar other){
        for (int i = 0; i < this.getSize(); ++i){
            this.getElement(i).multiply(other);
        }
    }

    //no.30 n-차원 벡터 객체는 자신으로부터 nx1 행렬을 생성하여 반환할 수 있다.
    public Matrix toColumnMatrix() {
        List<List<Scalar>> matrixElements = new ArrayList<>();

        for (Scalar value : this.elements) {
            List<Scalar> row = new ArrayList<>();
            row.add(value.clone());
            matrixElements.add(row);
        }
        MatrixImpl columnMatrix = new MatrixImpl();
        columnMatrix.elements = matrixElements;
        return columnMatrix;
    }

    //no.31 n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.
    public Matrix toRowMatrix() {
        List<List<Scalar>> matrixElements = new ArrayList<>();
        List<Scalar> row = new ArrayList<>();
        for (Scalar scalar : this.elements){
            row.add(scalar.clone());
        }
        matrixElements.add(row);
        MatrixImpl rowMatrix = new MatrixImpl();
        rowMatrix.elements = matrixElements;
        return rowMatrix;
    }
}
