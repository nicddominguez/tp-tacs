package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ListarUsuariosResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-14T19:00:38.696940-03:00[America/Buenos_Aires]")


public class ListarUsuariosResponse   {
  @JsonProperty("usuarios")
  @Valid
  private List<UsuarioModel> usuarios = null;

  public ListarUsuariosResponse usuarios(List<UsuarioModel> usuarios) {
    this.usuarios = usuarios;
    return this;
  }

  public ListarUsuariosResponse addUsuariosItem(UsuarioModel usuariosItem) {
    if (this.usuarios == null) {
      this.usuarios = new ArrayList<UsuarioModel>();
    }
    this.usuarios.add(usuariosItem);
    return this;
  }

  /**
   * Get usuarios
   * @return usuarios
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<UsuarioModel> getUsuarios() {
    return usuarios;
  }

  public void setUsuarios(List<UsuarioModel> usuarios) {
    this.usuarios = usuarios;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListarUsuariosResponse listarUsuariosResponse = (ListarUsuariosResponse) o;
    return Objects.equals(this.usuarios, listarUsuariosResponse.usuarios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuarios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListarUsuariosResponse {\n");
    
    sb.append("    usuarios: ").append(toIndentedString(usuarios)).append("\n");
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
