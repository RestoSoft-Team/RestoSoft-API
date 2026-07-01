package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.ConfiguracionPaginaClienteMapper;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.repository.ConfiguracionPaginaClienteRepository;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.service.ConfiguracionPaginaClienteService;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfiguracionPaginaClienteServiceImpl implements ConfiguracionPaginaClienteService {
    private final ArchivoStorageService archivoStorageService;
    private final ConfiguracionPaginaClienteRepository configuracionPaginaClienteRepository;
    private final ConfiguracionPaginaClienteMapper configuracionPaginaClienteMapper;
    private final EmpresaRepository empresaRepository;

    @Override
    public ConfiguracionPaginaClienteResponse findByEmpresaId(Integer idEmpresa) {

        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findByEmpresa_Id(idEmpresa);
        if (configuracion != null) {
            return configuracionPaginaClienteMapper.toResponse(configuracion);
        }
        throw new RecursoNoEncontradoException("Configuracion no encontrada");

    }

    @Override
    public ConfiguracionPaginaClienteResponse findByEmpresaNombre(String nombreEmpresa) {
        try {
            ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findByEmpresaNombre(nombreEmpresa)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Configuracion no encontrada"));
            return configuracionPaginaClienteMapper.toResponse(configuracion);
        } catch (Exception e) {
            ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findByEmpresa_Nombre(nombreEmpresa)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Configuracion no encontrada"));
            return configuracionPaginaClienteMapper.toResponse(configuracion);
        }
    }

    @Override
    @Transactional
    public ConfiguracionPaginaClienteResponse save(
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
    ) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa relacionada a la configuracion no encontrada"));

        boolean existe = configuracionPaginaClienteRepository.existsByEmpresa(empresa);
        if (existe) {
            throw new RegistroYaExistenteException("La configuracion de pagina cliente ya existe");
        }

        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteMapper.toEntity(request);
        configuracion.setEmpresa(empresa);

        if (brandingLogo != null && !brandingLogo.isEmpty()) {
            try {
                configuracion.setBrandingLogo(
                        archivoStorageService.guardarImagen(brandingLogo)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen de logo");
            }
        }

        if (brandingFavicon != null && !brandingFavicon.isEmpty()) {
            try {
                configuracion.setBrandingFavicon(
                        archivoStorageService.guardarImagen(brandingFavicon)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen de favicon");
            }
        }

        if (heroImagen != null && !heroImagen.isEmpty()) {
            try {
                configuracion.setHeroImagen(
                        archivoStorageService.guardarImagen(heroImagen)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen de hero");
            }
        }

        if (acercaImagen != null && !acercaImagen.isEmpty()) {
            try {
                configuracion.setAcercaImagen(
                        archivoStorageService.guardarImagen(acercaImagen)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen de acerca");
            }
        }

        if (galeriaImagen1 != null && !galeriaImagen1.isEmpty()) {
            try {
                configuracion.setGaleriaImagen1(
                        archivoStorageService.guardarImagen(galeriaImagen1)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen 1 de galeria");
            }
        }

        if (galeriaImagen2 != null && !galeriaImagen2.isEmpty()) {
            try {
                configuracion.setGaleriaImagen2(
                        archivoStorageService.guardarImagen(galeriaImagen2)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen 2 de galeria");
            }
        }

        if (galeriaImagen3 != null && !galeriaImagen3.isEmpty()) {
            try {
                configuracion.setGaleriaImagen3(
                        archivoStorageService.guardarImagen(galeriaImagen3)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen 3 de galeria");
            }
        }

        if (galeriaImagen4 != null && !galeriaImagen4.isEmpty()) {
            try {
                configuracion.setGaleriaImagen4(
                        archivoStorageService.guardarImagen(galeriaImagen4)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen 4 de galeria");
            }
        }

        if (galeriaImagen5 != null && !galeriaImagen5.isEmpty()) {
            try {
                configuracion.setGaleriaImagen5(
                        archivoStorageService.guardarImagen(galeriaImagen5)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen 5 de galeria");
            }
        }

        if (galeriaImagen6 != null && !galeriaImagen6.isEmpty()) {
            try {
                configuracion.setGaleriaImagen6(
                        archivoStorageService.guardarImagen(galeriaImagen6)
                );
            } catch (Exception e) {
                throw new RuntimeException("Error al guardar imagen 6 de galeria");
            }
        }


        return configuracionPaginaClienteMapper.toResponse(configuracionPaginaClienteRepository.save(configuracion));

    }


    @Override
    @Transactional
    public ConfiguracionPaginaClienteResponse update(
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
    ) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa relacionada a la configuracion no encontrada"));

        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findByEmpresa(empresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La configuracion de pagina cliente que se intenta actualizar no existe."));


        configuracionPaginaClienteMapper.updateEntityFromDto(request, configuracion);
        configuracion.setEmpresa(empresa);

        if (brandingLogo != null && !brandingLogo.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(brandingLogo);

                archivoStorageService.eliminarImagen(
                        configuracion.getBrandingLogo()
                );

                configuracion.setBrandingLogo(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen de branding logo", e);
            }
        }

        if (brandingFavicon != null && !brandingFavicon.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(brandingFavicon);

                archivoStorageService.eliminarImagen(
                        configuracion.getBrandingFavicon()
                );

                configuracion.setBrandingFavicon(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen de branding favicon", e);
            }
        }

        if (heroImagen != null && !heroImagen.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(heroImagen);

                archivoStorageService.eliminarImagen(
                        configuracion.getHeroImagen()
                );

                configuracion.setHeroImagen(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen del hero", e);
            }
        }

        if (acercaImagen != null && !acercaImagen.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(acercaImagen);

                archivoStorageService.eliminarImagen(
                        configuracion.getAcercaImagen()
                );

                configuracion.setAcercaImagen(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen de acerca", e);
            }
        }

        if (galeriaImagen1 != null && !galeriaImagen1.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(galeriaImagen1);

                archivoStorageService.eliminarImagen(
                        configuracion.getGaleriaImagen1()
                );

                configuracion.setGaleriaImagen1(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen 1 de galería", e);
            }
        }

        if (galeriaImagen2 != null && !galeriaImagen2.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(galeriaImagen2);

                archivoStorageService.eliminarImagen(
                        configuracion.getGaleriaImagen2()
                );

                configuracion.setGaleriaImagen2(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen 2 de galería", e);
            }
        }

        if (galeriaImagen3 != null && !galeriaImagen3.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(galeriaImagen3);

                archivoStorageService.eliminarImagen(
                        configuracion.getGaleriaImagen3()
                );

                configuracion.setGaleriaImagen3(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen 3 de galería", e);
            }
        }

        if (galeriaImagen4 != null && !galeriaImagen4.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(galeriaImagen4);

                archivoStorageService.eliminarImagen(
                        configuracion.getGaleriaImagen4()
                );

                configuracion.setGaleriaImagen4(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen 4 de galería", e);
            }
        }

        if (galeriaImagen5 != null && !galeriaImagen5.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(galeriaImagen5);

                archivoStorageService.eliminarImagen(
                        configuracion.getGaleriaImagen5()
                );

                configuracion.setGaleriaImagen5(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen 5 de galería", e);
            }
        }

        if (galeriaImagen6 != null && !galeriaImagen6.isEmpty()) {
            try {
                String nuevaImagen = archivoStorageService.guardarImagen(galeriaImagen6);

                archivoStorageService.eliminarImagen(
                        configuracion.getGaleriaImagen6()
                );

                configuracion.setGaleriaImagen6(nuevaImagen);

            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar imagen 6 de galería", e);
            }
        }

        return configuracionPaginaClienteMapper.toResponse(configuracionPaginaClienteRepository.save(configuracion));

    }


    @Override
    @Transactional
    public void deleteByEmpresaId(Integer idEmpresa) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa relacionada no encontrada"));

        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findByEmpresa(empresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Configuracion a eliminar no encontrada  no encontrada"));

        archivoStorageService.eliminarImagen(configuracion.getBrandingLogo());
        archivoStorageService.eliminarImagen(configuracion.getBrandingFavicon());
        archivoStorageService.eliminarImagen(configuracion.getHeroImagen());
        archivoStorageService.eliminarImagen(configuracion.getAcercaImagen());
        archivoStorageService.eliminarImagen(configuracion.getGaleriaImagen1());
        archivoStorageService.eliminarImagen(configuracion.getGaleriaImagen2());
        archivoStorageService.eliminarImagen(configuracion.getGaleriaImagen3());
        archivoStorageService.eliminarImagen(configuracion.getGaleriaImagen4());
        archivoStorageService.eliminarImagen(configuracion.getGaleriaImagen5());
        archivoStorageService.eliminarImagen(configuracion.getGaleriaImagen6());

        configuracionPaginaClienteRepository.delete(configuracion);

    }


}
