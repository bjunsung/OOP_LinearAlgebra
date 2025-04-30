package tensor;
import java.math.BigDecimal;

class ScalarImpl implements Scalar{

    private BigDecimal value;

    public ScalarImpl(String value){
        this.value = new BigDecimal(value);
    }
    public String getValue(){
        return value.toString();
    }

    public void setValue(String value)
    {
        this.value = new BigDecimal(value);
    }

    public int compareTo(Scalar other){
        return this.value.compareTo(new BigDecimal(other.getValue()));
    }

    public Scalar clone(){
        return new ScalarImpl(this.getValue());
    }

    public boolean equals(Object obj){
        if ( this == obj ) return true;
        if ( obj == null || getClass() != obj.getClass()) return false;
        ScalarImpl other = (ScalarImpl) obj;
        return this.value.equals(other.value);
    }

    public void add(Scalar other){
        this.value = this.value.add(new BigDecimal(other.getValue()));
    }

    public void multiply(Scalar other){
        this.value = this.value.multiply(new BigDecimal(other.getValue()));
    }

};
