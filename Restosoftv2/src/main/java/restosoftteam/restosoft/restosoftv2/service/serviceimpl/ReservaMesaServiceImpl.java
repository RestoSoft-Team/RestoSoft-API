package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.response.MesaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponseMapStruct;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.ValorInconsistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.MesaMapper;
import restosoftteam.restosoft.restosoftv2.mapper.ReservaMapper;
import restosoftteam.restosoft.restosoftv2.mapper.ReservaMesaMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.MesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ReservaMesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ReservaRepository;
import restosoftteam.restosoft.restosoftv2.service.ReservaMesaService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservaMesaServiceImpl implements ReservaMesaService {
    private final ReservaMesaRepository reservaMesaRepository;
    private final ReservaMesaMapper reservaMesaMapper;
    private final ReservaRepository reservaRepository;
    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;
    private final ReservaMapper reservaMapper;
    private final EmpresaRepository empresaRepository;

    @Override
    public List<ReservaMesaResponseMapStruct> findAllByEmpresaIdDiario(Integer idEmpresa){

        Empresa empresa = empresaRepository.findById(idEmpresa).
                orElseThrow(()-> new RecursoNoEncontradoException("Empresa relacionda no encontrada."));

        return reservaMesaRepository.
                findAllByEmpresaAndFechaToday(empresa).stream().map(reservaMesaMapper ::toResponse).toList();

    }

    @Override
    public List<ReservaMesaResponseMapStruct> findAllByEmpresaIdGeneral(Integer idEmpresa){
        Empresa empresa = empresaRepository.findById(idEmpresa).
                orElseThrow(()-> new RecursoNoEncontradoException("Empresa relacionda no encontrada."));

        return reservaMesaRepository.
                findAllByEmpresa(empresa).stream().map(reservaMesaMapper ::toResponse).toList();
    }

    @Override
    public List<ReservaMesaResponseMapStruct> findAllByReservaId(Integer idReserva) {

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reserva relacionada no existe."));


        return reservaMesaRepository.findAllByReserva(reserva)
                .stream().map(reservaMesaMapper::toResponse).toList();

    }

    @Override
    public List<ReservaMesaResponseMapStruct> findAllByMesaId(Integer idMesa) {

        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La mesa relacionada no existe."));

        return reservaMesaRepository.findAllByMesa(mesa)
                .stream().map(reservaMesaMapper::toResponse).toList();

    }

    @Override
    @Transactional
    public ReservaMesaResponse save(Reserva reserva, List<Integer> mesasIds) {

        validarCapacidadMesasReserva(reserva, mesasIds);

        List<ReservaMesa> listaReservaMesas = new ArrayList<>();

        for (Integer idMesa : mesasIds) {
            ReservaMesa reservaMesa = new ReservaMesa(reserva, obtenerMesaPorId(idMesa));
            listaReservaMesas.add(reservaMesa);
        }

        List<ReservaMesa> listaResultado = reservaMesaRepository.saveAll(listaReservaMesas);

        ReservaMesaResponse reservaMesaResponse = new ReservaMesaResponse();

        if (!listaResultado.isEmpty()) {
            reservaMesaResponse.setId(listaResultado.getFirst().getId());
            reservaMesaResponse.setReserva(reservaMapper.toResponse(listaResultado.getFirst().getReserva()));

            List<MesaResponse> listaMesasRespuesta = new ArrayList<>();
            for (ReservaMesa reservaMesa : listaResultado) {
                listaMesasRespuesta.add(mesaMapper.toResponse(reservaMesa.getMesa()));
            }
            reservaMesaResponse.setListaMesas(listaMesasRespuesta);
        }
        return reservaMesaResponse;

    }

    @Override
    @Transactional
    public ReservaMesaResponse update(Reserva reserva, List<Integer> mesasIds) {

        reservaMesaRepository.deleteAllByReserva(reserva);
        return save(reserva, mesasIds);

    }


    @Override
    @Transactional
    public void deleteByReservaAndMesa(Integer idReserva, Integer idMesa) {

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reserva relacionada no existe."));

        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La mesa relacionada no existe."));

        reservaMesaRepository.deleteByReservaAndMesa(reserva, mesa);

    }

    @Override
    @Transactional
    public void deleteAllByReserva(Integer idReserva) {

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reserva relacionada no existe."));
        reservaMesaRepository.deleteAllByReserva(reserva);

    }

    private Mesa obtenerMesaPorId(Integer idMesa) {
        return mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La mesa relacionada con  el id " + idMesa + " no existe."));
    }

    private void validarCapacidadMesasReserva(Reserva reserva, List<Integer> listaMesaIds) {

        int cantidadPersonasReserva = reserva.getCantidadPersonas();
        int capacidadTotalMesas = 0;
        boolean existeMesaSuficiente = false;

        for (Integer mesaId : listaMesaIds) {
            Integer capacidadMesa = mesaRepository.obtenerDisponibilidadPersonasByMesa_Id(mesaId);

            if (capacidadMesa == null) {
                throw new RecursoNoEncontradoException(
                        "La mesa relacionada con el id: " + mesaId + " no existe.");
            }

            if (capacidadMesa >= cantidadPersonasReserva) {
                existeMesaSuficiente = true;
            }

            capacidadTotalMesas += capacidadMesa;
        }

        if (capacidadTotalMesas < cantidadPersonasReserva) {
            throw new ValorInconsistenteException(
                    "La disponibilidad de las mesas seleccionadas no alcanza para la cantidad de personas en la reserva.");
        }

        if (listaMesaIds.size() > 1 && existeMesaSuficiente) {
            throw new ValorInconsistenteException(
                    "No es posible seleccionar varias mesas cuando una sola mesa tiene capacidad suficiente para la reserva.");
        }

    }
    @Override
    public boolean cambiaronMesas(Reserva reserva,
                                             List<Integer> nuevasMesas){

        List<Integer> actuales =
                reservaMesaRepository.findAllByReserva(reserva)
                        .stream()
                        .map(rm -> rm.getMesa().getId())
                        .toList();

        return !new HashSet<>(actuales)
                .equals(new HashSet<>(nuevasMesas));

    }

}
