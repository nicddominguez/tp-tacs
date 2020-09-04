package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NuevoJWTModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-03T22:53:49.293-03:00[America/Buenos_Aires]")
public class NuevoJWTModel   {
  @JsonProperty("usuario")
  private UsuarioModel usuarioModel = null;

  @JsonProperty("token")
  private String token = null;

  public NuevoJWTModel usuario(UsuarioModel usuarioModel) {
    this.usuarioModel = usuarioModel;
    return this;
  }

  /**
   * Get usuario
   * @return usuario
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public UsuarioModel getUsuario() {
    return usuarioModel;
  }

  public void setUsuario(UsuarioModel usuarioModel) {
    this.usuarioModel = usuarioModel;
  }

  public NuevoJWTModel token(String token) {
    this.token = token;
    return this;
  }

  /**
   * JWT que puede utilizarse para acceder a los recursos protegidos
   * @return token
  **/
  @ApiModelProperty(required = true, value = "JWT que puede utilizarse para acceder a los recursos protegidos")
      @NotNull

    public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NuevoJWTModel nuevoJWTModel = (NuevoJWTModel) o;
    return Objects.equals(this.usuarioModel, nuevoJWTModel.usuarioModel) &&
        Objects.equals(this.token, nuevoJWTModel.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuarioModel, token);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NuevoJWTModel {\n");
    
    sb.append("    usuario: ").append(toIndentedString(usuarioModel)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
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
