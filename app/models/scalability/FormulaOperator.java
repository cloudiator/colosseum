/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package models.scalability;

/**
 * Created by Frank on 25.05.2015.
 */
public enum FormulaOperator {

    /** Kairos aggregators */
    SUM("SUM"), // ADD, PLUS
    MINUS("MINUS"),
    AVG("AVG"),
    MEDIAN("MEDIAN"),
    DEV("DEV"),
    STD("STD"),
    DERIVATIVE("DERIVATIVE"),
    DIV("DIV"),
    MULTIPLY("MULTIPLAY"),
    MIN("MIN"),
    MAX("MAX"),
    RATE("RATE"),
    SAMPLER("SAMPLER"),
    SCALE("SCALE"),
    COUNT("COUNT"),
    LEAST_SQUARES("LEAST_SQUARES"),
    PERCENTILE("PERCENTILE"),
    MODE("MODE"),
    MODULO("MODULO"),
    FIRST("FIRST"),
    LAST("LAST"),

    /** condition aggregators */
    LT("LT"),
    LTE("LTE"),
    GT("GT"),
    GTE("GTE"),
    EQ("EQ"),
    NEQ("NEQ"),


    /** event aggregators / binary */
    OR("OR"),
    AND("AND"),
    XOR("XOR"),

    /** reading information */
    IDENTITY("IDENTITY");


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
