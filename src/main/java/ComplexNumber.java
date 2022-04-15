
/**
 * A class representing Complex Numbers
 */
//Todo: should this extend Number?
public class ComplexNumber extends Number {
    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal(){
        return real;
    }

    public double getImaginary(){
        return imaginary;
    }

    /**
     * @return String format "a + bi"
     */
    public String toString() {
        if (real == 0) return imaginary + "i";
        if (imaginary == 0) return Double.toString(real);
        if (imaginary < 0) return real + "-" + (-imaginary) + "i";
        return real + "+" + imaginary + "i";
    }

    /**
     * @return the euclidian Norm of the vector representing the complex number
     */
    public double abs() {
        return Math.sqrt((real * real) + (imaginary + imaginary));
    }

    /**
     * Adds two complex numbers
     *
     * @param other @ComplexNumber object
     * @return a new @ComplexNumber object representing the sum of two complex numbers
     */
    public ComplexNumber add(ComplexNumber other) {
        double r_tmp = this.real + other.real;
        double ri_tmp = this.imaginary + other.imaginary;
        return new ComplexNumber(r_tmp, ri_tmp);
    }

    /**
     * Subtracts two complex numbers
     *
     * @param other @ComplexNumber object
     * @return a new @ComplexNumber object representing the difference of two complex numbers
     */
    public ComplexNumber subtract(ComplexNumber other) {
        double r_tmp = this.real - other.real;
        double ri_tmp = this.imaginary - other.imaginary;
        return new ComplexNumber(r_tmp, ri_tmp);
    }

    /**
     * Multiplies two complex numbers
     *
     * @param other @ComplexNumber object
     * @return a new @ComplexNumber object representing the product of two complex numbers
     */
    public ComplexNumber multiply(ComplexNumber other) {
        double r_tmp = this.real * other.real - this.imaginary * other.imaginary;
        double ri_tmp = this.real * other.imaginary + this.imaginary * other.real;
        return new ComplexNumber(r_tmp, ri_tmp);
    }

    /**
     * @param other ComplexNumber object
     * @return 0 if both are equal, -1 if the other number is  smaller and 1 otherwise
     */
    public int equals(ComplexNumber other) {
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
