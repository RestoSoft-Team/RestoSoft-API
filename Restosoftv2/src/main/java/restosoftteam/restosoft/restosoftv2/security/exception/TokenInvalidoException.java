package restosoftteam.restosoft.restosoftv2.security.exception;

public class TokenInvalidoException extends JwtAuthenticationException {

    public TokenInvalidoException(String message) {
        super(message);
    }

    public TokenInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

}