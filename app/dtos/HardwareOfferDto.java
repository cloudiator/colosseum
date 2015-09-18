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
import dtos.validation.validators.ExpressionValidator;
import dtos.validation.validators.NotNullValidator;
import dtos.validation.validators.PredicateValidator;

public class HardwareOfferDto extends ValidatableDto {

    protected Integer numberOfCores;
    protected Long mbOfRam;
    protected Float localDiskSpace;

    public HardwareOfferDto() {
        super();
    }

    public HardwareOfferDto(Integer numberOfCores, Long mbOfRam, Float localDiskSpace) {
        this.numberOfCores = numberOfCores;
        this.mbOfRam = mbOfRam;
        this.localDiskSpace = localDiskSpace;
    }

    @Override public void validation() {
        validator(Integer.class).validate(numberOfCores).withValidator(new NotNullValidator())
            .withValidator(new ExpressionValidator(numberOfCores > 0));
        validator(Long.class).validate(mbOfRam).withValidator(new NotNullValidator())
            .withValidator(new ExpressionValidator(mbOfRam > 0));
        validator(Float.class).validate(localDiskSpace)
            .withValidator(new PredicateValidator<>(aFloat -> aFloat == null || aFloat > 0F));
    }

    public Integer getNumberOfCores() {
        return numberOfCores;
    }

    public void setNumberOfCores(Integer numberOfCores) {
        this.numberOfCores = numberOfCores;
    }

    public Long getMbOfRam() {
        return mbOfRam;
    }

    public void setMbOfRam(Long mbOfRam) {
        this.mbOfRam = mbOfRam;
    }

    public Float getLocalDiskSpace() {
        return localDiskSpace;
    }

    public void setLocalDiskSpace(Float localDiskSpace) {
        this.localDiskSpace = localDiskSpace;
    }
}
