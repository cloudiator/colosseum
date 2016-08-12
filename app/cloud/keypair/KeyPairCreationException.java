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

package cloud.keypair;

/**
 * Created by daniel on 12.08.16.
 */
public class KeyPairCreationException extends Exception {

    public KeyPairCreationException() {
    }

    public KeyPairCreationException(String s) {
        super(s);
    }

    public KeyPairCreationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public KeyPairCreationException(Throwable throwable) {
        super(throwable);
    }

    public KeyPairCreationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
