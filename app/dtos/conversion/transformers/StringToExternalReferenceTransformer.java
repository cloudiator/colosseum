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

package dtos.conversion.transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import models.generic.ExternalReference;

/**
 * Created by daniel on 03.06.15.
 *
 * TODO: Delete when new KeyValue approach proven to be correct in ModelWithReference entities.
 */
public class StringToExternalReferenceTransformer
    implements Transformer<List<String>, List<ExternalReference>> {

    //private final ModelService<ExternalReference> externalReferenceModelService;

    //    public StringToExternalReferenceTransformer(
    //        ModelService<ExternalReference> externalReferenceModelService) {
    //        this.externalReferenceModelService = externalReferenceModelService;
    //    }

    @Override public List<ExternalReference> transform(List<String> strings) {
        if (strings == null) {
            return new ArrayList<>();
        }

        List<ExternalReference> externalReferences = new ArrayList<>(strings.size());
        for (String s : strings) {
            ExternalReference externalReference = new ExternalReference(s, s);
            //externalReferenceModelService.save(externalReference);
            externalReferences.add(externalReference);
        }
        return externalReferences;
    }

    @Override public List<String> transformReverse(List<ExternalReference> externalReferences) {
        List<String> strings = new ArrayList<>(externalReferences.size());
        strings.addAll(externalReferences.stream().map(ExternalReference::getReference)
            .collect(Collectors.toList()));
        return strings;
    }
}
