package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.RegistroWizardRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.ZonaResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.model.Rol;
import restosoftteam.restosoft.restosoftv2.repository.RolRepository;
import restosoftteam.restosoft.restosoftv2.service.*;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegistroWizardServiceImpl implements RegistroWizardService {

    private final EmpresaService empresaService;
    private final UsuarioService usuarioService;
    private final RolRepository rolRepository;
    private final ZonaService zonaService;
    private final MesaService mesaService;

    @Override
    @Transactional
    public void registrarTodo(RegistroWizardRequest request, MultipartFile imagenAdmin) {

        EmpresaResponse empresaGuardada = empresaService.save(request.getEmpresa());
        Integer idEmpresa = empresaGuardada.getId();

        Rol rolAdmin = rolRepository.findByNombre("ADMINISTRADOR")
                .orElseThrow(() -> new RecursoNoEncontradoException("El rol ADMINISTRADOR no existe en la BD."));

        request.getAdmin().setIdRol(rolAdmin.getId());

        // Aquí pasamos la imagen del admin a tu método existente
        usuarioService.crearEmpleado(idEmpresa, request.getAdmin(), imagenAdmin);

        Map<Long, Integer> mapaIdsZonas = new HashMap<>();

        if (request.getZonas() != null) {
            for (var zonaRequest : request.getZonas()) {
                zonaRequest.setIdEmpresa(idEmpresa);
                ZonaResponse zonaGuardada = zonaService.crearZona(zonaRequest);
                if (zonaRequest.getIdTemporal() != null) {
                    mapaIdsZonas.put(zonaRequest.getIdTemporal(), zonaGuardada.getId());
                }
            }
        }

        if (request.getMesas() != null) {
            for (var mesaRequest : request.getMesas()) {
                mesaRequest.setIdempresa(idEmpresa);
                Integer idZonaReal = mapaIdsZonas.get(mesaRequest.getIdZona());
                if (idZonaReal != null) {
                    mesaService.crearMesaConZonaReal(mesaRequest, idZonaReal);
                } else {
                    throw new RuntimeException("La mesa está asignada a una zona que no existe");
                }
            }
        }
    }
}