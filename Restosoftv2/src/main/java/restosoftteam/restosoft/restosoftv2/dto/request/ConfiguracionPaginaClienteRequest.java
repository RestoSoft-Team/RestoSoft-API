package restosoftteam.restosoft.restosoftv2.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfiguracionPaginaClienteRequest {

    // Branding
    private String brandingNombre;
    private String brandingSlogan;
    private String brandingDescripcion;

    // Hero
    private Boolean heroActiva;
    private String heroOverlay;
    private Boolean heroMostrarDireccion;
    private Boolean heroMostrarHorario;

    // Colores
    private String colorPrimario;
    private String colorPrimarioHover;
    private String colorPrimarioRgb;
    private String colorSecundario;
    private String colorSecundarioHover;
    private String colorOscuro;
    private String colorDarkMid;
    private String colorBg;
    private String colorBgAlt;
    private String colorCard;
    private String colorTexto;
    private String colorTextoSec;
    private String colorTextoTer;
    private String colorBorde;
    private String colorExito;
    private String colorWarning;
    private String colorPeligro;

    // Tipografía
    private String tipografiaFuente;
    private String tipografiaFuenteHeading;
    private String tipografiaFuenteGoogle;
    private String tipografiaFsHero;
    private String tipografiaFsH2;
    private String tipografiaFsH3;
    private String tipografiaFsH4;
    private String tipografiaFsBody;
    private String tipografiaFsSmall;
    private String tipografiaFsMini;
    private String tipografiaFwHero;
    private String tipografiaFwH2;
    private String tipografiaFwH3;
    private String tipografiaFwH4;
    private String tipografiaFwBody;
    private String tipografiaFwBold;
    private String tipografiaFwSmall;

    // Bordes
    private String bordeTarjeta;
    private String bordeBoton;
    private String bordeInput;
    private String bordeBadge;
    private String bordeImagen;
    private String bordeTooltip;

    // Sombras
    private String sombraSuave;
    private String sombraMedia;
    private String sombraFuerte;
    private String sombraBoton;

    // Espacios
    private String espacioSeccionPy;
    private String espacioCardP;
    private String espacioGap;
    private String espacioMaxW;

    // Animaciones
    private Boolean animActiva;
    private String animVelocidad;
    private String animCurva;
    private String animHoverLift;
    private String animHoverLiftBtn;

    // Secciones
    private String seccionOrdenJson;

    // Acerca
    private Boolean acercaActiva;
    private String acercaTitulo;
    private String acercaTexto;

    // Horarios
    private Boolean horariosActiva;
    private String horariosTitulo;
    private String horariosItemsJson;

    // Galería
    private Boolean galeriaActiva;
    private String galeriaTitulo;

    // Contacto
    private Boolean contactoActiva;
    private String contactoTitulo;
    private Boolean contactoMostrarDireccion;
    private String contactoDireccion;
    private Boolean contactoMostrarTelefono;
    private String contactoTelefono;
    private Boolean contactoMostrarEmail;
    private String contactoEmail;
    private Boolean contactoMostrarWhatsapp;
    private String contactoWhatsapp;

    // Redes
    private Boolean redesActiva;
    private String redesTitulo;
    private String redesItemsJson;

    // Login
    private Boolean loginActiva;
    private String loginTitulo;
    private String loginTexto;
    private String loginEnlace;
    private String loginTextoBoton;

    // Footer
    private String footerTexto;
    private Boolean footerMostrarPowered;

}