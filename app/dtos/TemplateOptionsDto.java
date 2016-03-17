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

package dtos;

import dtos.api.Dto;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by daniel on 06.10.15.
 */
public class TemplateOptionsDto implements Dto {

    public static class Tags {
        public static List<Tag> of(@Nullable Map<String, String> tags) {
            if (tags == null) {
                return Collections.emptyList();
            }
            List<Tag> ret = new ArrayList<>(tags.size());
            tags.forEach((key, value) -> ret.add(new Tag(key, value)));
            return ret;
        }

        public static Map<String, String> to(@Nullable List<Tag> tags) {
            if (tags == null) {
                return Collections.emptyMap();
            }
            Map<String, String> ret = new HashMap<>(tags.size());
            tags.forEach(tag -> ret.put(tag.key, tag.value));
            return ret;
        }
    }


    public static class Tag {

        public Tag() {

        }

        public Tag(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
