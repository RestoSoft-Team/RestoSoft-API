package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.LoginRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse loginConfiguracion(LoginRequest request);

    LoginResponse loginCliente(LoginRequest request);

}