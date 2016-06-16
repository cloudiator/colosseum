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

package models.api;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Created by daniel on 16.12.15.
 */
public interface CredentialStore {

    enum CredentialStoreStrategies implements CredentialStoreStrategy {

        NEAREST_FIRST {
            @Override public Optional<String> loginName(CredentialStore startingPoint) {
                return Optional.ofNullable(traverse(startingPoint).getLoginName());
            }

            @Override public Optional<String> loginPassword(CredentialStore startingPoint) {
                return Optional.ofNullable(traverse(startingPoint).getLoginPassword());
            }

            private Credential traverse(CredentialStore credentialStore) {
                String bestLoginName = null;
                String bestLoginPassword = null;
                CredentialStore currentStore = credentialStore;
                while (currentStore != null) {
                    if (bestLoginName == null && currentStore.getLoginNameCandidate().isPresent()) {
                        bestLoginName = currentStore.getLoginNameCandidate().get();
                    }
                    if (bestLoginPassword == null && currentStore.getLoginPasswordCandidate()
                        .isPresent()) {
                        bestLoginPassword = currentStore.getLoginPasswordCandidate().get();
                    }
                    if (bestLoginName != null && bestLoginPassword != null) {
                        break;
                    }
                    currentStore = currentStore.getParent().orElse(null);
                }
                return new Credential(bestLoginName, bestLoginPassword);
            }
        };


        private static class Credential {
            @Nullable String loginName;
            @Nullable String loginPassword;

            private Credential(@Nullable String loginName, @Nullable String loginPassword) {
                this.loginName = loginName;
                this.loginPassword = loginPassword;
            }

            @Nullable public String getLoginName() {
                return loginName;
            }

            @Nullable public String getLoginPassword() {
                return loginPassword;
            }
        }
    }

    Optional<? extends CredentialStore> getParent();

    Optional<String> getLoginNameCandidate();

    Optional<String> getLoginPasswordCandidate();

    interface CredentialStoreStrategy {

        Optional<String> loginName(CredentialStore startingPoint);

        Optional<String> loginPassword(CredentialStore startingPoint);

    }
}
