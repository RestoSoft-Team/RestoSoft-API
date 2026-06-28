package restosoftteam.restosoft.restosoftv2.enums;

import java.util.Arrays;

public enum RolSistema {

    ADMINISTRADOR,
    MESERO,
    COCINA,
    CONFIGURACION,
    CLIENTE;

    public static RolSistema from(String nombreRol) {

        return Arrays.stream(values())
                .filter(rol -> rol.name().equalsIgnoreCase(nombreRol))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Rol no reconocido: " + nombreRol
                        )
                );

    }

    public boolean esRolEmpresa() {

        return this == ADMINISTRADOR
                || this == MESERO
                || this == COCINA;

    }

    public boolean esRolConfiguracion() {

        return this == CONFIGURACION;

    }

    public boolean esRolCliente() {

        return this == CLIENTE;

    }

}