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

package dtos.conversion.converters;

import com.google.inject.TypeLiteral;

import java.util.List;
import java.util.Map;

import dtos.TemplateOptionsDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.Transformer;
import dtos.generic.KeyValue;
import dtos.generic.KeyValues;
import models.TemplateOptions;

/**
 * Created by daniel on 06.10.15.
 */
public class TemplateOptionsConverter
    extends AbstractConverter<TemplateOptions, TemplateOptionsDto> {

    protected TemplateOptionsConverter() {
        super(TemplateOptions.class, TemplateOptionsDto.class);
    }

    @Override public void configure() {
        binding(new TypeLiteral<List<KeyValue>>() {
        }, new TypeLiteral<Map<String, String>>() {
        }).fromField("tags").toField("tags").withTransformation(
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
