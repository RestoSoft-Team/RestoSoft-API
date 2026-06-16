package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponseMapStruct;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;
@Mapper(componentModel = "spring")
public interface ReservaMesaMapper {
    ReservaMesaResponseMapStruct toResponse(ReservaMesa mesa);
}
