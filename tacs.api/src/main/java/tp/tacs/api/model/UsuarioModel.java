package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Un usuario.
 */
@ApiModel(description = "Un usuario.")
@Validated


public class UsuarioModel   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("nombreDeUsuario")
  private String nombreDeUsuario = null;

  public UsuarioModel id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UsuarioModel nombreDeUsuario(String nombreDeUsuario) {
    this.nombreDeUsuario = nombreDeUsuario;
    return this;
  }

  /**
   * Get nombreDeUsuario
   * @return nombreDeUsuario
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    public String getNombreDeUsuario() {
    return nombreDeUsuario;
  }

  public void setNombreDeUsuario(String nombreDeUsuario) {
    this.nombreDeUsuario = nombreDeUsuario;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsuarioModel usuarioModel = (UsuarioModel) o;
    return Objects.equals(this.id, usuarioModel.id) &&
        Objects.equals(this.nombreDeUsuario, usuarioModel.nombreDeUsuario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombreDeUsuario);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsuarioModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombreDeUsuario: ").append(toIndentedString(nombreDeUsuario)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
