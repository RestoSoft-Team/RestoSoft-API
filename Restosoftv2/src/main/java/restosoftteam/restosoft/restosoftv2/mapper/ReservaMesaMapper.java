package restosoftteam.restosoft.restosoftv2.mapper;

import restosoftteam.restosoft.restosoftv2.dto.request.ReservaMesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;

public interface ReservaMesaMapper {
    ReservaMesa toEntity(ReservaMesaRequest request);
    ReservaMesaResponse toResponse(ReservaMesa mesa);
}
