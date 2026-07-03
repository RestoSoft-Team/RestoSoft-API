package restosoftteam.restosoft.restosoftv2.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface EmailService {

    void enviarTicketPedido(String emailCliente, String emailEmpresa, Integer pedidoId, String mesa, String mesero,
                            List<Map<String, Object>> items, BigDecimal total);

    void enviarFacturaPago(String emailCliente, String emailEmpresa, Integer pedidoId, String mesa, String mesero,
                           List<Map<String, Object>> items, BigDecimal total, List<Map<String, Object>> metodosPago, String tipoPago);
}