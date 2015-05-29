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

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.ExpressionValidator;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullOrEmptyValidator;
import models.*;
import models.service.api.generic.ModelService;

public class ComponentHorizontalOutScalingActionDto extends ValidatableDto {

    private Long amount;
    private Long min;
    private Long max;
    private Long count;
    private Long component;

    public ComponentHorizontalOutScalingActionDto() {
        super();
    }

    public ComponentHorizontalOutScalingActionDto(Long amount, Long min, Long max, Long count, Long component) {
        this.amount = amount;
        this.min = min;
        this.max = max;
        this.count = count;
        this.component = component;
    }

    @Override public void validation() {
        validator(Long.class).validate(component)
                .withValidator(new ModelIdValidator<>(References.componentService.get()));
    }

    public static class References {
        @Inject public static Provider<ModelService<Component>> componentService;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getComponent() {
        return component;
    }

    public void setComponent(Long component) {
        this.component = component;
    }
}
