package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.MesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MesaResponse;
import restosoftteam.restosoft.restosoftv2.mapper.MesaMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Zona;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.MesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ZonaRespository;
import restosoftteam.restosoft.restosoftv2.service.MesaService;


@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;
    private final EmpresaRepository empresaRepository;
    private final ZonaRespository zonaRespository;


    @Override
    public MesaResponse crearMesa(MesaRequest mesaRequest) {
        Mesa mesa = mesaMapper.toEntity(mesaRequest);

        Empresa empresa = empresaRepository.findById(mesaRequest.getIdempresa()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        mesa.setIdempresa(empresa);
        Mesa guardar =  mesaRepository.save(mesa);
        return mesaMapper.toResponse(guardar);

    }

    @Override
    public MesaResponse crearMesaConZonaReal(MesaRequest mesaRequest, Integer idZonaReal) {

        Mesa mesa = mesaMapper.toEntity(mesaRequest);

        Zona zona = zonaRespository.findById(idZonaReal)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada con el ID real: " + idZonaReal));

        Empresa empresa = empresaRepository.findById(mesaRequest.getIdempresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        mesa.setZona(zona);
        mesa.setIdempresa(empresa);

        Mesa mesaGuardada = mesaRepository.save(mesa);

        return mesaMapper.toResponse(mesaGuardada);
    }

}
