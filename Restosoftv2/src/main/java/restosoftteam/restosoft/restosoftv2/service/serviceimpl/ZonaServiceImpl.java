package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionesRestauranteRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.MesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.ZonaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ZonaResponse;
import restosoftteam.restosoft.restosoftv2.mapper.MesaMapper;
import restosoftteam.restosoft.restosoftv2.mapper.ZonaMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Zona;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.MesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ZonaRespository;
import restosoftteam.restosoft.restosoftv2.service.ZonaService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ZonaServiceImpl implements ZonaService {

    private final ZonaRespository  zonaRespository;
    private final ZonaMapper zonaMapper;
    private final EmpresaRepository empresaRepository;
    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

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
        zonaExistente.setPlanoUrl(zonaRequest.getPlanoUrl());
        zonaExistente.setEstado(zonaRequest.getEstado());

        return zonaMapper.toResponse(zonaRespository.save(zonaExistente));
    }

    @Override
    public ZonaResponse eliminarZona(Integer id, Integer idEmpresa) {
        Zona zona = zonaRespository.findByIdAndIdEmpresa_Id(id, idEmpresa).orElseThrow(() -> new RuntimeException("Zona no encontrada o no pertenece a la empresa"));

        zonaRespository.delete(zona);

        return zonaMapper.toResponse(zona);

    }

    @Override
    public List<ZonaResponse> obtenerZonasPorEmpresa(Integer idEmpresa) {
        List<Zona> zonas = zonaRespository.findAllByIdEmpresa_Id(idEmpresa);
        return zonas.stream()
                .map(zonaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void sincronizarPlano(ConfiguracionesRestauranteRequest request, Integer idEmpresa) {
        List<Zona> zonasActuales = zonaRespository.findAllByIdEmpresa_Id(idEmpresa);
        Set<Integer> idsZonasEnviadas = new HashSet<>();
        Map<Long, Integer> tempIdMap = new HashMap<>();

        for (ZonaRequest zr : request.getZonas()) {
            Zona zona;
            if (zr.getId() != null) {
                zona = zonaRespository.findById(zr.getId()).orElseThrow(() -> new RuntimeException("Zona no encontrada"));
                zona.setNombreZona(zr.getNombreZona());
                zona.setPlanoUrl(zr.getPlanoUrl());
                zona.setEstado(zr.getEstado());
                idsZonasEnviadas.add(zr.getId());
            } else {
                zona = new Zona();
                zona.setNombreZona(zr.getNombreZona());
                zona.setPlanoUrl(zr.getPlanoUrl());
                zona.setEstado(zr.getEstado());
                zona.setIdEmpresa(empresaRepository.findById(idEmpresa).orElseThrow(() -> new RuntimeException("Empresa no encontrada")));
            }
            Zona guardada = zonaRespository.save(zona);
            if (zr.getIdTemporal() != null) tempIdMap.put(zr.getIdTemporal(), guardada.getId());
            else tempIdMap.put(guardada.getId().longValue(), guardada.getId());
        }

        for (Zona zActual : zonasActuales) {
            if (!idsZonasEnviadas.contains(zActual.getId())) {
                zonaRespository.delete(zActual);
            }
        }

        List<Mesa> mesasActuales = mesaRepository.findAllByEmpresa_Id(idEmpresa);
        Set<Integer> idsMesasEnviadas = new HashSet<>();

        for (MesaRequest mr : request.getMesas()) {
            Mesa mesa;
            Integer idZonaReal = tempIdMap.get(mr.getIdZona());
            if (idZonaReal == null) throw new RuntimeException("Zona no encontrada para la mesa");

            if (mr.getId() != null) {
                mesa = mesaRepository.findById(mr.getId()).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
                mesa.setNumeroMesa(mr.getNumeroMesa());
                mesa.setDisponibilidadPersonas(mr.getDisponibilidadPersonas());
                mesa.setPosicionX(mr.getPosicionX());
                mesa.setPosicionY(mr.getPosicionY());
                mesa.setTipoMesa(mr.getTipoMesa());
                mesa.setEstado(mr.getEstado());
                mesa.setZona(zonaRespository.findById(idZonaReal).get());
                idsMesasEnviadas.add(mr.getId());
            } else {
                mesa = mesaMapper.toEntity(mr);
                mesa.setEmpresa(empresaRepository.findById(idEmpresa).get());
                mesa.setZona(zonaRespository.findById(idZonaReal).get());
            }
            mesaRepository.save(mesa);
        }

        for (Mesa mActual : mesasActuales) {
            if (!idsMesasEnviadas.contains(mActual.getId())) {
                mesaRepository.delete(mActual);
            }
        }
    }
}
