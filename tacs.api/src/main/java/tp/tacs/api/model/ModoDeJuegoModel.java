package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Modo de juego.
 */
public enum ModoDeJuegoModel {
  RAPIDO("Rapido"),
    NORMAL("Normal"),
    EXTENDIDO("Extendido");

  private String value;

  ModoDeJuegoModel(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ModoDeJuegoModel fromValue(String text) {
    for (ModoDeJuegoModel b : ModoDeJuegoModel.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
