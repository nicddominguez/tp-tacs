package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Modo de juego.
 */
public enum ModoDeJuego {
  RAPIDO("Rapido"),
    NORMAL("Normal"),
    EXTENDIDO("Extendido");

  private String value;

  ModoDeJuego(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ModoDeJuego fromValue(String text) {
    for (ModoDeJuego b : ModoDeJuego.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
