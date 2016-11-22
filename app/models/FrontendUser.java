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

package models;

import com.google.common.base.MoreObjects;
import de.uniulm.omi.cloudiator.common.Password;
import models.generic.Model;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The class representing a user of the system.
 *
 * @author Daniel Baur
 */
@Entity
public class FrontendUser extends Model {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String salt;

    @Column(unique = true)
    private String mail;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "frontendUser")
    private List<ApiAccessToken> tokens;

    @ManyToMany(mappedBy = "frontendUsers")
    private List<Tenant> tenants;

    /**
     * Empty constructor for hibernate.
     */
    protected FrontendUser() {
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
    public FrontendUser(String firstName, String lastName, String salt, String mail,
                        String password) {
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

        this.password =
                new String(Password.getInstance().hash(password.toCharArray(), generatedSalt));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        checkNotNull(firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        checkNotNull(lastName);
        this.lastName = lastName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        checkNotNull(salt);
        this.salt = salt;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        checkNotNull(mail);
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        checkNotNull(password);
        this.password = password;
    }

    public List<ApiAccessToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<ApiAccessToken> tokens) {
        this.tokens = tokens;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", getId()).add("mail", getMail()).toString();
    }
}
