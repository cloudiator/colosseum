/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package dtos;

import dtos.generic.ValidatableDto;

public class FormulaQuantifierDto extends ValidatableDto {

    private Boolean relative;
    private Double value;

    public FormulaQuantifierDto() {
        super();
    }

    public FormulaQuantifierDto(Boolean relative, Double value) {
        this.relative = relative;
        this.value = value;
    }

    @Override public void validation() {
        //TODO
    }

    public static class References {
    }

    public Boolean getRelative() {
        return relative;
    }

    public void setRelative(Boolean relative) {
        this.relative = relative;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
