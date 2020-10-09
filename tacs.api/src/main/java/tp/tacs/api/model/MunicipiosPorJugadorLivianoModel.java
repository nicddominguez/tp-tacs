package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * MunicipiosPorJugadorLivianoModel
 */
@Validated


public class MunicipiosPorJugadorLivianoModel   {
  @JsonProperty("idJugador")
  private String idJugador = null;

  @JsonProperty("municipios")
  @Valid
  private List<MunicipiosLivianosModel> municipios = null;

  public MunicipiosPorJugadorLivianoModel idJugador(String idJugador) {
    this.idJugador = idJugador;
    return this;
  }

  /**
   * Get idJugador
   * @return idJugador
  **/
  @ApiModelProperty(value = "")
  
    public String getIdJugador() {
    return idJugador;
  }

  public void setIdJugador(String idJugador) {
    this.idJugador = idJugador;
  }

  public MunicipiosPorJugadorLivianoModel municipios(List<MunicipiosLivianosModel> municipios) {
    this.municipios = municipios;
    return this;
  }

  public MunicipiosPorJugadorLivianoModel addMunicipiosItem(MunicipiosLivianosModel municipiosItem) {
    if (this.municipios == null) {
      this.municipios = new ArrayList<MunicipiosLivianosModel>();
    }
    this.municipios.add(municipiosItem);
    return this;
  }

  /**
   * Get municipios
   * @return municipios
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<MunicipiosLivianosModel> getMunicipios() {
    return municipios;
  }

  public void setMunicipios(List<MunicipiosLivianosModel> municipios) {
    this.municipios = municipios;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MunicipiosPorJugadorLivianoModel municipiosPorJugadorLivianoModel = (MunicipiosPorJugadorLivianoModel) o;
    return Objects.equals(this.idJugador, municipiosPorJugadorLivianoModel.idJugador) &&
        Objects.equals(this.municipios, municipiosPorJugadorLivianoModel.municipios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idJugador, municipios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MunicipiosPorJugadorLivianoModel {\n");
    
    sb.append("    idJugador: ").append(toIndentedString(idJugador)).append("\n");
    sb.append("    municipios: ").append(toIndentedString(municipios)).append("\n");
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
