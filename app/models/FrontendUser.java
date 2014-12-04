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

package models;

import components.security.Password;
import models.generic.Model;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The class representing a user of the system.
 *
 * @author Daniel Baur
 */
@Entity
public class FrontendUser extends Model {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * First name of the user.
     */
    private String firstName;

    /**
     * Last name of the user.
     */
    private String lastName;

    /**
     * The salt used for the users password.
     */
    private String salt;

    /**
     * The mail address of the user.
     */
    @Column(unique = true)
    private String mail;

    /**
     * The salted and hashed password of the user.
     */
    private String password;

    /**
     * Empty constructor. Needed by hibernate.
     */
    public FrontendUser() {
    }

    /**
     * Constructs a frontend user from the given information.
     *
     * @param firstName First Name of the user.
     * @param lastName  Last name of the user.
     * @param salt      Salt for the password.
     * @param mail      Mail address of the user.
     * @param password  Hashed and salted password of the user.
     */
    public FrontendUser(String firstName, String lastName, String salt,
                        String mail, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.salt = salt;
        this.mail = mail;
        this.password = password;
    }

    /**
     * Constructs a frontend user from the given information.
     * <p>
     * This constructor takes the clear text password, generates
     * a salt for the given password and hashes it using the generated
     * salt.
     *
     * @param firstName First name of the user.
     * @param lastName  Last name of the user.
     * @param password  Clear-text password of the user.
     * @param mail      Mail address of the user.
     */
    public FrontendUser(String firstName, String lastName, String password, String mail) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;

        byte[] generatedSalt = Password.getInstance().generateSalt();

        this.salt = Base64.encodeBase64String(generatedSalt);

        this.password = new String(Password.getInstance().hash(
                password.toCharArray(), generatedSalt));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
