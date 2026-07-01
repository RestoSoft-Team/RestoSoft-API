package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.ConfiguracionPaginaClienteService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/configuracion")
public class ConfiguracionPaginaClienteController {
    private final ConfiguracionPaginaClienteService configuracionPaginaClienteService;

    @GetMapping
    public ResponseEntity<ConfiguracionPaginaClienteResponse> obtenerConfiguracionPorIdEmpresa(
            @IdEmpresa Integer idEmpresa) {

        ConfiguracionPaginaClienteResponse configuracionResponse =
                configuracionPaginaClienteService.findByEmpresaId(idEmpresa);

        return ResponseEntity.ok(configuracionResponse);
    }

    @GetMapping("/empresa/{nombreEmpresa}")
    public ResponseEntity<ConfiguracionPaginaClienteResponse> obtenerConfiguracionPorNombreEmpresa(
            @PathVariable String nombreEmpresa) {
        System.out.println("obtenerConfiguracionPorNombreEmpresa: " + nombreEmpresa);
        System.out.println("obtenerConfiguracionPorNombreEmpresa: " + nombreEmpresa);
        System.out.println("obtenerConfiguracionPorNombreEmpresa: " + nombreEmpresa);
        System.out.println("obtenerConfiguracionPorNombreEmpresa: " + nombreEmpresa);
        System.out.println("obtenerConfiguracionPorNombreEmpresa: " + nombreEmpresa);


        return ResponseEntity.ok(
                configuracionPaginaClienteService.findByEmpresaNombre(nombreEmpresa)
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ConfiguracionPaginaClienteResponse> guardarConfiguracion(

            @IdEmpresa Integer idEmpresa,

            @Valid
            @RequestPart("configuracion")
            ConfiguracionPaginaClienteRequest request,

            @RequestParam(value = "brandingLogo", required = false) MultipartFile brandingLogo,
            @RequestParam(value = "brandingFavicon", required = false) MultipartFile brandingFavicon,
            @RequestParam(value = "heroImagen", required = false) MultipartFile heroImagen,
            @RequestParam(value = "acercaImagen", required = false) MultipartFile acercaImagen,
            @RequestParam(value = "galeriaImagen1", required = false) MultipartFile galeriaImagen1,
            @RequestParam(value = "galeriaImagen2", required = false) MultipartFile galeriaImagen2,
            @RequestParam(value = "galeriaImagen3", required = false) MultipartFile galeriaImagen3,
            @RequestParam(value = "galeriaImagen4", required = false) MultipartFile galeriaImagen4,
            @RequestParam(value = "galeriaImagen5", required = false) MultipartFile galeriaImagen5,
            @RequestParam(value = "galeriaImagen6", required = false) MultipartFile galeriaImagen6
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(configuracionPaginaClienteService.save(
                        request,
                        idEmpresa,
                        brandingLogo,
                        brandingFavicon,
                        heroImagen,
                        acercaImagen,
                        galeriaImagen1,
                        galeriaImagen2,
                        galeriaImagen3,
                        galeriaImagen4,
                        galeriaImagen5,
                        galeriaImagen6
                ));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ConfiguracionPaginaClienteResponse> actualizarConfiguracion(

            @IdEmpresa Integer idEmpresa,

            @Valid
            @RequestPart("configuracion")
            ConfiguracionPaginaClienteRequest request,

            @RequestParam(value = "brandingLogo", required = false) MultipartFile brandingLogo,
            @RequestParam(value = "brandingFavicon", required = false) MultipartFile brandingFavicon,
            @RequestParam(value = "heroImagen", required = false) MultipartFile heroImagen,
            @RequestParam(value = "acercaImagen", required = false) MultipartFile acercaImagen,
            @RequestParam(value = "galeriaImagen1", required = false) MultipartFile galeriaImagen1,
            @RequestParam(value = "galeriaImagen2", required = false) MultipartFile galeriaImagen2,
            @RequestParam(value = "galeriaImagen3", required = false) MultipartFile galeriaImagen3,
            @RequestParam(value = "galeriaImagen4", required = false) MultipartFile galeriaImagen4,
            @RequestParam(value = "galeriaImagen5", required = false) MultipartFile galeriaImagen5,
            @RequestParam(value = "galeriaImagen6", required = false) MultipartFile galeriaImagen6
    ) {

        return ResponseEntity.ok(
                configuracionPaginaClienteService.update(
                        request,
                        idEmpresa,
                        brandingLogo,
                        brandingFavicon,
                        heroImagen,
                        acercaImagen,
                        galeriaImagen1,
                        galeriaImagen2,
                        galeriaImagen3,
                        galeriaImagen4,
                        galeriaImagen5,
                        galeriaImagen6
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarConfiguracion(
            @IdEmpresa Integer idEmpresa) {

        configuracionPaginaClienteService.deleteByEmpresaId(idEmpresa);

        return ResponseEntity.noContent().build();
    }

}
