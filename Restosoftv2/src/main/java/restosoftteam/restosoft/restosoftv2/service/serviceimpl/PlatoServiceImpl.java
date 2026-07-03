package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.PlatoMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Plato;
import restosoftteam.restosoft.restosoftv2.model.PlatoCategoria;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.PlatoCategoriaRepository;
import restosoftteam.restosoft.restosoftv2.repository.PlatoRepository;
import restosoftteam.restosoft.restosoftv2.service.PlatoService;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlatoServiceImpl implements PlatoService {
    private final PlatoRepository platoRepository;
    private final PlatoMapper platoMapper;
    private final EmpresaRepository empresaRepository;
    private final PlatoCategoriaRepository platoCategoriaRepository;
    private final ArchivoStorageService archivoStorageService;

    @Override
    public PlatoResponse findById(Integer id) {
        return platoMapper.toResponse(platoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El plato no se ha encontrado.")));
    }

    @Override
    public List<PlatoResponse> findAllByEmpresaId(Integer idEmpresa) {
        return platoRepository.findAllConDetallesByEmpresa_Id(idEmpresa)
                .stream().map(platoMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public PlatoResponse update(PlatoRequest request, Integer id, MultipartFile imagen, Integer idEmpresa) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El plato que se intenta actualizar no existe."));

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada no existe."));

        PlatoCategoria platoCategoria = platoCategoriaRepository.findById(request.getIdcategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("La categoria del plato relacionada no existe."));

        platoMapper.updateEntityFromDto(request, plato);
        plato.setEmpresa(empresa);
        plato.setCategoria(platoCategoria);

        if (imagen != null && !imagen.isEmpty()) {
            try {
                if (plato.getImagenUrl() != null) {
                    archivoStorageService.eliminarImagen(plato.getImagenUrl());
                }
                String nuevaUrl = archivoStorageService.guardarImagen(imagen);
                plato.setImagenUrl(nuevaUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen del plato", e);
            }
        }

        return platoMapper.toResponse(platoRepository.save(plato));
    }

    @Override
    @Transactional
    public PlatoResponse save(PlatoRequest request, Integer idEmpresa, MultipartFile imagen) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada no existe."));

        PlatoCategoria platoCategoria = platoCategoriaRepository.findById(request.getIdcategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("La categoria del plato relacionada no existe."));

        Plato plato = platoMapper.toEntity(request);
        plato.setEmpresa(empresa);
        plato.setCategoria(platoCategoria);

        String urlImagen = "default-plate.png";
        if (imagen != null && !imagen.isEmpty()) {
            try {
                urlImagen = archivoStorageService.guardarImagen(imagen);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen del plato", e);
            }
        }
        plato.setImagenUrl(urlImagen);

        return platoMapper.toResponse(platoRepository.save(plato));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El plato que se intenta eliminar no existe."));

        if (plato.getImagenUrl() != null && !plato.getImagenUrl().equals("default-plate.png")) {
            archivoStorageService.eliminarImagen(plato.getImagenUrl());
        }

        platoRepository.delete(plato);
    }
}