package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "configuracion_pasarela")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracionPasarela {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa empresa;

    @Column(name = "pasarela", nullable = false, length = 50)
    private String pasarela; // EPAYCO, PAYPAL, etc.

    @Column(name = "public_key", length = 255)
    private String publicKey;

    @Column(name = "private_key", length = 255)
    private String privateKey;

    @Column(name = "url", length = 255)
    private String url;

    private Boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}