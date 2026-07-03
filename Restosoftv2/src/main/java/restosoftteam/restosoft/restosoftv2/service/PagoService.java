package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPagoPublicaResponse;

public interface PagoService {
    ConfiguracionPagoPublicaResponse obtenerConfiguracionPublica(Integer idEmpresa);
    void procesarPagoConfirmado(String refPayco, String estado, Double monto, Integer idPedido, Integer idEmpresa, Integer idUsuario);
}
