package dtos.builders;

import dtos.FrontendUserDto;

public class FrontendUserDtoBuilder {
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private String repeat;

    public FrontendUserDtoBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public FrontendUserDtoBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public FrontendUserDtoBuilder mail(String mail) {
        this.mail = mail;
        return this;
    }

    public FrontendUserDtoBuilder password(String password) {
        this.password = password;
        return this;
    }

    public FrontendUserDtoBuilder repeat(String repeat) {
        this.repeat = repeat;
        return this;
    }

    public FrontendUserDto build() {
        return new FrontendUserDto(firstName, lastName, mail, password, repeat);
    }
}