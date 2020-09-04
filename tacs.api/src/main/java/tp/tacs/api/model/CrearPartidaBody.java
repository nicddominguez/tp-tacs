package tp.tacs.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * CrearPartidaBody
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:39.417-03:00[America/Buenos_Aires]")
public class CrearPartidaBody   {
  @JsonProperty("idProvincia")
  private Integer idProvincia = null;

  @JsonProperty("cantidadMunicipios")
  private Integer cantidadMunicipios = null;

  @JsonProperty("idJugadores")
  @Valid
  private List<Integer> idJugadores = null;

  @JsonProperty("modoDeJuego")
  private ModoDeJuegoModel modoDeJuegoModel = null;

  public CrearPartidaBody idProvincia(Integer idProvincia) {
    this.idProvincia = idProvincia;
    return this;
  }

  /**
   * Id de la provincia donde se juega
   * @return idProvincia
  **/
  @ApiModelProperty(value = "Id de la provincia donde se juega")
  
    public Integer getIdProvincia() {
    return idProvincia;
  }

  public void setIdProvincia(Integer idProvincia) {
    this.idProvincia = idProvincia;
  }

  public CrearPartidaBody cantidadMunicipios(Integer cantidadMunicipios) {
    this.cantidadMunicipios = cantidadMunicipios;
    return this;
  }

  /**
   * Cantidad de municipios que se usan
   * @return cantidadMunicipios
  **/
  @ApiModelProperty(value = "Cantidad de municipios que se usan")
  
    public Integer getCantidadMunicipios() {
    return cantidadMunicipios;
  }

  public void setCantidadMunicipios(Integer cantidadMunicipios) {
    this.cantidadMunicipios = cantidadMunicipios;
  }

  public CrearPartidaBody idJugadores(List<Integer> idJugadores) {
    this.idJugadores = idJugadores;
    return this;
  }

  public CrearPartidaBody addIdJugadoresItem(Integer idJugadoresItem) {
    if (this.idJugadores == null) {
      this.idJugadores = new ArrayList<Integer>();
    }
    this.idJugadores.add(idJugadoresItem);
    return this;
  }

  /**
   * Jugadores
   * @return idJugadores
  **/
  @ApiModelProperty(value = "Jugadores")
  
    public List<Integer> getIdJugadores() {
    return idJugadores;
  }

  public void setIdJugadores(List<Integer> idJugadores) {
    this.idJugadores = idJugadores;
  }

  public CrearPartidaBody modoDeJuego(ModoDeJuegoModel modoDeJuegoModel) {
    this.modoDeJuegoModel = modoDeJuegoModel;
    return this;
  }

  /**
   * Get modoDeJuego
   * @return modoDeJuego
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public ModoDeJuegoModel getModoDeJuego() {
    return modoDeJuegoModel;
  }

  public void setModoDeJuego(ModoDeJuegoModel modoDeJuegoModel) {
    this.modoDeJuegoModel = modoDeJuegoModel;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CrearPartidaBody crearPartidaBody = (CrearPartidaBody) o;
    return Objects.equals(this.idProvincia, crearPartidaBody.idProvincia) &&
        Objects.equals(this.cantidadMunicipios, crearPartidaBody.cantidadMunicipios) &&
        Objects.equals(this.idJugadores, crearPartidaBody.idJugadores) &&
        Objects.equals(this.modoDeJuegoModel, crearPartidaBody.modoDeJuegoModel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idProvincia, cantidadMunicipios, idJugadores, modoDeJuegoModel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CrearPartidaBody {\n");
    
    sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
    sb.append("    cantidadMunicipios: ").append(toIndentedString(cantidadMunicipios)).append("\n");
    sb.append("    idJugadores: ").append(toIndentedString(idJugadores)).append("\n");
    sb.append("    modoDeJuego: ").append(toIndentedString(modoDeJuegoModel)).append("\n");
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
