package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tp.tacs.api.model.UsuarioModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NuevoJWTModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-10T23:54:09.200258200-03:00[America/Buenos_Aires]")


public class NuevoJWTModel   {
  @JsonProperty("usuario")
  private UsuarioModel usuario = null;

  @JsonProperty("token")
  private String token = null;

  @JsonProperty("refreshToken")
  private String refreshToken = null;

  public NuevoJWTModel usuario(UsuarioModel usuario) {
    this.usuario = usuario;
    return this;
  }

  /**
   * Get usuario
   * @return usuario
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public UsuarioModel getUsuario() {
    return usuario;
  }

  public void setUsuario(UsuarioModel usuario) {
    this.usuario = usuario;
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

  public NuevoJWTModel refreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  /**
   * Token de refresh para generar un nuevo token de acceso
   * @return refreshToken
  **/
  @ApiModelProperty(value = "Token de refresh para generar un nuevo token de acceso")
  
    public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
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
    return Objects.equals(this.usuario, nuevoJWTModel.usuario) &&
        Objects.equals(this.token, nuevoJWTModel.token) &&
        Objects.equals(this.refreshToken, nuevoJWTModel.refreshToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuario, token, refreshToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NuevoJWTModel {\n");
    
    sb.append("    usuario: ").append(toIndentedString(usuario)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
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
