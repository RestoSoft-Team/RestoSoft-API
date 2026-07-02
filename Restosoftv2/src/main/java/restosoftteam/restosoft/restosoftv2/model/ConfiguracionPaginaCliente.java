package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "configuracion_paginas_clientes", schema = "restosoft_v2")
public class ConfiguracionPaginaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpagina", nullable = false)
    private Integer id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa empresa;

    // ============================================
    // BRANDING (Identidad)
    // ============================================
    @Column(name = "br_nombre", length = 100)
    private String brandingNombre;

    @Column(name = "br_slogan", length = 255)
    private String brandingSlogan;

    @Column(name = "br_descripcion", length = 500)
    private String brandingDescripcion;

    @Column(name = "br_logo", length = 500)
    private String brandingLogo;

    @Column(name = "br_favicon", length = 500)
    private String brandingFavicon;

    // ============================================
    // SECCIÓN: HERO
    // ============================================
    @Column(name = "hero_activa")
    private Boolean heroActiva = true;

    @Column(name = "hero_imagen", length = 500)
    private String heroImagen;

    @Column(name = "hero_overlay", length = 100)
    private String heroOverlay;

    @Column(name = "hero_mostrar_direccion")
    private Boolean heroMostrarDireccion = true;

    @Column(name = "hero_mostrar_horario")
    private Boolean heroMostrarHorario = true;

    // ============================================
    // ESTILOS: COLORES
    // ============================================
    @Column(name = "color_primario", length = 20)
    private String colorPrimario;

    @Column(name = "color_primario_hover", length = 20)
    private String colorPrimarioHover;

    @Column(name = "color_primario_rgb", length = 30)
    private String colorPrimarioRgb;

    @Column(name = "color_secundario", length = 20)
    private String colorSecundario;

    @Column(name = "color_secundario_hover", length = 20)
    private String colorSecundarioHover;

    @Column(name = "color_oscuro", length = 20)
    private String colorOscuro;

    @Column(name = "color_dark_mid", length = 20)
    private String colorDarkMid;

    @Column(name = "color_bg", length = 20)
    private String colorBg;

    @Column(name = "color_bg_alt", length = 20)
    private String colorBgAlt;

    @Column(name = "color_card", length = 20)
    private String colorCard;

    @Column(name = "color_texto", length = 20)
    private String colorTexto;

    @Column(name = "color_texto_sec", length = 20)
    private String colorTextoSec;

    @Column(name = "color_texto_ter", length = 20)
    private String colorTextoTer;

    @Column(name = "color_borde", length = 20)
    private String colorBorde;

    @Column(name = "color_exito", length = 20)
    private String colorExito;

    @Column(name = "color_warning", length = 20)
    private String colorWarning;

    @Column(name = "color_peligro", length = 20)
    private String colorPeligro;

    // ============================================
    // ESTILOS: TIPOGRAFÍA
    // ============================================
    @Column(name = "tipografia_fuente", length = 100)
    private String tipografiaFuente;

    @Column(name = "tipografia_fuente_heading", length = 100)
    private String tipografiaFuenteHeading;

    @Column(name = "tipografia_fuente_google", length = 100)
    private String tipografiaFuenteGoogle;

    @Column(name = "tipografia_fs_hero", length = 10)
    private String tipografiaFsHero;

    @Column(name = "tipografia_fs_h2", length = 10)
    private String tipografiaFsH2;

    @Column(name = "tipografia_fs_h3", length = 10)
    private String tipografiaFsH3;

    @Column(name = "tipografia_fs_h4", length = 10)
    private String tipografiaFsH4;

    @Column(name = "tipografia_fs_body", length = 10)
    private String tipografiaFsBody;

    @Column(name = "tipografia_fs_small", length = 10)
    private String tipografiaFsSmall;

    @Column(name = "tipografia_fs_mini", length = 10)
    private String tipografiaFsMini;

    @Column(name = "tipografia_fw_hero", length = 10)
    private String tipografiaFwHero;

    @Column(name = "tipografia_fw_h2", length = 10)
    private String tipografiaFwH2;

    @Column(name = "tipografia_fw_h3", length = 10)
    private String tipografiaFwH3;

    @Column(name = "tipografia_fw_h4", length = 10)
    private String tipografiaFwH4;

    @Column(name = "tipografia_fw_body", length = 10)
    private String tipografiaFwBody;

    @Column(name = "tipografia_fw_bold", length = 10)
    private String tipografiaFwBold;

    @Column(name = "tipografia_fw_small", length = 10)
    private String tipografiaFwSmall;

    // ============================================
    // ESTILOS: BORDES, SOMBRAS, ESPACIOS, ANIM
    // ============================================
    @Column(name = "borde_tarjeta", length = 10)
    private String bordeTarjeta;
    @Column(name = "borde_boton", length = 10)
    private String bordeBoton;
    @Column(name = "borde_input", length = 10)
    private String bordeInput;
    @Column(name = "borde_badge", length = 10)
    private String bordeBadge;
    @Column(name = "borde_imagen", length = 10)
    private String bordeImagen;
    @Column(name = "borde_tooltip", length = 10)
    private String bordeTooltip;

    @Column(name = "sombra_suave", length = 100)
    private String sombraSuave;
    @Column(name = "sombra_media", length = 100)
    private String sombraMedia;
    @Column(name = "sombra_fuerte", length = 100)
    private String sombraFuerte;
    @Column(name = "sombra_boton", length = 100)
    private String sombraBoton;

    @Column(name = "espacio_seccion_py", length = 10)
    private String espacioSeccionPy;
    @Column(name = "espacio_card_p", length = 10)
    private String espacioCardP;
    @Column(name = "espacio_gap", length = 10)
    private String espacioGap;
    @Column(name = "espacio_max_w", length = 10)
    private String espacioMaxW;

    @Column(name = "anim_activa")
    private Boolean animActiva = true;
    @Column(name = "anim_velocidad", length = 10)
    private String animVelocidad;
    @Column(name = "anim_curva", length = 50)
    private String animCurva;
    @Column(name = "anim_hover_lift", length = 10)
    private String animHoverLift;
    @Column(name = "anim_hover_lift_btn", length = 10)
    private String animHoverLiftBtn;

    // ============================================
    // SECCIONES DE CONTENIDO
    // ============================================
    @Column(name = "seccion_orden", length = 255)
    private String seccionOrdenJson; // Ej: '["hero","acerca","horarios"]'

    @Column(name = "acerca_activa")
    private Boolean acercaActiva = true;
    @Column(name = "acerca_titulo", length = 100)
    private String acercaTitulo;
    @Column(name = "acerca_texto", length = 1000)
    private String acercaTexto;
    @Column(name = "acerca_imagen", length = 500)
    private String acercaImagen;

    @Column(name = "horarios_activa")
    private Boolean horariosActiva = true;
    @Column(name = "horarios_titulo", length = 100)
    private String horariosTitulo;
    @Column(name = "horarios_items", columnDefinition = "TEXT")
    private String horariosItemsJson;

    @Column(name = "galeria_activa")
    private Boolean galeriaActiva = true;

    @Column(name = "galeria_titulo", length = 100)
    private String galeriaTitulo;

    @Column(name = "galeria_imagen_1", length = 500)
    private String galeriaImagen1;

    @Column(name = "galeria_imagen_2", length = 500)
    private String galeriaImagen2;

    @Column(name = "galeria_imagen_3", length = 500)
    private String galeriaImagen3;

    @Column(name = "galeria_imagen_4", length = 500)
    private String galeriaImagen4;

    @Column(name = "galeria_imagen_5", length = 500)
    private String galeriaImagen5;

    @Column(name = "galeria_imagen_6", length = 500)
    private String galeriaImagen6;

    @Column(name = "contacto_activa")
    private Boolean contactoActiva = true;
    @Column(name = "contacto_titulo", length = 100)
    private String contactoTitulo;
    @Column(name = "contacto_mostrar_direccion")
    private Boolean contactoMostrarDireccion = true;
    @Column(name = "contacto_direccion", length = 255)
    private String contactoDireccion;
    @Column(name = "contacto_mostrar_telefono")
    private Boolean contactoMostrarTelefono = true;
    @Column(name = "contacto_telefono", length = 50)
    private String contactoTelefono;
    @Column(name = "contacto_mostrar_email")
    private Boolean contactoMostrarEmail = true;
    @Column(name = "contacto_email", length = 100)
    private String contactoEmail;
    @Column(name = "contacto_mostrar_whatsapp")
    private Boolean contactoMostrarWhatsapp = true;
    @Column(name = "contacto_whatsapp", length = 50)
    private String contactoWhatsapp;

    @Column(name = "redes_activa")
    private Boolean redesActiva = true;
    @Column(name = "redes_titulo", length = 100)
    private String redesTitulo;
    @Column(name = "redes_items", columnDefinition = "TEXT")
    private String redesItemsJson; // Ej: '[{"plataforma":"FB","url":"...","icono":"..."}]'

    @Column(name = "login_activa")
    private Boolean loginActiva = true;
    @Column(name = "login_titulo", length = 100)
    private String loginTitulo;
    @Column(name = "login_texto", length = 255)
    private String loginTexto;
    @Column(name = "login_enlace", length = 255)
    private String loginEnlace;
    @Column(name = "login_texto_boton", length = 50)
    private String loginTextoBoton;

    @Column(name = "footer_texto", length = 255)
    private String footerTexto;
    @Column(name = "footer_mostrar_powered")
    private Boolean footerMostrarPowered = true;
}