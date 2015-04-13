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

package models.repository.impl;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import models.Cloud;
import models.Image;
import models.ImageOffer;
import models.repository.api.ImageRepository;
import models.repository.impl.generic.BaseModelRepositoryJpa;

import static models.util.JpaResultHelper.getSingleResultOrNull;

/**
 * Created by daniel on 31.10.14.
 */
public class ImageRepositoryJpa extends BaseModelRepositoryJpa<Image>
    implements ImageRepository {

    @Inject
    public ImageRepositoryJpa(TypeLiteral<Image> type) {
        super(type);
    }

    /**
     * Searches the concrete image on the given cloud and the image
     *
     * @param cloud           the cloud
     * @param imageOffer the image
     * @return the unique cloud image if any, otherwise null.
     */
    @Override public Image findByCloudAndImageOffer(final Cloud cloud, final ImageOffer imageOffer) {

        return (Image) getSingleResultOrNull(
            em().createQuery("from Image i where cloud = :cloud and image = :image")
                .setParameter("cloud", cloud).setParameter("image", imageOffer));

    }
}
