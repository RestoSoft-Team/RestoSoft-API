package restosoftteam.restosoft.restosoftv2.security.exception;

public class TokenExpiradoException extends JwtAuthenticationException {

    public TokenExpiradoException(String message) {
        super(message);
    }

    public TokenExpiradoException(String message, Throwable cause) {
        super(message, cause);
    }

}