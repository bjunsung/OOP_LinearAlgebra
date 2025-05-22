package tensor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

class MatrixImpl implements Matrix {
    final Scalar zero = new ScalarImpl("0.0");
    final Scalar one = new ScalarImpl("1.0");
    List<List<Scalar>> elements;

    private void checkRowIndex(int row){
        if (row < 0 || row >= this.getMatrixRowCount())
            throw new TensorInvalidIndexException("Invalid row index" + row);
    }

    private void checkColumnIndex(int col){
        if (col < 0 || col >= this.getMatrixColumnCount())
            throw new TensorInvalidIndexException("Invalid row index" + col);
    }

    private void checkIndices(int row, int col){
        checkRowIndex(row);
        checkColumnIndex(col);
    }

    MatrixImpl() {
        elements = new ArrayList<>();
    }

    MatrixImpl(List<List<Scalar>> elements){
        this.elements = elements;
    }

    MatrixImpl(int rowDimension, int colDimension, String value) {
        if (rowDimension < 0 || colDimension < 0)
            throw new TensorInvalidInputException("row/column dimension must bigger than or equal to zero");
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
        if (rowDimension < 0 || colDimension < 0)
            throw new TensorInvalidInputException("row/column dimension must bigger than or equal to zero");
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
            for (int i = 0; i < this.getMatrixRowCount(); ++i)
                if(this.elements.get(i).size() != this.getMatrixRowCount())
                    throw new TensorInvalidInputException("Matrix size error");
        } catch (FileNotFoundException e) {
            System.out.println(" file not found - " + filepath);
            throw new TensorInvalidInputException("none CSV file in" + filepath);
        } catch (IOException e) {
            System.out.println("FAIL to read CSV file");
            throw new TensorInvalidInputException("Error ");
        } catch(IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    //2차원배열로 행렬 생성
    MatrixImpl(String[][] values) {
        int columnSize = values[0].length;
        for (int i = 1; i < values.length; ++i)
            if (values[i].length != columnSize)
                throw new TensorInvalidInputException("inconsistent column count detected");

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
        if (size < 0)
            throw new TensorInvalidDimensionException("row/column dimension must bigger than or equal to zero");
        elements = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < size; ++j) {
                row.add(new ScalarImpl(i == j ? "1.0" : "0.0"));
            }
            elements.add(row);
        }
    }

    public Scalar getMatrixElement(int row, int col) {
        checkIndices(row, col);
        return elements.get(row).get(col);
    }

    public void setMatrixElement(int row, int col, Scalar value) {
        checkIndices(row, col);
        elements.get(row).get(col).setValue(value.toString());
    }

    public int getMatrixRowCount() {
        return elements.size();
    }


    public int getMatrixColumnCount() {
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
        if (this.getMatrixRowCount() != other.getMatrixRowCount() || this.getMatrixColumnCount() != other.getMatrixColumnCount())
            return false;
        for (int i = 0; i < this.getMatrixRowCount(); ++i)
            for (int j = 0; j < this.getMatrixColumnCount(); ++ j)
                if(!this.getMatrixElement(i, j).equals(other.getMatrixElement(i,j)))
                    return false;
        return true;
    }

    //no.22 행렬은 다른 행렬과 덧셈이 가능하다.
    public void add(Matrix other) {
        if (other.getMatrixRowCount() != this.getMatrixRowCount() || other.getMatrixColumnCount() != this.getMatrixColumnCount()) {
            throw new TensorSizeMismatchException("invalid matrix addition size, can not operate"
                    + " (" + this.getMatrixRowCount() + "x" + this.getMatrixColumnCount() + ")+(" + other.getMatrixRowCount() + "x" + other.getMatrixColumnCount() + ")");
        }
        for (int i = 0; i < other.getMatrixRowCount(); i++) {
            for (int j = 0; j < other.getMatrixColumnCount(); j++) {
                this.getMatrixElement(i, j).add(other.getMatrixElement(i, j));
            }
        }
    }

    //no.23 행렬은 다른 행렬과 곱셈이 가능하다. (this matrix x input matrix)
    public void multiplyRight(Matrix other) {
        if (this.getMatrixColumnCount() != other.getMatrixRowCount()) {
            throw new MatrixMulMismatchException("invalid matrix multiply size, can not operate"
                    + " (" + this.getMatrixRowCount() + "x" + this.getMatrixColumnCount() + ")x(" + other.getMatrixRowCount() + "x" + other.getMatrixColumnCount() + ")");
        }
        List<List<Scalar>> multipliedElements = new ArrayList<>();
        for (int i = 0; i < this.getMatrixRowCount(); i++) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < other.getMatrixColumnCount(); j++) {
                Scalar scalar = new ScalarImpl("0.0");
                for (int k = 0; k < this.getMatrixColumnCount(); k++) {
                    scalar.add(new BigDecimal(this.getMatrixElement(i, k).getValue()).multiply(new BigDecimal(other.getMatrixElement(k, j).getValue())));
                }
                row.add(scalar);
            }
            multipliedElements.add(i, row);
        }
        this.elements = multipliedElements;
    }

    //no.23 행렬은 다른 행렬과 곱셈이 가능하다. (input matrix x this matrix)
    public void multiply(Matrix other) {
        if (this.getMatrixColumnCount() != other.getMatrixRowCount()) {
            throw new MatrixMulMismatchException("invalid matrix multiply size, can not operate"
                    + " (" + other.getMatrixRowCount() + "x" + other.getMatrixColumnCount() + ")x(" + this.getMatrixRowCount() + "x" + this.getMatrixColumnCount() + ")");
        }
        List<List<Scalar>> multipliedElements = new ArrayList<>();
        for (int i = 0; i < other.getMatrixRowCount(); i++) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < this.getMatrixColumnCount(); j++) {
                Scalar scalar = new ScalarImpl("0.0");
                for (int k = 0; k < this.getMatrixRowCount(); k++) {
                    scalar.add(new BigDecimal(other.getMatrixElement(i, k).getValue()).multiply(new BigDecimal(this.getMatrixElement(k, j).getValue())));
                }
                row.add(scalar);
            }
            multipliedElements.add(i, row);
            this.elements = multipliedElements;
        }
    }

    //no.32 행렬은 다른 행렬과 가로로 합쳐질 수 있다.
    public void concatColumns(Matrix other) {
        if (this.getMatrixRowCount() != other.getMatrixRowCount())
            throw new TensorSizeMismatchException("matrix row size is different. a: " + this.getMatrixRowCount() + " b: " + other.getMatrixRowCount());
        for (int i = 0; i < other.getMatrixRowCount(); ++i) {
            for (int j = 0; j < other.getMatrixColumnCount(); ++j) {
                this.elements.get(i).add(other.getMatrixElement(i, j).clone());
            }
        }
    }

    //no.33 행렬은 다른 행렬과 세로로 합쳐질 수 있다.
    public void concatRows(Matrix other) {
        if (this.getMatrixColumnCount() != other.getMatrixColumnCount())
            throw new TensorSizeMismatchException("matrix column size is different. a: " + this.getMatrixColumnCount() + " b: " + other.getMatrixColumnCount());
        for (int i = 0; i < other.getMatrixRowCount(); ++i) {
            List<Scalar> newRow = new ArrayList<>();
            for (int j = 0; j < other.getMatrixColumnCount(); ++j)
                newRow.add(other.getMatrixElement(i, j).clone());
            this.elements.add(newRow);
        }
    }

    //no.34. 행렬은 특정 행을 벡터 형태로 추출해 줄 수 있다.
    public Vector extractRow(int rowIndex) {
        checkRowIndex(rowIndex);
        VectorImpl rowVector = new VectorImpl();
        for (int i = 0; i < this.getMatrixColumnCount(); ++i) {
            rowVector.elements.add(this.getMatrixElement(rowIndex, i).clone());
        }
        return rowVector;
    }

    //no.35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다.
    public Vector extractColumn(int columnIndex) {
        checkColumnIndex(columnIndex);
        VectorImpl columnVector = new VectorImpl();
        for (int i = 0; i < this.getMatrixRowCount(); ++i) {
            columnVector.elements.add(this.getMatrixElement(i, columnIndex).clone());
        }
        return columnVector;
    }

    //no.36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
    public Matrix subMatrix(int startRow, int endRow, int startCol, int endCol) {
        if (startRow > endRow || startCol > endCol)
            throw new TensorInvalidInputException("start index must be smaller than or equal to end index");
        checkIndices(startRow, startCol);
        checkIndices(endRow, endCol);
        List<List<Scalar>> subMatrixElements = new ArrayList<>();
        for (int i = startRow; i <= endRow; ++i) {
            List<Scalar> row = new ArrayList<>();
            for (int j = startCol; j <= endCol; ++j) {
                row.add(this.getMatrixElement(i, j).clone());
            }
            subMatrixElements.add(row);
        }
        return new MatrixImpl(subMatrixElements);
    }

    //no.37. 행렬은 특정 범위의 부분 행렬을 추출해줄 수 있다. (minor)
    public Matrix minor(int rowToExclude, int colToExclude) {
        checkIndices(rowToExclude, colToExclude);
        List<List<Scalar>> subMatrixElements = new ArrayList<>();
        for (int i = 0; i < this.getMatrixRowCount(); ++i) {
            if (i == rowToExclude) continue;
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < this.getMatrixColumnCount(); ++j) {
                if (j == colToExclude) continue;
                row.add(this.getMatrixElement(i, j).clone());
            }
            subMatrixElements.add(row);
        }
        return new MatrixImpl(subMatrixElements);
    }

    //no.38. 행렬은 전치행령을 구해 줄 수 있다.
    public Matrix transpose() {
        List<List<Scalar>> transposeElements = new ArrayList<>();
        for (int i = 0; i < this.getMatrixColumnCount(); ++i) {
            List<Scalar> transposeRow = new ArrayList<>();
            for (int j = 0; j < this.getMatrixRowCount(); ++j) {
                transposeRow.add(this.getMatrixElement(j, i).clone());
            }
            transposeElements.add(transposeRow);
        }
        return new MatrixImpl(transposeElements);
    }

    //no.39 행렬은 대각 요소의 합을 구해줄 수 있다.
    public Scalar trace() {
        if (this.getMatrixRowCount() != this.getMatrixColumnCount())
            throw new MatrixNonSquareException("Non square matrix: " + this.getMatrixRowCount() + "x" + this.getMatrixColumnCount());
        ScalarImpl traceSum = new ScalarImpl("0.0");
        for (int i = 0; i < this.getMatrixRowCount(); ++i)
            traceSum.add(this.getMatrixElement(i, i)); //Scalar add연산, clone() 필요 없음
        return traceSum;
    }

    //no.40. 행렬은 자신이 정사각 행렬인지 여부를 반별해 줄 수 있다
    public boolean isSquare() {
        return this.getMatrixRowCount() == this.getMatrixColumnCount();
    }

    //no.41. 행렬은 자신이 상삼각 행렬인지 여부를 판별해 줄 수 있다.
    public boolean isUpperTriangular() {
        if (!this.isSquare()) return false;
        for (int i = 0; i < this.getMatrixRowCount(); ++i)
            for (int j = 0; j < i; ++j)
                if (!this.getMatrixElement(i, j).equals(zero))
                    return false;
        return true;
    }

    //no.42. 행렬은 자신이 하삼각 행렬인지 여부를 판별해 줄 수 있다.
    public boolean isLowerTriangular() {
        if (!this.isSquare()) return false;
        for (int i = 0; i < this.getMatrixRowCount(); ++i)
            for (int j = i + 1; j < this.getMatrixRowCount(); ++j)
                if (!this.getMatrixElement(i, j).equals(zero))
                    return false;
        return true;
    }

    //no.43. 행렬은 자신이 단위행렬인지 여부를 판별해 줄 수 있다.
    public boolean isIdentityMatrix() {
        BigDecimal epsilon = new BigDecimal("0.00000001");
        BigDecimal diff;
        if (!this.isSquare()) return false;
        for (int i = 0; i < this.getMatrixRowCount(); ++i)
            for (int j = 0; j < this.getMatrixColumnCount(); ++j) {
                diff = new BigDecimal(this.getMatrixElement(i, j).getValue()).abs();
                if ((i != j) && diff.compareTo(epsilon) > 0) return false;
                diff = diff.add(new BigDecimal("-1.0")).abs();
                if ((i == j) && diff.compareTo(epsilon) > 0) return false;
            }
        return true;
    }

    //no.44. 행렬은 자신이 영행렬인지 여부를 판별해 줄 수 있다.
    public boolean isZeroMatrix() {
        for (int i = 0; i < this.getMatrixRowCount(); ++i)
            for (int j = 0; j < this.getMatrixColumnCount(); ++j)
                if (!this.getMatrixElement(i, j).equals(zero)) return false;
        return true;
    }

    //no.45. 행렬은 특정 두 행의 위치를 맞교환할 수 있다.
    public void swapRows(int row1, int row2) {
        checkRowIndex(row1);
        checkRowIndex(row2);
        if (row1 == row2) return;
        for (int i = 0; i < this.getMatrixColumnCount(); ++ i){
            Scalar temp = this.getMatrixElement(row1, i).clone();
            this.getMatrixElement(row1, i).setValue(this.getMatrixElement(row2, i).toString());
            this.getMatrixElement(row2, i).setValue(temp.toString());
        }
    }

    //no.46. 행렬은 특정 두 열의 위치를 맞교환할 수 있다.
    public void swapColumns(int col1, int col2) {
        checkColumnIndex(col1);
        checkColumnIndex(col2);
        if (col1 == col2) return;
        for (int i = 0; i < this.getMatrixRowCount(); ++i) {
            Scalar temp = this.getMatrixElement(i, col1).clone();
            this.getMatrixElement(i, col1).setValue(this.getMatrixElement(i, col2).toString());
            this.getMatrixElement(i, col2).setValue(temp.toString());
        }
    }

    //no.47. 행렬은 특정 행에 스칼라배 할 수 있다.
    public void scaleRow(int row, Scalar scalar) {
        checkRowIndex(row);
        if (scalar.equals(one)) return;
        for (int i = 0; i < this.getMatrixColumnCount(); ++i)
            this.getMatrixElement(row, i).multiply(scalar);
    }

    //no.48. 행렬은 특정 열에 스칼라배 할 수 있다.
    public void scaleColumn(int column, Scalar scalar) {
        checkColumnIndex(column);
        if (scalar.equals(one)) return;
        for (int i = 0; i < this.getMatrixRowCount(); ++i)
            this.getMatrixElement(i, column).multiply(scalar);
    }

    //no.49. 행렬은 특정 행에 다른 행의 스칼라배를 더할 수 있다.
    public void addScaledRow(int targetRow, int sourceRow, Scalar scalar) {
        checkRowIndex(targetRow);
        checkRowIndex(sourceRow);
        if (targetRow == sourceRow)
            throw new TensorInvalidInputException("target row is equal to source row");
        if (scalar.equals(zero)) return;
        for (int i = 0; i < this.getMatrixColumnCount(); ++i) {
            if (this.getMatrixElement(sourceRow, i).equals(zero)) continue;
            Scalar temp = this.getMatrixElement(sourceRow, i).clone();
            temp.multiply(scalar);
            this.getMatrixElement(targetRow, i).add(temp);
        }
    }

    //no.50. 행렬은 특정 열에 다른 열의 스칼라배를 더할 수 있다.
    public void addScaledColumn(int targetCol, int sourceCol, Scalar scalar) {
        checkColumnIndex(targetCol);
        checkColumnIndex(sourceCol);
        if (targetCol == sourceCol)
            throw new TensorInvalidInputException("target column is equal to source column");
        if (scalar.equals(zero)) return;
        for (int i = 0; i < this.getMatrixRowCount(); ++i) {
            if (this.getMatrixElement(i, sourceCol).equals(zero)) continue;
            Scalar temp = this.getMatrixElement(i, sourceCol).clone();
            temp.multiply(scalar);
            this.getMatrixElement(i, targetCol).add(temp);
        }
    }

    //no.51. 행렬은 자신으로부터 RREF 행렬을 구해서 반환 해 줄 수 있다
    public Matrix toRref() {
        MatrixImpl rrefMatrix =  (MatrixImpl) this.clone();
        int rowCount = rrefMatrix.getMatrixRowCount();
        int colCount = rrefMatrix.getMatrixColumnCount();
        int lead = 0;
        for (int row = 0; row < rowCount; row++) {
            if (lead >= colCount)
                break;
            int r = row;
            while (r < rowCount && rrefMatrix.getMatrixElement(r, lead).compareTo(new ScalarImpl("0.0")) == 0) {
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
            Scalar pivot = rrefMatrix.getMatrixElement(row, lead);
            Scalar reciprocal;
            reciprocal = new ScalarImpl(new BigDecimal("1.0").divide(new BigDecimal(pivot.getValue()), 10, BigDecimal.ROUND_HALF_UP).toString());
            rrefMatrix.scaleRow(row, reciprocal);
            // 3. 현재 열의 다른 행에 대해 0 만들기
            for (int i = 0; i < rowCount; i++) {
                if (i != row) {
                    Scalar factor = rrefMatrix.getMatrixElement(i, lead).clone();
                    factor.multiply(new ScalarImpl("-1.0"));
                    rrefMatrix.addScaledRow(i, row, factor);

                }
            }
            // 4. 오차 허용
            BigDecimal epsilon = new BigDecimal("0.00000001");
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    BigDecimal diff = new BigDecimal(rrefMatrix.getMatrixElement(i, j).getValue()).abs();
                    if (diff.compareTo(epsilon) < 0) {
                        rrefMatrix.setMatrixElement(i, j, zero);
                    }
                }
            }
            lead++;
        }
        return rrefMatrix;
    }

    //no.52. 행렬은 자신이 RREF 행렬인지 여부를 구해줄 수 있다.
    public boolean isRref() {
        if (this.getMatrixRowCount() == 0 || this.getMatrixColumnCount() == 0) return true;
        int lead = -1;  // 선행 원소의 열 위치 추적
        for (int i = 0; i < this.getMatrixRowCount(); ++i) {
            int rowLead = -1;
            // 1. 이 행의 선행 원소 찾기
            for (int j = 0; j < this.getMatrixColumnCount(); ++j) {
                if (!this.getMatrixElement(i, j).equals(zero)) {
                    rowLead = j;
                    break;
                }
            }
            // 2. 전부 0인 행이면, 그 아래는 모두 0.
            if (rowLead == -1) {
                for (int k = i + 1; k < this.getMatrixRowCount(); ++k) {
                    for (int j = 0; j < this.getMatrixColumnCount(); ++j) {
                        if (!this.getMatrixElement(k, j).equals(zero)) {
                            return false; // 0행 아래에 0이 아닌 값 발견 -> not RREF
                        }
                    }
                }
                break; // 남은 행은 다 0이므로 더 이상 검사 안 함
            }
            // 3. 선행 원소는 반드시 1이어야 함
            if (!this.getMatrixElement(i, rowLead).equals(one)) {
                return false;
            }
            // 4. 선행 열의 다른 행은 모두 0이어야 함
            for (int r = 0; r < this.getMatrixRowCount(); ++r) {
                if (r == i) continue;
                if (!this.getMatrixElement(r, rowLead).equals(zero)) {
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
            throw new MatrixNonSquareException("Determinant is only defined for square matrices.");
        }
        int size = this.getMatrixRowCount();
        if (size == 1) {
            return this.getMatrixElement(0, 0).clone(); //1x1 matrix
        }
        if (size == 2) {
            Scalar a = this.getMatrixElement(0, 0).clone();
            Scalar b = this.getMatrixElement(0, 1).clone();
            Scalar c = this.getMatrixElement(1, 0).clone();
            Scalar d = this.getMatrixElement(1, 1).clone();
            a.multiply(d); // ad
            c.multiply(b); // bc
            a.add(new ScalarImpl("-" + c.getValue())); // ad - bc
            return a;
        }
        else { // size > 2
            Scalar det = new ScalarImpl("0.0");
            for (int i = 0; i < size; ++i) {
                Scalar cofactor = this.getMatrixElement(0, i).clone();
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
            throw new MatrixNonSquareException("Non-square matrix does not have an inverse.");
        MatrixImpl rrefMatrix = (MatrixImpl) this.clone();
        int size = rrefMatrix.getMatrixRowCount();
        MatrixImpl inverseMatrix = new MatrixImpl(size); // 단위행렬 생성
        int lead = 0;
        for (int row = 0; row < size; row++) {
            if (lead >= size)
                break;
            int r = row;
            while (r < size && rrefMatrix.getMatrixElement(r, lead).compareTo(new ScalarImpl("0.0")) == 0) {
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
            Scalar pivot = rrefMatrix.getMatrixElement(row, lead).clone();
            Scalar reciprocal = new ScalarImpl(new BigDecimal("1.0").divide(new BigDecimal(pivot.getValue()), 10, BigDecimal.ROUND_HALF_UP).toString());
            rrefMatrix.scaleRow(row, reciprocal);
            inverseMatrix.scaleRow(row, reciprocal);
            // 3. pivot 열의 다른 행을 0으로 만들기
            for (int i = 0; i < size; i++) {
                if (i == row) continue;
                Scalar factor = rrefMatrix.getMatrixElement(i, lead).clone();
                factor.multiply(new ScalarImpl("-1.0"));
                rrefMatrix.addScaledRow(i, row, factor);
                inverseMatrix.addScaledRow(i, row, factor);
            }
            //오차 허용
            BigDecimal epsilon = new BigDecimal("0.00000001");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    BigDecimal diff = new BigDecimal(rrefMatrix.getMatrixElement(i, j).getValue()).abs();
                    if (diff.compareTo(epsilon) < 0) {
                        rrefMatrix.setMatrixElement(i, j, zero);
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

    //no.28 전달받은 두 행렬의 덧셈
    public static Matrix add(Matrix a, Matrix b) {
        if(a.getMatrixRowCount() != b.getMatrixRowCount() || a.getMatrixColumnCount() != b.getMatrixColumnCount()){
            throw new TensorInvalidInputException("invalid matrix size");
        }
        Matrix newMatrix = a.clone();
        for(int i = 0; i < newMatrix.getMatrixRowCount(); ++i){
            for(int j = 0; j < newMatrix.getMatrixColumnCount(); ++j){
                newMatrix.getMatrixElement(i,j).add(b.getMatrixElement(i,j));
            }
        }
        return newMatrix;
    }

    //no.29 전달받은 두 행렬의 곱셈
    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getMatrixColumnCount() != b.getMatrixRowCount()){
            throw new TensorInvalidInputException("invalid matrix multiply size"
                    + "(" + a.getMatrixRowCount() + "x" + a.getMatrixColumnCount() + ")x(" + b.getMatrixRowCount() + "x" + b.getMatrixColumnCount() + ")");
        }
        Matrix newMatrix = a.clone();
        newMatrix.multiplyRight(b);
        return newMatrix;
    }

    //no.32 전달받은 두 행렬의 가로 합(concat columns)
    public static Matrix concatColumns(Matrix a, Matrix b) {
        Matrix concated = a.clone();
        concated.concatColumns(b);
        return concated;
    }

    //no.33 전달받은 두 행렬의 세로 합(concat rows)
    public static Matrix concatRows(Matrix a, Matrix b) {
        Matrix concated = a.clone();
        concated.concatRows(b);
        return concated;
    }

    @Override
    public String toString(){
        String indent = "\t\t\t\t\t\t";
        StringBuilder matrixString = new StringBuilder();
        for (int i = 0; i < this.getMatrixRowCount(); ++i) {
            matrixString.append(indent);
            for (int j = 0; j < this.getMatrixColumnCount(); ++j) {
                BigDecimal bigdec = new BigDecimal(this.getMatrixElement(i, j).toString());
                matrixString.append(String.format("%9.2f", bigdec.doubleValue()));
            }
            if(i != this.getMatrixRowCount() - 1)
                matrixString.append("\n");
        }
        return matrixString.toString();
    }
}

