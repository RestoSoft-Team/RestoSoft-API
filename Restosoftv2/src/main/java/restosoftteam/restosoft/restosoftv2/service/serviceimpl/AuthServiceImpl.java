package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.LoginRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.LoginResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.RolResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;
import restosoftteam.restosoft.restosoftv2.enums.EstadoRegistro;
import restosoftteam.restosoft.restosoftv2.enums.RolSistema;
import restosoftteam.restosoft.restosoftv2.enums.TipoLogin;
import restosoftteam.restosoft.restosoftv2.exception.CredencialesInvalidasException;
import restosoftteam.restosoft.restosoftv2.exception.EmpresaDesactivadaException;
import restosoftteam.restosoft.restosoftv2.exception.RolNoPermitidoException;
import restosoftteam.restosoft.restosoftv2.exception.UsuarioDesactivadoException;
import restosoftteam.restosoft.restosoftv2.mapper.AuthMapper;
import restosoftteam.restosoft.restosoftv2.model.EmpresaUsuario;
import restosoftteam.restosoft.restosoftv2.model.Rol;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaUsuarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.security.jwt.JwtService;
import restosoftteam.restosoft.restosoftv2.service.AuthService;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;

    private final EmpresaUsuarioRepository empresaUsuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthMapper authMapper;

    @Override
    public LoginResponse login(LoginRequest request) {

        return autenticarEmpresa(
                request,
                TipoLogin.EMPRESA
        );

    }

    @Override
    public LoginResponse loginCliente(LoginRequest request) {

        return autenticarEmpresa(
                request,
                TipoLogin.CLIENTE
        );

    }

    @Override
    public LoginResponse loginConfiguracion(LoginRequest request) {

        return autenticarConfiguracion(
                request
        );

    }


    private LoginResponse autenticarEmpresa(LoginRequest request,
                                            TipoLogin tipoLogin) {

        EmpresaUsuario empresaUsuario = empresaUsuarioRepository
                .buscarPorEmail(request.getEmail())
                .orElseThrow(() -> {

                    log.warn(
                            "Intento de inicio de sesión con correo inexistente: {}",
                            request.getEmail()
                    );

                    return new CredencialesInvalidasException(
                            "Correo o contraseña incorrectos."
                    );

                });

        validarPassword(
                request.getPassword(),
                empresaUsuario.getUsuario().getPassword()
        );

        validarEstadoUsuario(
                empresaUsuario.getUsuario()
        );

        validarEstadoEmpresa(
                empresaUsuario
        );

        validarRolPermitido(
                empresaUsuario.getRol(),
                tipoLogin
        );

        String token = jwtService.generarToken(
                empresaUsuario.getUsuario().getId(),
                empresaUsuario.getEmpresa().getId(),
                empresaUsuario.getUsuario().getEmail(),
                empresaUsuario.getRol().getNombre()
        );

        UsuarioResponse usuarioResponse =
                authMapper.toUsuarioResponse(
                        empresaUsuario.getUsuario(),
                        empresaUsuario.getRol()
                );

        EmpresaResponse empresaResponse =
                authMapper.toEmpresaResponse(
                        empresaUsuario.getEmpresa()
                );

        RolResponse rolResponse =
                authMapper.toRolResponse(
                        empresaUsuario.getRol()
                );

        log.info(
                "Inicio de sesión exitoso. Usuario: {}, Empresa: {}",
                empresaUsuario.getUsuario().getEmail(),
                empresaUsuario.getEmpresa().getNombre()
        );

        return new LoginResponse(
                token,
                "Bearer",
                usuarioResponse,
                empresaResponse,
                rolResponse
        );

    }

    private LoginResponse autenticarConfiguracion(LoginRequest request) {

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> {

                    log.warn(
                            "Intento de inicio de sesión configuración con correo inexistente: {}",
                            request.getEmail()
                    );

                    return new CredencialesInvalidasException(
                            "Correo o contraseña incorrectos."
                    );

                });

        validarPassword(
                request.getPassword(),
                usuario.getPassword()
        );

        validarEstadoUsuario(
                usuario
        );

        validarRolPermitido(
                usuario.getRol(),
                TipoLogin.CONFIGURACION
        );

        String token = jwtService.generarToken(
                usuario.getId(),
                null,
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );

        UsuarioResponse usuarioResponse =
                authMapper.toUsuarioResponse(
                        usuario,
                        usuario.getRol()
                );

        RolResponse rolResponse =
                authMapper.toRolResponse(
                        usuario.getRol()
                );

        log.info(
                "Inicio de sesión configuración exitoso. Usuario: {}",
                usuario.getEmail()
        );

        return new LoginResponse(
                token,
                "Bearer",
                usuarioResponse,
                null,
                rolResponse
        );

    }

    private void validarPassword(String passwordIngresada,
                                 String passwordEncriptada) {

        if (!passwordEncoder.matches(passwordIngresada, passwordEncriptada)) {

            log.warn("Contraseña incorrecta.");

            throw new CredencialesInvalidasException(
                    "Correo o contraseña incorrectos."
            );

        }

    }


    private void validarEstadoUsuario(Usuario usuario) {

        if (EstadoRegistro.from(usuario.getEstado())
                != EstadoRegistro.ACTIVO) {

            log.warn(
                    "Intento de acceso de usuario desactivado: {}",
                    usuario.getEmail()
            );

            throw new UsuarioDesactivadoException(
                    "El usuario se encuentra desactivado."
            );

        }

    }


    private void validarEstadoEmpresa(EmpresaUsuario empresaUsuario) {

        if (EstadoRegistro.from(
                empresaUsuario.getEmpresa().getEstado())
                != EstadoRegistro.ACTIVO) {

            log.warn(
                    "Intento de acceso a empresa desactivada: {}",
                    empresaUsuario.getEmpresa().getNombre()
            );

            throw new EmpresaDesactivadaException(
                    "La empresa se encuentra desactivada."
            );

        }

    }


    private void validarRolPermitido(Rol rol, TipoLogin tipoLogin) {

        RolSistema rolSistema = RolSistema.from(rol.getNombre());

        switch (tipoLogin) {

            case EMPRESA -> {

                if (!rolSistema.esRolEmpresa()) {

                    log.warn(
                            "Rol {} sin permisos para el panel de empresa.",
                            rolSistema.name()
                    );

                    throw new RolNoPermitidoException(
                            "El rol no tiene permisos para acceder al panel de empresa."
                    );

                }

            }

            case CLIENTE -> {

                if (!rolSistema.esRolCliente()) {

                    log.warn(
                            "Rol {} sin permisos para el portal de clientes.",
                            rolSistema.name()
                    );

                    throw new RolNoPermitidoException(
                            "El rol no tiene permisos para acceder al portal de clientes."
                    );

                }

            }

            case CONFIGURACION -> {

                if (!rolSistema.esRolConfiguracion()) {

                    log.warn(
                            "Rol {} sin permisos para el panel de configuración.",
                            rolSistema.name()
                    );

                    throw new RolNoPermitidoException(
                            "El rol no tiene permisos para acceder al panel de configuración."
                    );

                }

            }

        }

    }

}