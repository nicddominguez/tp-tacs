package tp.tacs.api.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Estado de una partida.
 */
public enum EstadoDeJuego {
  ENPROGRESO("EnProgreso"),
    TERMINADA("Terminada"),
    CANCELADA("Cancelada");

  private String value;

  EstadoDeJuego(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static EstadoDeJuego fromValue(String text) {
    for (EstadoDeJuego b : EstadoDeJuego.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
