package ninja.oxente.cara_suja.domains.security;

public interface IPasswordHasher {

    String encode(String plainTextPassword);

    boolean verify(String plainTextPassword, String hashedPassword);
}
