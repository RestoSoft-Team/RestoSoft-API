package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.PromocionRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PromocionResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.PromocionMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Plato;
import restosoftteam.restosoft.restosoftv2.model.Promocion;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.PlatoRepository;
import restosoftteam.restosoft.restosoftv2.repository.PromocionRepository;
import restosoftteam.restosoft.restosoftv2.service.PromocionService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromocionServiceImpl implements PromocionService {
    private final PromocionRepository promocionRepository;
    private final EmpresaRepository empresaRepository;
    private final PlatoRepository platoRepository;
    private final PromocionMapper mapper;

    @Override
    @Transactional
    public PromocionResponse save(PromocionRequest request) {
        Empresa empresa = empresaRepository.findById(request.getIdEmpresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada"));
        Promocion promocion = mapper.toEntity(request);
        promocion.setIdempresa(empresa);
        promocion.setEstado(request.getEstado() != null ? request.getEstado() : "ACTIVA");

        // Asignar platos desde el request
        if (request.getIdsPlatos() != null && !request.getIdsPlatos().isEmpty()) {
            List<Plato> platos = platoRepository.findAllById(request.getIdsPlatos());
            promocion.setPlatos(platos);
        } else {
            promocion.setPlatos(new ArrayList<>());
        }

        promocion = promocionRepository.save(promocion);
        return buildResponse(promocion);
    }

    @Override
    @Transactional
    public PromocionResponse update(Integer id, PromocionRequest request) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Promoción no encontrada"));

        mapper.updateEntityFromDto(request, promocion);
        if (request.getEstado() != null) {
            promocion.setEstado(request.getEstado());
        }

        // Actualizar platos
        if (request.getIdsPlatos() != null) {
            List<Plato> platos = platoRepository.findAllById(request.getIdsPlatos());
            promocion.setPlatos(platos);
        } else {
            promocion.setPlatos(new ArrayList<>());
        }

        promocion = promocionRepository.save(promocion);
        return buildResponse(promocion);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!promocionRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Promoción no encontrada");
        }
        promocionRepository.deleteById(id);
    }

    @Override
    public PromocionResponse findById(Integer id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Promoción no encontrada"));
        return buildResponse(promocion);
    }

    @Override
    public List<PromocionResponse> findAllByEmpresa(Integer idEmpresa) {
        return promocionRepository.findByIdempresa_Id(idEmpresa)
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PromocionResponse> findVigentes() {
        return promocionRepository.findPromocionesVigentes()
                .stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    private PromocionResponse buildResponse(Promocion p) {
        List<Integer> idsPlatos = p.getPlatos() != null
                ? p.getPlatos().stream().map(Plato::getId).collect(Collectors.toList())
                : new ArrayList<>();
        return PromocionResponse.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .valorDescuento(p.getValorDescuento())
                .fechaInicio(p.getFechaInicio())
                .fechaFin(p.getFechaFin())
                .estado(p.getEstado())
                .idsPlatos(idsPlatos)
                .build();
    }
}