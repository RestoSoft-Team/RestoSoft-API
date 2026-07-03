package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.enums.EstadoReserva;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.exception.ValorInconsistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.ReservaMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ReservaRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.ReservaMesaService;
import restosoftteam.restosoft.restosoftv2.service.ReservaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ReservaMesaService reservaMesaService;

    @Override
    @Transactional
    public ReservaMesaResponse save(ReservaRequest request, List<Integer> idSmesas, Integer idEmpresa) {

        if(idSmesas.isEmpty()){
            throw new ValorInconsistenteException("No se ha seleccionado ninguna mesa.");
        }

        validarDisponibilidadMesas(request.getFecha(),request.getHora(),idSmesas,null);

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada no existe."));

        Usuario usuario = usuarioRepository.findById(request.getIdusuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario relacionado no existe."));

        Reserva reserva = reservaMapper.toEntity(request);
        reserva.setEmpresa(empresa);
        reserva.setUsuario(usuario);
        Reserva reservaGuardada = reservaRepository.save(reserva);

        return reservaMesaService.save(reservaGuardada, idSmesas);

    }

    @Override
    @Transactional
    public ReservaMesaResponse update(ReservaRequest request, Integer idReserva, List<Integer> idSmesas,Integer idEmpresa) {

        if(idSmesas.isEmpty()){
            throw new ValorInconsistenteException("No se ha seleccionado ninguna mesa.");
        }

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(()-> new RecursoNoEncontradoException("La reserva que se intenta actualizar no existe."));

        boolean cambioFecha = !reserva.getFecha().equals(request.getFecha());

        boolean cambioHora = !reserva.getHora().equals(request.getHora());

        boolean cambioMesas = reservaMesaService.cambiaronMesas(reserva,idSmesas);

        if (cambioFecha || cambioHora || cambioMesas) {

            validarDisponibilidadMesas(
                    request.getFecha(),
                    request.getHora(),
                    idSmesas,
                    idReserva
            );

        }

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada no existe."));

        Usuario usuario = usuarioRepository.findById(request.getIdusuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario relacionado no existe."));

        reservaMapper.updateReservaFromDto(request,reserva);
        reserva.setEmpresa(empresa);
        reserva.setUsuario(usuario);
        Reserva reservaGuardada = reservaRepository.save(reserva);


        return reservaMesaService.update(reservaGuardada, idSmesas);

    }

    public boolean ModificarEstadoReserva(Integer id,EstadoReserva estadoReserva) {
        return reservaRepository.updateEstadoById(id, estadoReserva);
    }

    @Override
    @Transactional
    public void delete(Integer idReserva){
        reservaMesaService.deleteAllByReserva(idReserva);
    }

    private void validarDisponibilidadMesas(
            LocalDate fecha,
            LocalTime hora,
            List<Integer> mesaIds,
            Integer idReservaExcluir) {


        LocalTime horaInicio = hora.minusHours(1).minusMinutes(30);
        LocalTime horaFin = hora.plusHours(1).plusMinutes(30);

        List<ReservaMesa> reservasMesas = reservaRepository.buscarMesasReservadas(
                fecha,
                horaInicio,
                horaFin,
                idReservaExcluir
        );

        Set<Integer> mesasOcupadas = reservasMesas.stream()
                .map(rm -> rm.getMesa().getId())
                .collect(Collectors.toSet());

        List<Integer> mesasEnConflicto = mesaIds.stream()
                .filter(mesasOcupadas::contains)
                .toList();

        if (!mesasEnConflicto.isEmpty()) {

            throw new RegistroYaExistenteException(
                    "Las siguientes mesas ya se encuentran reservadas: "
                            + mesasEnConflicto
            );

        }

    }
}