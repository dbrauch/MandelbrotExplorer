
/**
 * A class representing Complex Numbers
 */
//Todo: should this extend Number?
public class ComplexNumber extends Number{
    final double r;
    final double ri;

    public ComplexNumber(double r_input, double ri_input){
        r=r_input;
        ri=ri_input;
    }

    /**
     *
     * @return String format "a + bi"
     */
    public String toString(){
        if (r==0) return ri+"i";
        if (ri==0) return Double.toString(r);
        if (ri < 0) return r+"-"+(-ri)+"i";
        return r+"+"+ri+"i";
    }

    /**
     *
     * @return the euclidian Norm of the vector representing the complex number
     */
    public double abs(){
        return Math.sqrt((r*r)+(ri+ri));
    }

    /**
     * Adds two complex numbers
     * @param other @ComplexNumber object
     * @return a new @ComplexNumber object representing the sum of two complex numbers
     */
    public ComplexNumber add(ComplexNumber other){
        double r_tmp=this.r + other.r;
        double ri_tmp=this.ri + other.ri;
        return new ComplexNumber(r_tmp,ri_tmp);
    }

    /**
     * Subtracts two complex numbers
     * @param other @ComplexNumber object
     * @return a new @ComplexNumber object representing the difference of two complex numbers
     */
    public ComplexNumber subtract(ComplexNumber other){
        double r_tmp=this.r - other.r;
        double ri_tmp=this.ri - other.ri;
        return new ComplexNumber(r_tmp,ri_tmp);
    }

    /**
     * Multiplies two complex numbers
     * @param other @ComplexNumber object
     * @return a new @ComplexNumber object representing the product of two complex numbers
     */
    public ComplexNumber multiply(ComplexNumber other){
        double r_tmp=this.r*other.r-this.ri*other.ri;
        double ri_tmp=this.r*other.ri+this.ri*other.r;
        return new ComplexNumber(r_tmp,ri_tmp);
    }

    /**
     *
     * @param other ComplexNumber object
     * @return 0 if both are equal, -1 if the other number is  smaller and 1 otherwise
     */
    public int equals(ComplexNumber other){
        return Double.compare(this.abs(), other.abs());
    }

    @Override
    public int intValue() {
        throw new RuntimeException("Method not supported!");
    }

    @Override
    public long longValue() {
        throw new RuntimeException("Method not supported!");
    }

    @Override
    public float floatValue() {
        throw new RuntimeException("Method not supported!");
    }

    @Override
    public double doubleValue() {
        throw new RuntimeException("Method not supported!");
    }
}
