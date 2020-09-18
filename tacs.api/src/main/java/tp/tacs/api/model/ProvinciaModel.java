package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Una provincia.
 */
@ApiModel(description = "Una provincia.")
@Validated


public class ProvinciaModel   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  public ProvinciaModel id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Id de la provincia
   * @return id
  **/
  @ApiModelProperty(required = true, value = "Id de la provincia")
      @NotNull

    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProvinciaModel nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Nombre de la provincia
   * @return nombre
  **/
  @ApiModelProperty(required = true, value = "Nombre de la provincia")
      @NotNull

    public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProvinciaModel provinciaModel = (ProvinciaModel) o;
    return Objects.equals(this.id, provinciaModel.id) &&
        Objects.equals(this.nombre, provinciaModel.nombre);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProvinciaModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
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
