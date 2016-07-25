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

package dtos.conversion.converters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import java.util.List;
import java.util.Map;

import dtos.ConstantMonitorDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.Transformer;
import dtos.generic.KeyValue;
import dtos.generic.KeyValues;
import models.ConstantMonitor;


@Singleton public class ConstantMonitorConverter
    extends AbstractConverter<ConstantMonitor, ConstantMonitorDto> {

    @Inject protected ConstantMonitorConverter() {
        super(ConstantMonitor.class, ConstantMonitorDto.class);
    }

    @Override public void configure() {
        binding().fromField("value").toField("value");
        binding(new TypeLiteral<List<KeyValue>>() {
        }, new TypeLiteral<Map<String, String>>() {
        }).fromField("externalReferences").toField("externalReferences").withTransformation(
                new Transformer<List<KeyValue>, Map<String, String>>() {
                    @Override public Map<String, String> transform(List<KeyValue> tags) {
                        return KeyValues.to(tags);
                    }

                    @Override public List<KeyValue> transformReverse(
                            Map<String, String> stringStringMap) {
                        return KeyValues.of(stringStringMap);
                    }
                });
    }
}
