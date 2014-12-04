package dtos;

public class FrontendUserDto {

    public String firstName;
    public String lastName;
    public String mail;
    public String password;
    public String repeat;

    public FrontendUserDto() {

    }

    public FrontendUserDto(String firstName, String lastName, String mail, String password, String repeat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.repeat = repeat;
    }
}
