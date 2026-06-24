package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.RolRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.RolResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.model.Rol;
import restosoftteam.restosoft.restosoftv2.repository.RolRepository;
import restosoftteam.restosoft.restosoftv2.service.RolService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public RolResponse crearRol(RolRequest request) {
        // Verificar que no exista un rol con el mismo nombre
        boolean yaExiste = rolRepository.findAll()
                .stream()
                .anyMatch(r -> r.getNombre().equalsIgnoreCase(request.getNombre()));

        if (yaExiste) {
            throw new RegistroYaExistenteException(
                    "Ya existe un rol con el nombre: " + request.getNombre());
        }

        Rol rol = new Rol();
        rol.setNombre(request.getNombre().toUpperCase());

        Rol guardado = rolRepository.save(rol);
        return construirResponse(guardado);
    }

    @Override
    public List<RolResponse> listarRoles() {
        return rolRepository.findAll()
                .stream()
                .map(this::construirResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarRol(Integer id) {
        if (!rolRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Rol no encontrado: " + id);
        }
        rolRepository.deleteById(id);
    }

    private RolResponse construirResponse(Rol rol) {
        RolResponse response = new RolResponse();
        response.setId(rol.getId());
        response.setNombre(rol.getNombre());
        return response;
    }
}