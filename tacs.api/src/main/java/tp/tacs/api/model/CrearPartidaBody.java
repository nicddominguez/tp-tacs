package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CrearPartidaBody
 */
@Validated


public class CrearPartidaBody   {
  @JsonProperty("idProvincia")
  private Long idProvincia = null;

  @JsonProperty("cantidadMunicipios")
  private Long cantidadMunicipios = null;

  @JsonProperty("idJugadores")
  @Valid
  private List<String> idJugadores = null;

  @JsonProperty("modoDeJuego")
  private ModoDeJuegoModel modoDeJuego = null;

  public CrearPartidaBody idProvincia(Long idProvincia) {
    this.idProvincia = idProvincia;
    return this;
  }

  /**
   * Id de la provincia donde se juega
   * @return idProvincia
  **/
  @ApiModelProperty(value = "Id de la provincia donde se juega")
  
    public Long getIdProvincia() {
    return idProvincia;
  }

  public void setIdProvincia(Long idProvincia) {
    this.idProvincia = idProvincia;
  }

  public CrearPartidaBody cantidadMunicipios(Long cantidadMunicipios) {
    this.cantidadMunicipios = cantidadMunicipios;
    return this;
  }

  /**
   * Cantidad de municipios que se usan
   * @return cantidadMunicipios
  **/
  @ApiModelProperty(value = "Cantidad de municipios que se usan")
  
    public Long getCantidadMunicipios() {
    return cantidadMunicipios;
  }

  public void setCantidadMunicipios(Long cantidadMunicipios) {
    this.cantidadMunicipios = cantidadMunicipios;
  }

  public CrearPartidaBody idJugadores(List<String> idJugadores) {
    this.idJugadores = idJugadores;
    return this;
  }

  public CrearPartidaBody addIdJugadoresItem(String idJugadoresItem) {
    if (this.idJugadores == null) {
      this.idJugadores = new ArrayList<String>();
    }
    this.idJugadores.add(idJugadoresItem);
    return this;
  }

  /**
   * Jugadores
   * @return idJugadores
  **/
  @ApiModelProperty(value = "Jugadores")
  
    public List<String> getIdJugadores() {
    return idJugadores;
  }

  public void setIdJugadores(List<String> idJugadores) {
    this.idJugadores = idJugadores;
  }

  public CrearPartidaBody modoDeJuego(ModoDeJuegoModel modoDeJuego) {
    this.modoDeJuego = modoDeJuego;
    return this;
  }

  /**
   * Get modoDeJuego
   * @return modoDeJuego
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public ModoDeJuegoModel getModoDeJuego() {
    return modoDeJuego;
  }

  public void setModoDeJuego(ModoDeJuegoModel modoDeJuego) {
    this.modoDeJuego = modoDeJuego;
  }


  @Override
  public boolean equals(Object o) {
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
        Objects.equals(this.modoDeJuego, crearPartidaBody.modoDeJuego);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idProvincia, cantidadMunicipios, idJugadores, modoDeJuego);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CrearPartidaBody {\n");
    
    sb.append("    idProvincia: ").append(toIndentedString(idProvincia)).append("\n");
    sb.append("    cantidadMunicipios: ").append(toIndentedString(cantidadMunicipios)).append("\n");
    sb.append("    idJugadores: ").append(toIndentedString(idJugadores)).append("\n");
    sb.append("    modoDeJuego: ").append(toIndentedString(modoDeJuego)).append("\n");
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
