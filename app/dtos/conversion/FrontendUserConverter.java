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

import dtos.FrontendUserDto;
import dtos.conversion.generic.AbstractConverter;
import models.FrontendUser;

/**
 * Created by daniel on 13.04.15.
 */
public class FrontendUserConverter extends AbstractConverter<FrontendUser, FrontendUserDto> {

    protected FrontendUserConverter() {
        super(FrontendUser.class, FrontendUserDto.class);
    }

    @Override public void configure() {
        builder().from("firstName").to("firstName");
        builder().from("lastName").to("lastName");
        builder().from("mail").to("mail");
    }
}
