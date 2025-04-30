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

class MatrixImpl implements Matrix{
    List<List<Scalar>> elements;

    MatrixImpl() {
        elements = new ArrayList<>();
    }

    MatrixImpl(int rowDimension, int colDimension, String value){
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
        }
    }

    MatrixImpl(String filepath){
        elements = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){
            String line;
            while((line = reader.readLine()) != null){
                String[] tokens = line.split(",");
                List<Scalar> row = new ArrayList<>();
                for(String token : tokens){
                    row.add(new ScalarImpl(token.trim()));
                }
                elements.add(row);
            }
        } catch(FileNotFoundException e){
            System.out.println(" file not found : " + filepath);
            throw new TensorInvalidInputException("none CSV file in" + filepath);
        } catch (IOException e){
            System.out.println("FAIL to read CSV file");
            throw new TensorInvalidInputException("Error ");
        }
    }

    //2차원배열로 행렬 생성
    MatrixImpl(String[][] values){
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
    MatrixImpl(int size){
        elements = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            List<Scalar> row = new ArrayList<>();
            for (int j = 0; j < size; ++j) {
                row.add(new ScalarImpl(i == j ? "1" : "0"));
            }
            elements.add(row);
        }
    }

    public Scalar getElement(int row, int col){
        if (row < 0 || row >= elements.size())
            throw new TensorInvalidInputException("invalid row size");
        if (col < 0 || col >= elements.get(0).size())
            throw new TensorInvalidInputException("invalid column size");
        return elements.get(row).get(col);
    }
    public void setElement(int row, int col, Scalar value){
        if (row < 0 || row >= elements.size())
            throw new TensorInvalidInputException("invalid row size");
        if (col < 0 || col >= elements.get(0).size())
            throw new TensorInvalidInputException("invalid column size");
        elements.get(row).get(col).setValue(value.toString());
    }

    public int getRowCount(){
        return elements.size();
    }

    public int getColumnCount(){
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


}
