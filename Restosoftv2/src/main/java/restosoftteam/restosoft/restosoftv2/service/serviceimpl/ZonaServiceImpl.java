package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.ZonaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ZonaResponse;
import restosoftteam.restosoft.restosoftv2.mapper.ZonaMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Zona;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ZonaRespository;
import restosoftteam.restosoft.restosoftv2.service.ZonaService;

@Service
@RequiredArgsConstructor
public class ZonaServiceImpl implements ZonaService {

    private final ZonaRespository  zonaRespository;
    private final ZonaMapper zonaMapper;
    private final EmpresaRepository empresaRepository;

    @Override
    public ZonaResponse crearZona(ZonaRequest zonaRequest) {
        Zona zona = zonaMapper.toEntity(zonaRequest);

        Empresa empresa = empresaRepository.findById(zonaRequest.getIdEmpresa()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        zona.setIdEmpresa(empresa);
        Zona guardar =  zonaRespository.save(zona);
        return zonaMapper.toResponse(guardar);
    }

    @Override
    public ZonaResponse actualizarZona(ZonaRequest zonaRequest) {

        Zona zonaExistente = zonaRespository.findById(zonaRequest.getId()).orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        zonaExistente.setNombreZona(zonaRequest.getNombreZona());
        zonaExistente.setPlanoUrl(zonaRequest.getUrlZona());
        zonaExistente.setEstado(zonaRequest.getEstado());

        return zonaMapper.toResponse(zonaRespository.save(zonaExistente));
    }

    @Override
    public ZonaResponse eliminarZona(Integer id, Integer idEmpresa) {
        Zona zona = zonaRespository.findByIdAndIdEmpresa_Id(id, idEmpresa).orElseThrow(() -> new RuntimeException("Zona no encontrada o no pertenece a la empresa"));

        zonaRespository.delete(zona);

        return zonaMapper.toResponse(zona);

    }
}
