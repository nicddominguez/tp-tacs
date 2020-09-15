package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Modo de operación de un municipio.
 */
public enum ModoDeMunicipioModel {
  DEFENSA("Defensa"),
    PRODUCCION("Produccion");

  private String value;

  ModoDeMunicipioModel(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ModoDeMunicipioModel fromValue(String text) {
    for (ModoDeMunicipioModel b : ModoDeMunicipioModel.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
