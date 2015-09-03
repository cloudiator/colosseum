package models.scalability;

/**
 * Created by Frank on 25.05.2015.
 */
public enum FormulaOperator {

    /** Kairos aggregators */
    SUM, // ADD, PLUS
    MINUS,
    AVG,
    MEDIAN,
    DEV,
    DIV,
    MIN,
    MAX,
    RATE,
    SAMPLER,
    SCALE,
    COUNT,
    LEAST_SQUARES,
    PERCENTILE,

    /** condition aggregators */
    LT,
    LTE,
    GT,
    GTE,
    EQ,


    /** event aggregators / binary */
    OR,
    AND,
    XOR;


    private final String text;

    private FormulaOperator(final String text) {
        this.text = text;
    }

    private FormulaOperator(){
        this.text = this.toString();
    }

    @Override public String toString() {
        if(text == null)
            return super.toString();
        return text;
    }
}
