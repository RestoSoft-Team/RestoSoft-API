package restosoftteam.restosoft.restosoftv2.enums;

public enum EstadoRegistro {

    ACTIVO,
    INACTIVO;

    public static EstadoRegistro from(String estado) {

        for (EstadoRegistro value : values()) {

            if (value.name().equalsIgnoreCase(estado)) {
                return value;
            }

        }

        throw new IllegalArgumentException(
                "Estado no reconocido: " + estado
        );

    }

}