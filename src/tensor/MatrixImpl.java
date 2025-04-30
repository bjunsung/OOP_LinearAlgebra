package tensor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

class MatrixImpl implements Matrix {
    List<List<Scalar>> elements;

    MatrixImpl() {
        elements = new ArrayList<>();
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
                        new BigDecimal(Math.random()).multiply(maxValue.subtract(minValue))
                );
                row.add(new ScalarImpl(randomValue.toString()));
            }
            elements.add(row);
        }
    }

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
            System.out.println(" file not found : " + filepath);
            throw new TensorInvalidInputException("none CSV file in" + filepath);
        } catch (IOException e) {
            System.out.println("FAIL to read CSV file");
            throw new TensorInvalidInputException("Error ");
        }
    }

    //2차원배열로 행렬 생성
    MatrixImpl(String[][] values) {
        elements = new ArrayList<>();
        for (String[] value : values) {
            List<Scalar> row = new ArrayList<>();
            for (String s : value) {
                row.add(new ScalarImpl(s));
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
                row.add(new ScalarImpl(i == j ? "1" : "0"));
            }
            elements.add(row);
        }
    }

    public Scalar getElement(int row, int col) {
        if (row < 0 || row >= elements.size())
            throw new TensorInvalidInputException("invalid row size");
        if (col < 0 || col >= elements.get(0).size())
            throw new TensorInvalidInputException("invalid column size");
        return elements.get(row).get(col);
    }

    public void setElement(int row, int col, Scalar value) {
        if (row < 0 || row >= elements.size())
            throw new TensorInvalidInputException("invalid row size");
        if (col < 0 || col >= elements.get(0).size())
            throw new TensorInvalidInputException("invalid column size");
        elements.get(row).get(col).setValue(value.toString());
    }

    public int getRowCount() {
        return elements.size();
    }

    public int getColumnCount() {
        return elements.getFirst().size();
    }

    @Override
    public Matrix clone() {
        List<List<Scalar>> clonedElements = new ArrayList<>();

        for (List<Scalar> row : this.elements) {
            List<Scalar> clonedRow = new ArrayList<>();
            for (Scalar scalar : row) {
                clonedRow.add(scalar.clone());
            }
            clonedElements.add(clonedRow);
        }

        MatrixImpl clonedMatrix = new MatrixImpl();
        clonedMatrix.elements = clonedElements;
        return clonedMatrix;
    }

    public void add(Matrix matrix) {
        if (matrix.getRowCount() != this.getRowCount() || matrix.getColumnCount() != this.getColumnCount()) {
            throw new TensorInvalidInputException("invalid matrix size");
        }
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getColumnCount(); j++) {
                this.getElement(i, j).add(matrix.getElement(i, j));
            }
        }
    }

    public void multiplyRight(Matrix matrix) {
        if (this.getColumnCount() != matrix.getRowCount()) {
            throw new TensorInvalidInputException("invalid matrix multiply size"
                    + "(" + this.getRowCount() + "x" + this.getColumnCount() + ")x(" + matrix.getRowCount() + "x" + matrix.getColumnCount() + ")");
        }
        List<List<Scalar>> multipliedElements = new ArrayList<>();
        for (int i = 0; i < this.getRowCount(); i++) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < matrix.getColumnCount(); j++) {
                Scalar scalar = new ScalarImpl("0.0");
                for (int k = 0; k < this.getColumnCount(); k++) {
                    scalar.add(new ScalarImpl(new BigDecimal(this.getElement(i, k).getValue()).multiply(new BigDecimal(matrix.getElement(k, j).getValue())).toString()));
                }
                row.add(scalar);
            }
            multipliedElements.add(i, row);
            this.elements = multipliedElements;
        }
    }

    public void multiplyLeft(Matrix matrix) {
        if (matrix.getColumnCount() != this.getRowCount()) {
            throw new TensorInvalidInputException("invalid matrix multiply size"
                    + "(" + matrix.getRowCount() + "x" + matrix.getColumnCount() + ")x(" + this.getRowCount() + "x" + this.getColumnCount() + ")");
        }
        List<List<Scalar>> multipliedElements = new ArrayList<>();
        for (int i = 0; i < matrix.getRowCount(); i++) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < this.getColumnCount(); j++) {
                Scalar scalar = new ScalarImpl("0.0");
                for (int k = 0; k < this.getRowCount(); k++) {
                    scalar.add(new ScalarImpl(new BigDecimal(matrix.getElement(i, k).getValue()).multiply(new BigDecimal(this.getElement(k, j).getValue())).toString()));
                }
                row.add(scalar);
            }
            multipliedElements.add(i, row);
            this.elements = multipliedElements;
        }
    }

    //no.32 행렬은 다른 행렬과 가로로 합쳐질 수 있다.
    public Matrix concatColumns(Matrix other) {
        if (this.getRowCount() != other.getRowCount())
            throw new TensorSizeMismatchException("matrix row size is different. a: " + this.getRowCount() + " b: " + other.getRowCount());
        for (int i = 0; i < this.getRowCount(); ++i) {
            List<Scalar> row = this.elements.get(i);
            for (int j = 0; j < other.getColumnCount(); ++j) {
                row.add(other.getElement(i, j).clone());
            }
        }
        return this;
    }

    //no.33 행렬은 다른 행렬과 세로로 합쳐질 수 있다.
    public Matrix concatRows(Matrix other) {
        if (this.getColumnCount() != other.getColumnCount())
            throw new TensorSizeMismatchException("matrix column size is different. a: " + this.getColumnCount() + " b: " + other.getColumnCount());
        for (int i = 0; i < other.getRowCount(); ++i) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < other.getColumnCount(); ++j)
                row.add(other.getElement(i, j).clone());
            this.elements.add(row);
        }
        return this;
    }

    //no.34. 행렬은 특정 행을 벡터 형태로 추출해 줄 수 있다.
    public Vector extractRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= this.getRowCount())
            throw new TensorInvalidInputException("row index out of bounds: " + rowIndex);
        VectorImpl vector = new VectorImpl();
        for (int i = 0; i < this.getColumnCount(); ++i) {
            vector.elements.add(this.getElement(rowIndex, i).clone());
        }
        return vector;
    }

    //no.35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다.
    public Vector extractColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= this.getColumnCount())
            throw new TensorInvalidInputException("column index out of bounds: " + columnIndex);
        VectorImpl vector = new VectorImpl();
        for (int i = 0; i < this.getRowCount(); ++i) {
            vector.elements.add(this.getElement(i, columnIndex).clone());
        }
        return vector;
    }

    //no.36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
    public Matrix subMatrix(int startRow, int endRow, int startCol, int endCol) {
        if (startRow > endRow || startCol > endCol ||
                startRow < 0 || endRow >= this.getRowCount() ||
                startCol < 0 || endCol >= this.getColumnCount())
            throw new TensorSizeMismatchException("invalid submatrix bounds");
        List<List<Scalar>> subMatrixElements = new ArrayList<>();
        for (int i = startRow; i < endRow; ++i) {
            List<Scalar> row = new ArrayList<>();
            for (int j = startCol; j < endCol; ++j) {
                row.add(this.getElement(i, j).clone());
            }
            subMatrixElements.add(row);
        }
        MatrixImpl subMatrix = new MatrixImpl();
        subMatrix.elements = subMatrixElements;
        return subMatrix;
    }

    //no.37. 행렬은 특정 범위의 부분 행렬을 추출해줄 수 있다. (minor)
    public Matrix minor(int rowToExclude, int colToExclude) {
        if (rowToExclude < 0 || colToExclude < 0 ||
                rowToExclude > this.getRowCount() || colToExclude > this.getColumnCount())
            throw new TensorSizeMismatchException("exclude row(col) out of range");
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
        MatrixImpl minor = new MatrixImpl();
        minor.elements = subMatrixElements;
        return minor;
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
        MatrixImpl transpose = new MatrixImpl();
        transpose.elements = transposeElements;
        return transpose;
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
        Scalar zero = new ScalarImpl("0.0");
        for (int i = 0; i < this.getRowCount(); ++i)
            for (int j = 0; j < i; ++j)
                if (!this.getElement(i, j).equals(zero))
                    return false;
        return true;
    }

    //no.42. 행렬은 자신이 하삼각 행렬인지 여부를 판별해 줄 수 있다.
    public boolean isLowerTriangular() {
        if (!this.isSquare()) return false;
        Scalar zero = new ScalarImpl("0.0");
        for (int i = 0; i < this.getRowCount(); ++i)
            for (int j = i + 1; j < this.getRowCount(); ++j)
                if (!this.getElement(i, j).equals(zero))
                    return false;
        return true;
    }

    //no.43. 행렬은 자신이 단위행렬인지 여부를 판별해 줄 수 있다.
    public boolean isIdentityMatrix() {
        if (!this.isSquare()) return false;
        Scalar zero = new ScalarImpl("0.0");
        Scalar basic = new ScalarImpl("1.0");
        for (int i = 0; i < this.getRowCount(); ++i)
            for (int j = 0; j < this.getColumnCount(); ++j) {
                if ((i != j) && !this.getElement(i, j).equals(zero)) return false;
                if ((i == j) && !this.getElement(i, j).equals(basic)) return false;
            }
        return true;
    }

    //no.44. 행렬은 자신이 영행렬인지 여부를 판별해 줄 수 있다.
    public boolean isZeroMatrix() {
        Scalar zero = new ScalarImpl("0.0");
        for (int i = 0; i < this.getRowCount(); ++i)
            for (int j = 0; j < this.getColumnCount(); ++j)
                if (!this.getElement(i, j).equals(zero)) return false;
        return true;
    }

    //no.45. 행렬은 특정 두 행의 위치를 맞교환할 수 있다.
    public void swapRows(int row1, int row2) {
        if (row1 < 0 || row2 < 0 || row1 >= this.getRowCount() || row2 >= this.getRowCount())
            throw new TensorInvalidInputException("invalid row index");
        if (row1 == row2) return;
        List<Scalar> rowElements1 = this.elements.get(row1);
        List<Scalar> rowElements2 = this.elements.get(row2);
        this.elements.set(row1, rowElements2);
        this.elements.set(row2, rowElements1);
    }

    //no.46. 행렬은 특정 두 열의 위치를 맞교환할 수 있다.
    public void swapColumns(int col1, int col2) {
        if (col1 < 0 || col2 < 0 || col1 >= this.getColumnCount() || col2 >= this.getColumnCount())
            throw new TensorInvalidInputException("invalid column index");
        if (col1 == col2) return;
        for (int i = 0; i < this.getRowCount(); ++i) {
            Scalar temp = this.getElement(i, col1).clone();
            this.elements.get(i).set(col1, this.getElement(i, col2).clone());
            this.elements.get(i).set(col2, temp);
        }
    }

    //no.47. 행렬은 특정 행에 스칼라배 할 수 있다.
    public void scaleRow(int row, Scalar scalar) {
        if (row < 0 || row >= this.getRowCount())
            throw new TensorInvalidInputException("invalid row index");
        if (scalar.equals(new ScalarImpl("1.0"))) return;
        for (int i = 0; i < this.getColumnCount(); ++i)
            this.getElement(row, i).multiply(scalar);
    }

    //no.48. 행렬은 특정 열에 스칼라배 할 수 있다.
    public void scaleColumn(int col, Scalar scalar) {
        if (col < 0 || col >= this.getColumnCount())
            throw new TensorInvalidInputException("invalid column index");
        if (scalar.equals(new ScalarImpl("1.0"))) return;
        for (int i = 0; i < this.getRowCount(); ++i)
            this.getElement(i, col).multiply(scalar);
    }

    //no.49. 행렬은 특정 행에 다른 행의 스칼라배를 더할 수 있다.
    public void addScaledRow(int targetRow, int sourceRow, Scalar scalar) {
        if (targetRow < 0 || targetRow >= this.getRowCount()
                || sourceRow < 0 || sourceRow >= this.getRowCount())
            throw new TensorInvalidInputException("invalid row index");
        if (targetRow == sourceRow)
            throw new TensorInvalidInputException("target row is equal to source row");
        Scalar zero = new ScalarImpl("0.0");
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
        if (targetCol < 0 || targetCol >= this.getColumnCount()
                || sourceCol < 0 || sourceCol >= this.getColumnCount())
            throw new TensorInvalidInputException("invalid column index");
        if (targetCol == sourceCol)
            throw new TensorInvalidInputException("target column is equal to source column");
        Scalar zero = new ScalarImpl("0.0");
        if (scalar.equals(zero)) return;
        for (int i = 0; i < this.getRowCount(); ++i) {
            if (this.getElement(i, sourceCol).equals(zero)) continue;
            Scalar temp = this.getElement(i, sourceCol).clone();
            temp.multiply(scalar);
            this.getElement(i, sourceCol).add(temp);
        }
    }

    //no.51. 행렬은 자신으로부터 RREF 행렬을 구해서 반환 해 줄 수 있다
    public Matrix toRref() {
        MatrixImpl rrefMatrix = (MatrixImpl) this.clone();
        int rowCount = rrefMatrix.getRowCount();
        int colCount = rrefMatrix.getColumnCount();
        int lead = 0;
        for (int r = 0; r < rowCount; r++) {
            if (lead >= colCount)
                break;
            int i = r;
            while (i < rowCount && rrefMatrix.getElement(i, lead).compareTo(new ScalarImpl("0.0")) == 0) {
                i++;
            }
            if (i == rowCount) {
                lead++;
                r--; // 다시 같은 r행에서 시도
                continue;
            }
            // 1. pivot row i → r로 스왑
            if (i != r) {
                rrefMatrix.swapRows(i, r);
            }
            // 2. 선행 원소를 1로 만들기
            Scalar pivot = rrefMatrix.getElement(r, lead);
            Scalar reciprocal = new ScalarImpl("1.0");
            reciprocal = new ScalarImpl(new BigDecimal("1.0").divide(new BigDecimal(pivot.getValue()), BigDecimal.ROUND_HALF_UP).toString());
            rrefMatrix.scaleRow(r, reciprocal);
            // 3. 현재 열의 다른 행에 대해 0 만들기
            for (int j = 0; j < rowCount; j++) {
                if (j != r) {
                    Scalar factor = rrefMatrix.getElement(j, lead).clone();
                    factor.multiply(new ScalarImpl("-1.0"));
                    rrefMatrix.addScaledRow(j, r, factor);
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
        Scalar one = new ScalarImpl("1.0");
        Scalar zero = new ScalarImpl("0.0");
        for (int i = 0; i < this.getRowCount(); ++i) {
            int rowLead = -1;
            // 1. 이 행의 선행 원소 찾기
            for (int j = 0; j < this.getColumnCount(); ++j) {
                if (!this.getElement(i, j).equals(zero)) {
                    rowLead = j;
                    break;
                }
            }
            // 2. 전부 0인 행이면, 그 아래는 모두 0이어야 함
            if (rowLead == -1) {
                for (int k = i + 1; k < this.getRowCount(); ++k) {
                    for (int j = 0; j < this.getColumnCount(); ++j) {
                        if (!this.getElement(k, j).equals(zero)) {
                            return false; // 0행 아래에 0이 아닌 값 발견 → RREF 아님
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
            // 5. 선행 원소의 열 위치는 점점 오른쪽으로만 가야 함
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
            return this.getElement(0, 0).clone(); // 1x1 행렬 → 자기 자신
        }
        if (size == 2) {
            Scalar a = this.getElement(0, 0).clone();
            Scalar b = this.getElement(0, 1).clone();
            Scalar c = this.getElement(1, 0).clone();
            Scalar d = this.getElement(1, 1).clone();
            a.multiply(d); // ad
            c.multiply(b); // cb
            a.add(new ScalarImpl("-" + c.getValue())); // ad - bc
            return a;
        }
        Scalar det = new ScalarImpl("0.0");
        for (int j = 0; j < size; ++j) {
            Scalar cofactor = this.getElement(0, j).clone();
            Matrix minor = this.minor(0, j);
            Scalar subDet = minor.determinant();
            cofactor.multiply(subDet);
            if (j % 2 == 1) {
                cofactor.multiply(new ScalarImpl("-1"));
            }
            det.add(cofactor);
        }
        return det;
    }

    //no.54. 행렬은 자신의 역행렬을 구해줄 수 있다.
    public Matrix inverse() {
        if (!this.isSquare())
            throw new NonSquareMatrixException("Non-square matrix does not have an inverse.");
        MatrixImpl rrefMatrix = (MatrixImpl) this.clone();
        int size = rrefMatrix.getRowCount();
        MatrixImpl inverseMatrix = new MatrixImpl(size); // 단위행렬 생성
        int lead = 0;
        for (int r = 0; r < size; r++) {
            if (lead >= size)
                break;
            int i = r;
            while (i < size && rrefMatrix.getElement(i, lead).compareTo(new ScalarImpl("0.0")) == 0) {
                i++;
            }
            if (i == size) {
                lead++;
                r--;
                continue;
            }
            // 1. pivot row i ↔ r로 교환
            if (i != r) {
                rrefMatrix.swapRows(i, r);
                inverseMatrix.swapRows(i, r);
            }
            // 2. pivot을 1로 만들기 (두 행렬에 동시에 적용)
            Scalar pivot = rrefMatrix.getElement(r, lead).clone();
            Scalar reciprocal = new ScalarImpl(
                    new BigDecimal("1.0").divide(new BigDecimal(pivot.getValue()), BigDecimal.ROUND_HALF_UP).toString()
            );
            rrefMatrix.scaleRow(r, reciprocal);
            inverseMatrix.scaleRow(r, reciprocal);
            // 3. pivot 열의 다른 행을 0으로 만들기 (두 행렬에 동시에 적용)
            for (int j = 0; j < size; j++) {
                if (j == r) continue;
                Scalar factor = rrefMatrix.getElement(j, lead).clone();
                factor.multiply(new ScalarImpl("-1.0"));
                rrefMatrix.addScaledRow(j, r, factor);
                inverseMatrix.addScaledRow(j, r, factor);
            }
            lead++;
        }
        if (!rrefMatrix.isIdentityMatrix()) {
            throw new TensorArithmeticException("Matrix is singular and cannot be inverted.");
        }
        return inverseMatrix;
    }

}

