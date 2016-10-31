/*
 * Copyright (c) 2014-2016 University of Ulm
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

package components.auth;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Configuration;
import play.Logger;
import util.logging.Loggers;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 05.10.16.
 */
@Singleton
class ConfigurationBasedTokenValidity implements TokenValidity {

    private final Logger.ALogger LOGGER = Loggers.of(Loggers.AUTH);

    private final long validity;
    private final static String VALIDITY_CONFIG = "colosseum.auth.token.validity";

    @Inject
    ConfigurationBasedTokenValidity(Configuration configuration) {
        final Long configuredValidity = configuration.getLong(VALIDITY_CONFIG);
        if (configuredValidity == null) {
            LOGGER.warn(String
                    .format("Configuration option %s not configured, assuming infinite token validity.",
                            VALIDITY_CONFIG));
            validity = INFINITE_VALIDITY;
        } else {
            validity = configuredValidity;
        }
        checkState(validity > 0 || validity == INFINITE_VALIDITY,
                "illegal value configured for " + VALIDITY_CONFIG);
        LOGGER.info(String.format("%s initialized with validity %s.", this, validity));
    }

    @Override
    public boolean isExpired(Token token) {
        return token.expiresAt() < deadline();
    }

    @Override
    public long validity() {
        return validity;
    }

    @Override
    public long deadline() {
        if (validity == INFINITE_VALIDITY) {
            return MAX_VALIDITY;
        }
        return System.currentTimeMillis() + validity;
    }
}
