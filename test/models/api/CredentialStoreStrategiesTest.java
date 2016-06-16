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

package models.api;

import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by daniel on 17.05.16.
 */
public class CredentialStoreStrategiesTest {

    private CredentialStore nearest;
    private CredentialStore two;
    private CredentialStore three;
    private CredentialStore far;

    @Before public void setUp() throws Exception {
        this.nearest = new CredentialStoreMock(two, "nearestName", "nearestPass");
        this.two = new CredentialStoreMock(three, "twoName", "twoPass");
        this.three = new CredentialStoreMock(far, "threeName", "threPass");
        this.far = new CredentialStoreMock(null, "farName", "farPass");
    }

    @Test public void testNearestFirstEverythingDefined() throws Exception {
        assertThat(CredentialStore.CredentialStoreStrategies.NEAREST_FIRST.loginName(nearest).get(),
            equalTo("nearestName"));
        assertThat(
            CredentialStore.CredentialStoreStrategies.NEAREST_FIRST.loginPassword(nearest).get(),
            equalTo("nearestPass"));
    }

    @Test public void testNearestFirstNothingDefined() throws Exception {
        this.nearest = new CredentialStoreMock(two, null, null);
        assertThat(CredentialStore.CredentialStoreStrategies.NEAREST_FIRST.loginName(nearest).get(),
            equalTo("twoName"));
        assertThat(
            CredentialStore.CredentialStoreStrategies.NEAREST_FIRST.loginPassword(nearest).get(),
            equalTo("twoPass"));
    }

    @Test public void testNearestFirstNameDefined() throws Exception {
        this.nearest = new CredentialStoreMock(two, "nearestName", null);
        assertThat(CredentialStore.CredentialStoreStrategies.NEAREST_FIRST.loginName(nearest).get(),
            equalTo("nearestName"));
        assertThat(
            CredentialStore.CredentialStoreStrategies.NEAREST_FIRST.loginPassword(nearest).get(),
            equalTo("twoPass"));
    }

    @Test public void testNearestFirstPassDefined() throws Exception {
        this.nearest = new CredentialStoreMock(two, null, "nearestPass");
        assertThat(CredentialStore.CredentialStoreStrategies.NEAREST_FIRST.loginName(nearest).get(),
            equalTo("twoName"));
        assertThat(
            CredentialStore.CredentialStoreStrategies.NEAREST_FIRST.loginPassword(nearest).get(),
            equalTo("nearestPass"));
    }

    private static class CredentialStoreMock implements CredentialStore {

        @Nullable private final CredentialStore parent;
        @Nullable private final String loginName;
        @Nullable private final String loginPassword;

        private CredentialStoreMock(@Nullable CredentialStore parent, @Nullable String loginName,
            @Nullable String loginPassword) {
            this.parent = parent;
            this.loginName = loginName;
            this.loginPassword = loginPassword;
        }

        @Override public Optional<CredentialStore> getParent() {
            return Optional.ofNullable(parent);
        }

        @Override public Optional<String> getLoginNameCandidate() {
            return Optional.ofNullable(loginName);
        }

        @Override public Optional<String> getLoginPasswordCandidate() {
            return Optional.ofNullable(loginPassword);
        }
    }
}
