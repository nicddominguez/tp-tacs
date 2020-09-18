package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Estado de una partida.
 */
public enum EstadoDeJuegoModel {
  ENPROGRESO("EnProgreso"),
    TERMINADA("Terminada"),
    CANCELADA("Cancelada");

  private String value;

  EstadoDeJuegoModel(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static EstadoDeJuegoModel fromValue(String text) {
    for (EstadoDeJuegoModel b : EstadoDeJuegoModel.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
