/*
 * Copyright (c) 2014-2016 University of Ulm
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

package dtos.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by Frank on 18.03.2016.
 */
public class KeyValues {
    public static List<KeyValue> of(@Nullable Map<String, String> tags) {
        if (tags == null) {
            return Collections.emptyList();
        }
        List<KeyValue> ret = new ArrayList<>(tags.size());
        tags.forEach((key, value) -> ret.add(new KeyValue(key, value)));
        return ret;
    }

    public static Map<String, String> to(@Nullable List<KeyValue> keyValues) {
        if (keyValues == null) {
            return Collections.emptyMap();
        }
        Map<String, String> ret = new HashMap<>(keyValues.size());
        keyValues.forEach(keyValue -> ret.put(keyValue.getKey(), keyValue.getValue()));
        return ret;
    }
}
