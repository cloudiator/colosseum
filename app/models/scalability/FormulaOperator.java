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
    XOR
}
