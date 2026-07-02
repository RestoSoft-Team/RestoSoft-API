package restosoftteam.restosoft.restosoftv2.service;

import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;

public interface ConfiguracionPaginaClienteService {

    ConfiguracionPaginaClienteResponse findByEmpresaId(Integer idEmpresa);

    ConfiguracionPaginaClienteResponse save(
            ConfiguracionPaginaClienteRequest request,
            Integer idEmpresa,
            MultipartFile brandingLogo,
            MultipartFile brandingFavicon,
            MultipartFile heroImagen,
            MultipartFile acercaImagen,
            MultipartFile galeriaImagen1,
            MultipartFile galeriaImagen2,
            MultipartFile galeriaImagen3,
            MultipartFile galeriaImagen4,
            MultipartFile galeriaImagen5,
            MultipartFile galeriaImagen6
    );

    ConfiguracionPaginaClienteResponse update(
            ConfiguracionPaginaClienteRequest request,
            Integer idEmpresa,
            MultipartFile brandingLogo,
            MultipartFile brandingFavicon,
            MultipartFile heroImagen,
            MultipartFile acercaImagen,
            MultipartFile galeriaImagen1,
            MultipartFile galeriaImagen2,
            MultipartFile galeriaImagen3,
            MultipartFile galeriaImagen4,
            MultipartFile galeriaImagen5,
            MultipartFile galeriaImagen6
    );

    void deleteByEmpresaId(Integer idEmpresa);

    ConfiguracionPaginaClienteResponse findByEmpresaNombre(String nombreEmpresa);
}