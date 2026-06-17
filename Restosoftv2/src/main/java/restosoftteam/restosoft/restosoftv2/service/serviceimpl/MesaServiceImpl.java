package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.List;
import java.util.UUID;


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

        Empresa empresa = empresaRepository.findById(mesaRequest.getIdempresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        mesa.setEmpresa(empresa);
        Mesa guardar =  mesaRepository.save(mesa);
        return mesaMapper.toResponse(guardar);

    }

    @Override
    public MesaResponse crearMesaConZonaReal(MesaRequest mesaRequest, Integer idZonaReal) {
        Mesa mesa = mesaMapper.toEntity(mesaRequest);

        Zona zona = zonaRespository.findById(idZonaReal)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        Empresa empresa = empresaRepository.findById(mesaRequest.getIdempresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        mesa.setZona(zona);
        mesa.setEmpresa(empresa);

        System.out.println("Guardando mesa en zona: " + mesa.getZona().getId());

        return mesaMapper.toResponse(mesaRepository.save(mesa));
    }

    @Override
    public MesaResponse actualizarMesa(MesaRequest mesaRequest, Integer idMesa) {

        Mesa mesaExistente = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + idMesa));

        boolean verificarNumeroMesa = mesaRepository.existsBynumeroMesaAndEmpresa_IdAndIdNot(
                mesaRequest.getNumeroMesa(),
                mesaRequest.getIdempresa(),
                idMesa
        );

        if (verificarNumeroMesa) {
            throw new RuntimeException("No se puede asignar el mismo numero de una mesa a otra mesa");
        }

        mesaExistente.setNumeroMesa(mesaRequest.getNumeroMesa());


        Mesa mesaGuardada = mesaRepository.save(mesaExistente);

        return mesaMapper.toResponse(mesaGuardada);
    }

    @Override
    @Transactional
    public void intercambiarNumeroMesa(Integer idMesaA, Integer idMesaB) {
        List<Mesa> mesas = mesaRepository.findAllById(List.of(idMesaA,idMesaB));

        if(mesas.size() != 2){
            throw new RuntimeException("Mesas no encontradas");
        }

        Mesa mesaA = mesas.stream().filter(m -> m.getId().equals(idMesaA)).findFirst().get();
        Mesa mesaB = mesas.stream().filter(m -> m.getId().equals(idMesaB)).findFirst().get();

        if(!mesaA.getEmpresa().getId().equals(mesaB.getEmpresa().getId())){
            throw new RuntimeException("No puedes intercambiar mesas de diferentes empresas");
        }

        String numeroOriginalA = mesaA.getNumeroMesa();
        String numeroOriginalB = mesaB.getNumeroMesa();

        mesaA.setNumeroMesa("TEMP_" + UUID.randomUUID().toString().substring(0,5));
        mesaRepository.saveAndFlush(mesaA);

        mesaB.setNumeroMesa(numeroOriginalA);
        mesaRepository.saveAndFlush(mesaB);

        mesaA.setNumeroMesa(numeroOriginalB);
        mesaRepository.save(mesaA);

    }

    @Override
    public List<MesaResponse> obtenerMesasPorEmpresa(Integer idEmpresa) {
        List<Mesa> mesas = mesaRepository.findAllByEmpresa_Id(idEmpresa);
        return mesas.stream()
                .map(mesaMapper::toResponse)
                .toList();
    }
}
