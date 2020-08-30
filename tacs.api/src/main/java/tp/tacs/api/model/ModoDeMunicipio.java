package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Modo de operación de un municipio.
 */
public enum ModoDeMunicipio {
  DEFENSA("Defensa"),
    PRODUCCION("Produccion");

  private String value;

  ModoDeMunicipio(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ModoDeMunicipio fromValue(String text) {
    for (ModoDeMunicipio b : ModoDeMunicipio.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
