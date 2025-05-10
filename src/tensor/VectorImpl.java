package tensor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureClassLoader;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import tensor.Vector;

class VectorImpl implements Vector{
    List<Scalar> elements;
    VectorImpl(){
        elements = new ArrayList<>();
    }

    VectorImpl(List<Scalar> elements){
        this.elements = elements;
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
                    BigDecimal.valueOf(Math.random()).multiply(maxValue.subtract(minValue))
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

    @Override
    public Vector clone() {
        try {
            VectorImpl cloned = (VectorImpl) super.clone();
            for (int i = 0 ; i < this.getSize(); ++i) {
                cloned.setElement(i, this.getElement(i).clone());
            }
            return cloned;
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object obj){
        if (super.equals(obj)) return true;
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
        ScalarImpl one = new ScalarImpl("1.0");
        if (other.equals(one)) return;
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
        return new MatrixImpl(matrixElements);
    }

    //no.31 n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.
    public Matrix toRowMatrix() {
        List<List<Scalar>> matrixElements = new ArrayList<>();
        List<Scalar> row = new ArrayList<>();
        for (Scalar scalar : this.elements){
            row.add(scalar.clone());
        }
        matrixElements.add(row);
        return new MatrixImpl(matrixElements);
    }

    boolean rounding = false;

    public String toString(boolean rounding){
        this.rounding = rounding;
        String str = this.toString();
        rounding = false;
        return str;
    }

    @Override
    public String toString(){
        String vectorLine = "";
        for (int i = 0; i < this.getSize(); ++i) {
            BigDecimal bigdec = new BigDecimal(this.getElement(i).getValue());
            if (rounding)
                bigdec = bigdec.setScale(2, RoundingMode.HALF_UP);
            vectorLine = vectorLine + bigdec.toString();
            if (i != this.getSize() - 1)
                 vectorLine = vectorLine + "   ";
        }
        return vectorLine;
    }
}
