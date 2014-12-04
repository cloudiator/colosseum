package dtos;

public class CredentialDto {

    public String user;
    public String secret;

    public CredentialDto() {

    }

    public CredentialDto(String user, String secret) {
        this.user = user;
        this.secret = secret;
    }
}
