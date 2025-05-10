package tensor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

class MatrixImpl implements Matrix {
    final Scalar zero = new ScalarImpl("0.0");
    final Scalar one = new ScalarImpl("1.0");
    List<List<Scalar>> elements;

    private void checkRowIndex(int row){
        if (row < 0 || row >= this.getRowCount())
            throw new TensorSizeMismatchException("Invalid row index" + row);
    }

    private void checkColumnIndex(int col){
        if (col < 0 || col >= this.getColumnCount())
            throw new TensorSizeMismatchException("Invalid row index" + col);
    }

    private void checkIndices(int row, int col){
        checkRowIndex(row);
        checkColumnIndex(col);;
    }

    MatrixImpl() {
        elements = new ArrayList<>();
    }

    MatrixImpl(List<List<Scalar>> elements){
        this.elements = elements;
    }

    MatrixImpl(int rowDimension, int colDimension, String value) {
        elements = new ArrayList<>();
        for (int i = 0; i < rowDimension; ++i) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < colDimension; ++j) {
                row.add(new ScalarImpl(value));
            }
            elements.add(row);
        }
    }

    MatrixImpl(int rowDimension, int colDimension, String min, String max) {
        elements = new ArrayList<>();
        BigDecimal minValue = new BigDecimal(min);
        BigDecimal maxValue = new BigDecimal(max);
        for (int i = 0; i < rowDimension; ++i) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < colDimension; ++j) {
                BigDecimal randomValue = minValue.add(
                        BigDecimal.valueOf(Math.random()).multiply(maxValue.subtract(minValue))
                );
                row.add(new ScalarImpl(randomValue));
            }
            elements.add(row);
        }
    }

    //csv 파일로 행렬 생성
    MatrixImpl(String filepath) {
        elements = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                List<Scalar> row = new ArrayList<>();
                for (String token : tokens) {
                    row.add(new ScalarImpl(token.trim()));
                }
                elements.add(row);
            }
        } catch (FileNotFoundException e) {
            System.out.println(" file not found - " + filepath);
            throw new TensorInvalidInputException("none CSV file in" + filepath);
        } catch (IOException e) {
            System.out.println("FAIL to read CSV file");
            throw new TensorInvalidInputException("Error ");
        }
    }

    //2차원배열로 행렬 생성
    MatrixImpl(String[][] values) {
        elements = new ArrayList<>();
        for (String[] valueRow : values) {
            List<Scalar> row = new ArrayList<>();
            for (String value : valueRow) {
                row.add(new ScalarImpl(value));
            }
            elements.add(row);
        }
    }

    //단위행렬 생성
    MatrixImpl(int size) {
        elements = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < size; ++j) {
                row.add(new ScalarImpl(i == j ? "1.0" : "0.0"));
            }
            elements.add(row);
        }
    }

    public Scalar getElement(int row, int col) {
        checkIndices(row, col);
        return elements.get(row).get(col);
    }

    public void setElement(int row, int col, Scalar value) {
        checkIndices(row, col);
        elements.get(row).get(col).setValue(value.toString());
    }

    public int getRowCount() {
        return elements.size();
    }


    public int getColumnCount() {
        return elements.getFirst().size();
    }

    //no.17 객체 복제를 할 수 있다.
    @Override
    public Matrix clone() {
        try{
            MatrixImpl cloned = (MatrixImpl) super.clone();
            cloned.elements = new ArrayList<>();

        for (List<Scalar> row : this.elements) {
            List<Scalar> clonedRow = new ArrayList<>();
            for (Scalar scalar : row) {
                clonedRow.add(scalar.clone());
            }
            cloned.elements.add(clonedRow);
        }
            return cloned;
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object obj){
        if (super.equals(obj))
            return true;
        if (this.getClass() != obj.getClass())
            return false;
        MatrixImpl other = (MatrixImpl) obj;
        if (this.getRowCount() != other.getRowCount() || this.getColumnCount() != other.getColumnCount())
            return false;
        for (int i = 0; i < this.getRowCount(); ++i)
            for (int j = 0; j < this.getColumnCount(); ++ j)
                if(!this.getElement(i, j).equals(other.getElement(i,j)))
                    return false;
        return true;
    }

    //no.22 행렬은 다른 행렬과 덧셈이 가능하다.
    public void add(Matrix other) {
        if (other.getRowCount() != this.getRowCount() || other.getColumnCount() != this.getColumnCount()) {
            throw new TensorSizeMismatchException("invalid matrix addition size, can not operate"
                    + " (" + this.getRowCount() + "x" + this.getColumnCount() + ")+(" + other.getRowCount() + "x" + other.getColumnCount() + ")");
        }
        for (int i = 0; i < other.getRowCount(); i++) {
            for (int j = 0; j < other.getColumnCount(); j++) {
                this.getElement(i, j).add(other.getElement(i, j));
            }
        }
    }

    //no.23 행렬은 다른 행렬과 곱셈이 가능하다. (this matrix x input matrix)
    public void multiplyRight(Matrix other) {
        if (this.getColumnCount() != other.getRowCount()) {
            throw new TensorSizeMismatchException("invalid matrix multiply size, can not operate"
                    + " (" + this.getRowCount() + "x" + this.getColumnCount() + ")x(" + other.getRowCount() + "x" + other.getColumnCount() + ")");
        }
        List<List<Scalar>> multipliedElements = new ArrayList<>();
        for (int i = 0; i < this.getRowCount(); i++) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < other.getColumnCount(); j++) {
                Scalar scalar = new ScalarImpl("0.0");
                for (int k = 0; k < this.getColumnCount(); k++) {
                    scalar.add(new BigDecimal(this.getElement(i, k).getValue()).multiply(new BigDecimal(other.getElement(k, j).getValue())));
                }
                row.add(scalar);
            }
            multipliedElements.add(i, row);
        }
        this.elements = multipliedElements;
    }

    //no.23 행렬은 다른 행렬과 곱셈이 가능하다. (input matrix x this matrix)
    public void multiplyLeft(Matrix other) {
        if (other.getColumnCount() != this.getRowCount()) {
            throw new TensorInvalidInputException("invalid matrix multiply size, can not operate"
                    + " (" + other.getRowCount() + "x" + other.getColumnCount() + ")x(" + this.getRowCount() + "x" + this.getColumnCount() + ")");
        }
        List<List<Scalar>> multipliedElements = new ArrayList<>();
        for (int i = 0; i < other.getRowCount(); i++) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < this.getColumnCount(); j++) {
                Scalar scalar = new ScalarImpl("0.0");
                for (int k = 0; k < this.getRowCount(); k++) {
                    scalar.add(new BigDecimal(other.getElement(i, k).getValue()).multiply(new BigDecimal(this.getElement(k, j).getValue())));
                }
                row.add(scalar);
            }
            multipliedElements.add(i, row);
            this.elements = multipliedElements;
        }
    }

    //no.32 행렬은 다른 행렬과 가로로 합쳐질 수 있다.
    public void concatColumns(Matrix other) {
        if (this.getRowCount() != other.getRowCount())
            throw new TensorSizeMismatchException("matrix row size is different. a: " + this.getRowCount() + " b: " + other.getRowCount());
        for (int i = 0; i < other.getRowCount(); ++i) {
            for (int j = 0; j < other.getColumnCount(); ++j) {
                this.elements.get(i).add(other.getElement(i, j).clone());
            }
        }
    }

    //no.33 행렬은 다른 행렬과 세로로 합쳐질 수 있다.
    public void concatRows(Matrix other) {
        if (this.getColumnCount() != other.getColumnCount())
            throw new TensorSizeMismatchException("matrix column size is different. a: " + this.getColumnCount() + " b: " + other.getColumnCount());
        for (int i = 0; i < other.getRowCount(); ++i) {
            List<Scalar> newRow = new ArrayList<>();
            for (int j = 0; j < other.getColumnCount(); ++j)
                newRow.add(other.getElement(i, j).clone());
            this.elements.add(newRow);
        }
    }

    //no.34. 행렬은 특정 행을 벡터 형태로 추출해 줄 수 있다.
    public Vector extractRow(int rowIndex) {
        checkRowIndex(rowIndex);
        VectorImpl rowVector = new VectorImpl();
        for (int i = 0; i < this.getColumnCount(); ++i) {
            rowVector.elements.add(this.getElement(rowIndex, i).clone());
        }
        return rowVector;
    }

    //no.35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다.
    public Vector extractColumn(int columnIndex) {
        checkColumnIndex(columnIndex);
        VectorImpl columnVector = new VectorImpl();
        for (int i = 0; i < this.getRowCount(); ++i) {
            columnVector.elements.add(this.getElement(i, columnIndex).clone());
        }
        return columnVector;
    }

    //no.36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
    public Matrix subMatrix(int startRow, int endRow, int startCol, int endCol) {
        checkIndices(startRow, startCol);
        checkIndices(endRow, endCol);
        List<List<Scalar>> subMatrixElements = new ArrayList<>();
        for (int i = startRow; i <= endRow; ++i) {
            List<Scalar> row = new ArrayList<>();
            for (int j = startCol; j <= endCol; ++j) {
                row.add(this.getElement(i, j).clone());
            }
            subMatrixElements.add(row);
        }
        return new MatrixImpl(subMatrixElements);
    }

    //no.37. 행렬은 특정 범위의 부분 행렬을 추출해줄 수 있다. (minor)
    public Matrix minor(int rowToExclude, int colToExclude) {
        checkIndices(rowToExclude, colToExclude);
        List<List<Scalar>> subMatrixElements = new ArrayList<>();
        for (int i = 0; i < this.getRowCount(); ++i) {
            if (i == rowToExclude) continue;
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < this.getColumnCount(); ++j) {
                if (j == colToExclude) continue;
                row.add(this.getElement(i, j).clone());
            }
            subMatrixElements.add(row);
        }
        return new MatrixImpl(subMatrixElements);
    }

    //no.38. 행렬은 전치행령을 구해 줄 수 있다.
    public Matrix transpose() {
        List<List<Scalar>> transposeElements = new ArrayList<>();
        for (int i = 0; i < this.getColumnCount(); ++i) {
            List<Scalar> transposeRow = new ArrayList<>();
            for (int j = 0; j < this.getRowCount(); ++j) {
                transposeRow.add(this.getElement(j, i).clone());
            }
            transposeElements.add(transposeRow);
        }
        return new MatrixImpl(transposeElements);
    }

    //no.39 행렬은 대각 요소의 합을 구해줄 수 있다.
    public Scalar trace() {
        if (this.getRowCount() != this.getColumnCount())
            throw new NonSquareMatrixException("Non square matrix: " + this.getRowCount() + "x" + this.getColumnCount());
        ScalarImpl traceSum = new ScalarImpl("0.0");
        for (int i = 0; i < this.getRowCount(); ++i)
            traceSum.add(this.getElement(i, i)); //Scalar add연산, clone() 필요 없음
        return traceSum;
    }

    //no.40. 행렬은 자신이 정사각 행렬인지 여부를 반별해 줄 수 있다
    public boolean isSquare() {
        return this.getRowCount() == this.getColumnCount();
    }

    //no.41. 행렬은 자신이 상삼각 행렬인지 여부를 판별해 줄 수 있다.
    public boolean isUpperTriangular() {
        if (!this.isSquare()) return false;
        for (int i = 0; i < this.getRowCount(); ++i)
            for (int j = 0; j < i; ++j)
                if (!this.getElement(i, j).equals(zero))
                    return false;
        return true;
    }

    //no.42. 행렬은 자신이 하삼각 행렬인지 여부를 판별해 줄 수 있다.
    public boolean isLowerTriangular() {
        if (!this.isSquare()) return false;
        for (int i = 0; i < this.getRowCount(); ++i)
            for (int j = i + 1; j < this.getRowCount(); ++j)
                if (!this.getElement(i, j).equals(zero))
                    return false;
        return true;
    }

    //no.43. 행렬은 자신이 단위행렬인지 여부를 판별해 줄 수 있다.
    public boolean isIdentityMatrix() {
        BigDecimal epsilon = new BigDecimal("0.00000001");
        BigDecimal diff;
        if (!this.isSquare()) return false;
        for (int i = 0; i < this.getRowCount(); ++i)
            for (int j = 0; j < this.getColumnCount(); ++j) {
                diff = new BigDecimal(this.getElement(i, j).getValue()).abs();
                if ((i != j) && diff.compareTo(epsilon) > 0) return false;
                diff = diff.add(new BigDecimal("-1.0")).abs();
                if ((i == j) && diff.compareTo(epsilon) > 0) return false;
            }
        return true;
    }

    //no.44. 행렬은 자신이 영행렬인지 여부를 판별해 줄 수 있다.
    public boolean isZeroMatrix() {
        for (int i = 0; i < this.getRowCount(); ++i)
            for (int j = 0; j < this.getColumnCount(); ++j)
                if (!this.getElement(i, j).equals(zero)) return false;
        return true;
    }

    //no.45. 행렬은 특정 두 행의 위치를 맞교환할 수 있다.
    public void swapRows(int row1, int row2) {
        checkRowIndex(row1);
        checkRowIndex(row2);
        if (row1 == row2) return;
        for (int i = 0; i < this.getColumnCount(); ++ i){
            Scalar temp = this.getElement(row1, i).clone();
            this.getElement(row1, i).setValue(this.getElement(row2, i).toString());
            this.getElement(row2, i).setValue(temp.toString());
        }
    }

    //no.46. 행렬은 특정 두 열의 위치를 맞교환할 수 있다.
    public void swapColumns(int col1, int col2) {
        checkColumnIndex(col1);
        checkColumnIndex(col2);
        if (col1 == col2) return;
        for (int i = 0; i < this.getRowCount(); ++i) {
            Scalar temp = this.getElement(i, col1).clone();
            this.getElement(i, col1).setValue(this.getElement(i, col2).toString());
            this.getElement(i, col2).setValue(temp.toString());
        }
    }

    //no.47. 행렬은 특정 행에 스칼라배 할 수 있다.
    public void scaleRow(int row, Scalar scalar) {
        checkRowIndex(row);
        if (scalar.equals(one)) return;
        for (int i = 0; i < this.getColumnCount(); ++i)
            this.getElement(row, i).multiply(scalar);
    }

    //no.48. 행렬은 특정 열에 스칼라배 할 수 있다.
    public void scaleColumn(int column, Scalar scalar) {
        checkColumnIndex(column);
        if (scalar.equals(one)) return;
        for (int i = 0; i < this.getRowCount(); ++i)
            this.getElement(i, column).multiply(scalar);
    }

    //no.49. 행렬은 특정 행에 다른 행의 스칼라배를 더할 수 있다.
    public void addScaledRow(int targetRow, int sourceRow, Scalar scalar) {
        checkRowIndex(targetRow);
        checkRowIndex(sourceRow);
        if (targetRow == sourceRow)
            throw new TensorInvalidInputException("target row is equal to source row");
        if (scalar.equals(zero)) return;
        for (int i = 0; i < this.getColumnCount(); ++i) {
            if (this.getElement(sourceRow, i).equals(zero)) continue;
            Scalar temp = this.getElement(sourceRow, i).clone();
            temp.multiply(scalar);
            this.getElement(targetRow, i).add(temp);
        }
    }

    //no.50. 행렬은 특정 열에 다른 열의 스칼라배를 더할 수 있다.
    public void addScaledColumn(int targetCol, int sourceCol, Scalar scalar) {
        checkColumnIndex(targetCol);
        checkColumnIndex(sourceCol);
        if (targetCol == sourceCol)
            throw new TensorInvalidInputException("target column is equal to source column");
        if (scalar.equals(zero)) return;
        for (int i = 0; i < this.getRowCount(); ++i) {
            if (this.getElement(i, sourceCol).equals(zero)) continue;
            Scalar temp = this.getElement(i, sourceCol).clone();
            temp.multiply(scalar);
            this.getElement(i, targetCol).add(temp);
        }
    }

    //no.51. 행렬은 자신으로부터 RREF 행렬을 구해서 반환 해 줄 수 있다
    public Matrix toRref() {
        MatrixImpl rrefMatrix =  (MatrixImpl) this.clone();
        int rowCount = rrefMatrix.getRowCount();
        int colCount = rrefMatrix.getColumnCount();
        int lead = 0;
        for (int row = 0; row < rowCount; row++) {
            if (lead >= colCount)
                break;
            int r = row;
            while (r < rowCount && rrefMatrix.getElement(r, lead).compareTo(new ScalarImpl("0.0")) == 0) {
                r++;
            }
            if (r == rowCount) {
                lead++;
                row--; // 다시 같은 r행에서 시도
                continue;
            }
            // 1. pivot row i → r로 스왑
            if (r != row) {
                rrefMatrix.swapRows(r, row);
            }
            // 2. 선행 원소를 1로 만들기
            Scalar pivot = rrefMatrix.getElement(row, lead);
            Scalar reciprocal;
            reciprocal = new ScalarImpl(new BigDecimal("1.0").divide(new BigDecimal(pivot.getValue()), 10, BigDecimal.ROUND_HALF_UP).toString());
            rrefMatrix.scaleRow(row, reciprocal);
            // 3. 현재 열의 다른 행에 대해 0 만들기
            for (int i = 0; i < rowCount; i++) {
                if (i != row) {
                    Scalar factor = rrefMatrix.getElement(i, lead).clone();
                    factor.multiply(new ScalarImpl("-1.0"));
                    rrefMatrix.addScaledRow(i, row, factor);

                }
            }
            // 4. 오차 허용
            BigDecimal epsilon = new BigDecimal("0.00000001");
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    BigDecimal diff = new BigDecimal(rrefMatrix.getElement(i, j).getValue()).abs();
                    if (diff.compareTo(epsilon) < 0) {
                        rrefMatrix.setElement(i, j, zero);
                    }
                }
            }
            lead++;
        }
        return rrefMatrix;
    }

    //no.52. 행렬은 자신이 RREF 행렬인지 여부를 구해줄 수 있다.
    public boolean isRref() {
        if (this.getRowCount() == 0 || this.getColumnCount() == 0) return true;
        int lead = -1;  // 선행 원소의 열 위치 추적
        for (int i = 0; i < this.getRowCount(); ++i) {
            int rowLead = -1;
            // 1. 이 행의 선행 원소 찾기
            for (int j = 0; j < this.getColumnCount(); ++j) {
                if (!this.getElement(i, j).equals(zero)) {
                    rowLead = j;
                    break;
                }
            }
            // 2. 전부 0인 행이면, 그 아래는 모두 0.
            if (rowLead == -1) {
                for (int k = i + 1; k < this.getRowCount(); ++k) {
                    for (int j = 0; j < this.getColumnCount(); ++j) {
                        if (!this.getElement(k, j).equals(zero)) {
                            return false; // 0행 아래에 0이 아닌 값 발견 -> not RREF
                        }
                    }
                }
                break; // 남은 행은 다 0이므로 더 이상 검사 안 함
            }
            // 3. 선행 원소는 반드시 1이어야 함
            if (!this.getElement(i, rowLead).equals(one)) {
                return false;
            }
            // 4. 선행 열의 다른 행은 모두 0이어야 함
            for (int r = 0; r < this.getRowCount(); ++r) {
                if (r == i) continue;
                if (!this.getElement(r, rowLead).equals(zero)) {
                    return false;
                }
            }
            // 5. 선행 원소는 오른쪽 아래로 향해야 함
            if (rowLead <= lead) {
                return false;
            }
            lead = rowLead;
        }
        return true;
    }

    //no.53. 행렬은 자신의 행렬식을 구해줄 수 있다.
    public Scalar determinant() {
        if (!this.isSquare()) {
            throw new NonSquareMatrixException("Determinant is only defined for square matrices.");
        }
        int size = this.getRowCount();
        if (size == 1) {
            return this.getElement(0, 0).clone(); //1x1 matrix
        }
        if (size == 2) {
            Scalar a = this.getElement(0, 0).clone();
            Scalar b = this.getElement(0, 1).clone();
            Scalar c = this.getElement(1, 0).clone();
            Scalar d = this.getElement(1, 1).clone();
            a.multiply(d); // ad
            c.multiply(b); // bc
            a.add(new ScalarImpl("-" + c.getValue())); // ad - bc
            return a;
        }
        else { // size > 2
            Scalar det = new ScalarImpl("0.0");
            for (int i = 0; i < size; ++i) {
                Scalar cofactor = this.getElement(0, i).clone();
                Matrix minor = this.minor(0, i);
                Scalar subDet = minor.determinant();
                cofactor.multiply(subDet);
                if (i % 2 == 1) {
                    cofactor.multiply(new ScalarImpl("-1"));
                }
                det.add(cofactor);
            }
            return det;
        }
    }

    //no.54. 행렬은 자신의 역행렬을 구해줄 수 있다.
    public Matrix inverse() {
        if (!this.isSquare())
            throw new NonSquareMatrixException("Non-square matrix does not have an inverse.");
        MatrixImpl rrefMatrix = (MatrixImpl) this.clone();
        int size = rrefMatrix.getRowCount();
        MatrixImpl inverseMatrix = new MatrixImpl(size); // 단위행렬 생성
        int lead = 0;
        for (int row = 0; row < size; row++) {
            if (lead >= size)
                break;
            int r = row;
            while (r < size && rrefMatrix.getElement(r, lead).compareTo(new ScalarImpl("0.0")) == 0) {
                r++;
            }
            if (r == size) {
                lead++;
                row--;
                continue;
            }
            // 1. pivot row i > r로 교환
            if (r != row) {
                rrefMatrix.swapRows(r, row);
                inverseMatrix.swapRows(r, row);
            }
            // 2. pivot을 1로 만들기
            Scalar pivot = rrefMatrix.getElement(row, lead).clone();
            Scalar reciprocal = new ScalarImpl(new BigDecimal("1.0").divide(new BigDecimal(pivot.getValue()), 10, BigDecimal.ROUND_HALF_UP).toString());
            rrefMatrix.scaleRow(row, reciprocal);
            inverseMatrix.scaleRow(row, reciprocal);
            // 3. pivot 열의 다른 행을 0으로 만들기
            for (int i = 0; i < size; i++) {
                if (i == row) continue;
                Scalar factor = rrefMatrix.getElement(i, lead).clone();
                factor.multiply(new ScalarImpl("-1.0"));
                rrefMatrix.addScaledRow(i, row, factor);
                inverseMatrix.addScaledRow(i, row, factor);
            }
            //오차 허용
            BigDecimal epsilon = new BigDecimal("0.00000001");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    BigDecimal diff = new BigDecimal(rrefMatrix.getElement(i, j).getValue()).abs();
                    if (diff.compareTo(epsilon) < 0) {
                        rrefMatrix.setElement(i, j, zero);
                    }
                }
            }
            lead++;
        }
        if (!rrefMatrix.isIdentityMatrix()) {
            throw new TensorArithmeticException("Matrix is singular and cannot be inverted.");
        }
        return inverseMatrix;
    }


    @Override
    public String toString(){
        String indent = "\t\t\t\t\t\t";
        StringBuilder matrixString = new StringBuilder();
        for (int i=0; i < this.getRowCount(); ++i) {
            matrixString.append(indent);
            for (int j = 0; j < this.getColumnCount(); ++j) {
                BigDecimal bigdec = new BigDecimal(this.getElement(i, j).toString());
                matrixString.append(String.format("%9.2f", bigdec.doubleValue()));
            }
            if(i != this.getRowCount() - 1)
                matrixString.append("\n");
        }
        return matrixString.toString();
    }
}

