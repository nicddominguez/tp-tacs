package tp.tacs.api.models;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;

/**
 * AccionDeMovimiento
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-27T18:00:32.244-03:00[America/Buenos_Aires]")
public class AccionDeMovimiento   {
  /**
   * Gets or Sets tipoAccion
   */
  public enum TipoAccionEnum {
    MOVIMIENTO("Movimiento");

    private String value;

    TipoAccionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TipoAccionEnum fromValue(String text) {
      for (TipoAccionEnum b : TipoAccionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("tipoAccion")
  private TipoAccionEnum tipoAccion = null;

  @JsonProperty("cantidad")
  private Integer cantidad = null;

  public AccionDeMovimiento tipoAccion(TipoAccionEnum tipoAccion) {
    this.tipoAccion = tipoAccion;
    return this;
  }

  /**
   * Get tipoAccion
   * @return tipoAccion
  **/
  @ApiModelProperty(value = "")
  
    public TipoAccionEnum getTipoAccion() {
    return tipoAccion;
  }

  public void setTipoAccion(TipoAccionEnum tipoAccion) {
    this.tipoAccion = tipoAccion;
  }

  public AccionDeMovimiento cantidad(Integer cantidad) {
    this.cantidad = cantidad;
    return this;
  }

  /**
   * Get cantidad
   * @return cantidad
  **/
  @ApiModelProperty(value = "")
  
    public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccionDeMovimiento accionDeMovimiento = (AccionDeMovimiento) o;
    return Objects.equals(this.tipoAccion, accionDeMovimiento.tipoAccion) &&
        Objects.equals(this.cantidad, accionDeMovimiento.cantidad);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tipoAccion, cantidad);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccionDeMovimiento {\n");
    
    sb.append("    tipoAccion: ").append(toIndentedString(tipoAccion)).append("\n");
    sb.append("    cantidad: ").append(toIndentedString(cantidad)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
