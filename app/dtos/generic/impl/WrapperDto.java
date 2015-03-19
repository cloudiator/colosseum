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

package dtos.generic.impl;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import dtos.generic.api.Dto;

import java.util.Set;

/**
 * Created by daniel on 19.03.15.
 */
public class WrapperDto<T extends Dto> {

    private final T dto;
    private final Set<Link> link;

    private WrapperDto(T dto, String selfLink) {
        this.dto = dto;
        this.link = Links.fromSelfLink(selfLink);
    }

    public static <S extends Dto> WrapperDto<S> of(S dto, String selfLink) {
        return new WrapperDto<>(dto, selfLink);
    }

    @JsonUnwrapped public T getDto() {
        return dto;
    }

    public Set<Link> getLink() {
        return link;
    }
}
