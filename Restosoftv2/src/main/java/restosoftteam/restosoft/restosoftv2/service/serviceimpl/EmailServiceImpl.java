package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import restosoftteam.restosoft.restosoftv2.service.EmailService;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${restosoft.email.oficial}")
    private String emailRestosoft;

    @Value("${spring.mail.username}")
    private String emailRemitente;

    private void configurarDestinatarios(MimeMessageHelper helper, String emailCliente, String emailEmpresa) throws MessagingException, UnsupportedEncodingException {
        List<InternetAddress> tos = new ArrayList<>();
        List<InternetAddress> ccs = new ArrayList<>();

        InternetAddress addrCliente = parsearEmailValido(emailCliente);
        InternetAddress addrEmpresa = parsearEmailValido(emailEmpresa);
        InternetAddress addrRestosoft = parsearEmailValido(emailRestosoft);

        if (addrCliente != null) {
            tos.add(addrCliente);
            if (addrEmpresa != null && !addrCliente.getAddress().equalsIgnoreCase(addrEmpresa.getAddress())) {
                ccs.add(addrEmpresa);
            }
        } else if (addrEmpresa != null) {
            tos.add(addrEmpresa);
        } else {
            throw new MessagingException("No hay destinatarios válidos para enviar el correo.");
        }

        if (addrRestosoft != null) {
            ccs.add(addrRestosoft);
        }

        helper.setTo(tos.toArray(new InternetAddress[0]));

        if (!ccs.isEmpty()) {
            helper.setCc(ccs.toArray(new InternetAddress[0]));
        }

        helper.setFrom(new InternetAddress(emailRemitente, "RestSoft System"));
    }

    private InternetAddress parsearEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) return null;

        String cleanEmail = email.trim();

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(emailRegex, cleanEmail)) {
            System.err.println("Formato de correo inválido omitido: " + email);
            return null;
        }

        try {
            return new InternetAddress(cleanEmail);
        } catch (AddressException e) {
            System.err.println("Error al parsear correo omitido: " + email);
            return null;
        }
    }

    @Override
    @Async
    public void enviarTicketPedido(String emailCliente, String emailEmpresa, Integer pedidoId, String mesa, String mesero,
                                   List<Map<String, Object>> items, BigDecimal total) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            configurarDestinatarios(helper, emailCliente, emailEmpresa);
            helper.setSubject("Su pedido #" + pedidoId + " ha sido recibido - RestSoft");

            Context context = new Context();
            context.setVariable("pedidoId", pedidoId);
            context.setVariable("mesa", mesa);
            context.setVariable("mesero", mesero);
            context.setVariable("fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            context.setVariable("estado", "PENDIENTE");
            context.setVariable("items", items);
            context.setVariable("total", total);

            String htmlContent = templateEngine.process("email-pedido-recibido", context);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            System.err.println("Error al enviar email de pedido: " + e.getMessage());
        }
    }

    @Override
    @Async
    public void enviarFacturaPago(String emailCliente, String emailEmpresa, Integer pedidoId, String mesa, String mesero,
                                  List<Map<String, Object>> items, BigDecimal total, List<Map<String, Object>> metodosPago, String tipoPago) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            configurarDestinatarios(helper, emailCliente, emailEmpresa);
            helper.setSubject("Factura de Pago - Pedido #" + pedidoId + " - RestSoft");

            Context context = new Context();
            context.setVariable("pedidoId", pedidoId);
            context.setVariable("mesa", mesa);
            context.setVariable("mesero", mesero);
            context.setVariable("fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            context.setVariable("estado", "PAGADO");
            context.setVariable("items", items);
            context.setVariable("total", total);
            context.setVariable("metodosPago", metodosPago);
            context.setVariable("tipoPago", tipoPago);

            String htmlContent = templateEngine.process("email-factura-pago", context);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            System.err.println("Error al enviar factura: " + e.getMessage());
        }
    }
}