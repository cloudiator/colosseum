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

package dtos.conversion;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.LinkedList;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 17.03.15.
 */
public class ConversionBindings implements ConversionBinding {

    private boolean isBuild = false;
    private Collection<FieldBindingBuilder> builders;
    private Collection<ConversionBinding> conversionBindings;

    public ConversionBindings() {
        this.builders = new LinkedList<>();
    }

    public FieldBindingBuilder builder() {
        checkState(!isBuild);
        FieldBindingBuilder fieldBindingBuilder = new FieldBindingBuilder();
        this.builders.add(fieldBindingBuilder);
        return fieldBindingBuilder;
    }

    private void build() {
        final ImmutableList.Builder<ConversionBinding> builder = ImmutableList.<ConversionBinding>builder();
        for (FieldBindingBuilder fieldBindingBuilder : builders) {
            builder.add(fieldBindingBuilder.build());
        }
        this.conversionBindings = builder.build();
        isBuild = true;
    }

    @Override public void bind(Object from, Object to) {
        build();
        for (ConversionBinding conversionBinding : conversionBindings) {
            conversionBinding.bind(from, to);
        }
    }

    @Override public void bindReverse(Object from, Object to) {
        build();
        for (ConversionBinding conversionBinding : conversionBindings) {
            conversionBinding.bindReverse(from, to);
        }
    }
}



