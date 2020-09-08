package tp.tacs.api.dominio.partida;

import com.fasterxml.jackson.annotation.JsonValue;
import tp.tacs.api.model.EstadoDeJuegoModel;

import java.util.Arrays;

public enum Estado {
    EN_CURSO("EnProgreso"),
    TERMINADA("Terminada"),
    CANCELADA("Cancelada");

    private String value;

    Estado(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static Estado fromValue(String text) {
        return Arrays.stream(Estado.values())
                .filter(b -> String.valueOf(b.value).equals(text))
                .findFirst()
                .orElse(null);
    }
}
