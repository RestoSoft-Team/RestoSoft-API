package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.HorarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.HorarioResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.HorarioMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Horario;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.HorarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.HorarioService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final HorarioMapper horarioMapper;

    @Override
    public HorarioResponse crearHorario(HorarioRequest request) {
        Horario horario = horarioMapper.toEntity(request);

        Usuario usuario = usuarioRepository.findById(request.getIdusuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + request.getIdusuario()));

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada: " + request.getIdempresa()));

        horario.setIdusuario(usuario);
        horario.setIdempresa(empresa);

        return horarioMapper.toResponse(horarioRepository.save(horario));
    }

    @Override
    public HorarioResponse actualizarHorario(Integer id, HorarioRequest request) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Horario no encontrado: " + id));
        horario.setHoraEntrada(request.getHoraEntrada());
        horario.setHoraSalida(request.getHoraSalida());
        horario.setDiaSemana(request.getDiaSemana());
        return horarioMapper.toResponse(horarioRepository.save(horario));
    }

    @Override
    public void eliminarHorario(Integer id) {
        if (!horarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Horario no encontrado: " + id);
        }
        horarioRepository.deleteById(id);
    }

    @Override
    public List<HorarioResponse> listarHorariosPorUsuario(Integer idUsuario, Integer idEmpresa) {
        return horarioRepository.findByIdusuarioIdAndIdempresaId(idUsuario, idEmpresa)
                .stream()
                .map(horarioMapper::toResponse)
                .collect(Collectors.toList());
    }
}