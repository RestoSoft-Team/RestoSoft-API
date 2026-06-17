package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaMesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaResponse;
import restosoftteam.restosoft.restosoftv2.enums.EstadoReserva;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.exception.ValorInconsistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.ReservaMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ReservaMesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ReservaRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.ReservaMesaService;
import restosoftteam.restosoft.restosoftv2.service.ReservaService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    public ReservaMesaResponse save(ReservaRequest request, List<Integer> idSmesas) {

        if(idSmesas.isEmpty()){
            throw new ValorInconsistenteException("No se ha seleccionado ninguna mesa.");
        }

        validarDisponibilidad(request.getFecha(),request.getHora());

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
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
    public ReservaMesaResponse update(ReservaRequest request, Integer idReserva, List<Integer> idSmesas) {

        if(idSmesas.isEmpty()){
            throw new ValorInconsistenteException("No se ha seleccionado ninguna mesa.");
        }

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(()-> new RecursoNoEncontradoException("La reserva que se intenta actualizar no existe."));

        validarDisponibilidadExcluyendoId(request.getFecha(),request.getHora(),idReserva);

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
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

    private void validarDisponibilidad(LocalDate fecha, LocalTime hora){

        if (reservaRepository.buscarPorFechaYRangoHora(fecha,
                hora.minusHours(1).minusMinutes(30),
                hora.plusHours(1).plusMinutes(30)) > 0) {
            throw new RegistroYaExistenteException("Ya existe una reserva con esa fecha y hora actualmente.");
        }

    }

    private void validarDisponibilidadExcluyendoId(LocalDate fecha, LocalTime hora,Integer id){

        if (reservaRepository.buscarPorFechaYRangoHoraExcluyendoId(fecha,
                hora.minusHours(1).minusMinutes(30),
                hora.plusHours(1).plusMinutes(30),id) > 0) {
            throw new RegistroYaExistenteException("Ya existe una reserva con esa fecha y hora actualmente.");
        }

    }
}
