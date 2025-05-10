package tensor;
import java.math.BigDecimal;
import java.math.RoundingMode;

class ScalarImpl implements Scalar{

    private BigDecimal value;

    public ScalarImpl(BigDecimal other) {
        this(other.toString());
    }

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

    @Override
    public Scalar clone(){
        try{
            ScalarImpl cloned = (ScalarImpl) super.clone();
            cloned.setValue(this.getValue());
            return cloned;
        }catch(CloneNotSupportedException e){
            throw new AssertionError();
        }
    }

    public boolean equals(Object obj){
        if ( super.equals(obj) ) return true;
        if ( obj == null || getClass() != obj.getClass()) return false;
        ScalarImpl other = (ScalarImpl) obj;
        return this.value.equals(other.value);
    }

    public void add(Scalar other){
        this.value = this.value.add(new BigDecimal(other.getValue()));
    }

    public void add(BigDecimal other){
        add(new ScalarImpl(other.toString()));
    }

    public void multiply(Scalar other){
        this.value = this.value.multiply(new BigDecimal(other.getValue()));
    }


    @Override
    public String toString(){
        BigDecimal bigdec = new BigDecimal(this.value.toString());
        bigdec = bigdec.setScale(2, RoundingMode.HALF_UP);
        return bigdec.toString();
    }

};
