/*
 * Copyright (c) 2014 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import components.security.Password;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


public class PasswordTest {

    /**
     * Checks the singleton pattern.
     * <p>
     * Checks that the instance method returns an instance of Password
     * and that subsequent calls to getInstance return the same instance.
     */
    @Test public void testSingleton() {
        assertThat(Password.getInstance(), instanceOf(Password.class));
        assertEquals(Password.getInstance(), Password.getInstance());
    }

    /**
     * Checks the generateSalt method.
     * <p>
     * The salt should have a length of 32 byte and two calls
     * should not generate the same salt...
     */
    @Test public void testGenerateSalt() {
        //check if length is ok
        assertEquals(Password.getInstance().generateSalt().length, 32);
        //check that two calls do not computeService the same salt
        //TODO more sophisticated testing of rng
        assertThat(Password.getInstance().generateSalt(),
            not(equalTo(Password.getInstance().generateSalt())));
    }

    /**
     * The hash method should fail if password and salt are null.
     */
    @Test(expected = NullPointerException.class) public void testHashNullPasswordAndSalt() {
        Password.getInstance().hash(null, null);
    }

    /**
     * The hash method should fail if the password is null.
     */
    @Test(expected = NullPointerException.class) public void testHashNullPassword() {
        Password.getInstance().hash(null, "hghejshgkesdwas".getBytes());
    }

    /**
     * The hash method should fail if the salt is null.
     */
    @Test(expected = NullPointerException.class) public void testHashNullSalt() {
        Password.getInstance().hash("hfwahfiwhafiowa".toCharArray(), null);
    }

    /**
     * The hash method should fail if salt and password are empty.
     */
    @Test(expected = IllegalArgumentException.class) public void testHashEmptyPasswordAndSalt() {
        Password.getInstance().hash(new char[0], new byte[0]);
    }

    /**
     * The hash method should fail if the password is emtpy.
     */
    @Test(expected = IllegalArgumentException.class) public void testHashEmptyPassword() {
        Password.getInstance().hash(new char[0], "hghejshgkesdwas".getBytes());
    }

    /**
     * The hash method should fail of the salt is empty.
     */
    @Test(expected = IllegalArgumentException.class) public void testHashEmptySalt() {
        Password.getInstance().hash("hfwahfiwhafiowa".toCharArray(), new byte[0]);
    }

    /**
     * Checks the hash method.
     * <p>
     * - The generated hash of two times the same password and salt must be equal.
     * - The hash of the same password but different salts must not be equal.
     * - The hash of the same salt but different passwords must not be equal.
     * - the hash must reach at least 512 bit.
     */
    @Test public void testHash() {
        char[] password = "test1234".toCharArray();
        char[] passwordIncorrect = "1234test".toCharArray();
        byte[] salt = Password.getInstance().generateSalt();
        byte[] saltIncorrect = Password.getInstance().generateSalt();
        float hashLength = 4 * (64 + 2 - ((64 + 2) % 3)) / 3;

        //the hash of the same password and salt must be equal
        assertArrayEquals(Password.getInstance().hash(password, salt),
            Password.getInstance().hash(password, salt));

        //the hash of the same password but different salt must not be equal
        assertThat(Password.getInstance().hash(password, salt),
            not(equalTo(Password.getInstance().hash(password, saltIncorrect))));

        //the hash of the same salt but different password must not be equal
        assertThat(Password.getInstance().hash(password, salt),
            not(equalTo(Password.getInstance().hash(passwordIncorrect, salt))));

        //the hash must reach at least 512 bit
        assertTrue(Password.getInstance().hash(password, salt).length >= hashLength);
    }

    /**
     * Checks the test method.
     * <p>
     * - If provided with the correct password and salt for the hash the test method must return true.
     * - If provided with the incorrect password for the hash the test method must return false.
     * - If provided with the incorrect salt for the hash the test method must return false.
     */
    @Test public void testCheck() {

        char[] password = "test1234".toCharArray();
        char[] passwordIncorrect = "1234test".toCharArray();
        byte[] salt = Password.getInstance().generateSalt();
        byte[] saltIncorrect = Password.getInstance().generateSalt();

        char[] hash = Password.getInstance().hash(password, salt);
        char[] hashForIncorrectPassword = Password.getInstance().hash(passwordIncorrect, salt);
        char[] hashForIncorrectSalt = Password.getInstance().hash(password, saltIncorrect);

        //correct password an hash => true
        assertTrue(Password.getInstance().check(password, hash, salt));

        //incorrect password for the hash
        assertFalse(Password.getInstance().check(password, hashForIncorrectPassword, salt));

        //incorrect salt for the hash
        assertFalse(Password.getInstance().check(password, hashForIncorrectSalt, salt));
    }


}
