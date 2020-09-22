package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import tp.tacs.api.model.UsuarioModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListarUsuariosResponse
 */
@Validated


public class ListarUsuariosResponse   {
  @JsonProperty("usuarios")
  @Valid
  private List<UsuarioModel> usuarios = null;

  @JsonProperty("cantidadTotalDeUsuarios")
  private Long cantidadTotalDeUsuarios = null;

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

  public ListarUsuariosResponse cantidadTotalDeUsuarios(Long cantidadTotalDeUsuarios) {
    this.cantidadTotalDeUsuarios = cantidadTotalDeUsuarios;
    return this;
  }

  /**
   * Get cantidadTotalDeUsuarios
   * @return cantidadTotalDeUsuarios
  **/
  @ApiModelProperty(value = "")
  
    public Long getCantidadTotalDeUsuarios() {
    return cantidadTotalDeUsuarios;
  }

  public void setCantidadTotalDeUsuarios(Long cantidadTotalDeUsuarios) {
    this.cantidadTotalDeUsuarios = cantidadTotalDeUsuarios;
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
    return Objects.equals(this.usuarios, listarUsuariosResponse.usuarios) &&
        Objects.equals(this.cantidadTotalDeUsuarios, listarUsuariosResponse.cantidadTotalDeUsuarios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usuarios, cantidadTotalDeUsuarios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListarUsuariosResponse {\n");
    
    sb.append("    usuarios: ").append(toIndentedString(usuarios)).append("\n");
    sb.append("    cantidadTotalDeUsuarios: ").append(toIndentedString(cantidadTotalDeUsuarios)).append("\n");
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
