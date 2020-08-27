package tp.tacs.api.models;

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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-27T18:00:32.244-03:00[America/Buenos_Aires]")
public class CrearPartidaBody   {
  @JsonProperty("provincia")
  private Provincia provincia = null;

  @JsonProperty("cantidadMunicipios")
  private Integer cantidadMunicipios = null;

  @JsonProperty("jugadores")
  @Valid
  private List<Usuario> jugadores = null;

  @JsonProperty("modoDeJuego")
  private ModoDeJuego modoDeJuego = null;

  public CrearPartidaBody provincia(Provincia provincia) {
    this.provincia = provincia;
    return this;
  }

  /**
   * Get provincia
   * @return provincia
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public Provincia getProvincia() {
    return provincia;
  }

  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
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

  public CrearPartidaBody jugadores(List<Usuario> jugadores) {
    this.jugadores = jugadores;
    return this;
  }

  public CrearPartidaBody addJugadoresItem(Usuario jugadoresItem) {
    if (this.jugadores == null) {
      this.jugadores = new ArrayList<Usuario>();
    }
    this.jugadores.add(jugadoresItem);
    return this;
  }

  /**
   * Jugadores
   * @return jugadores
  **/
  @ApiModelProperty(value = "Jugadores")
      @Valid
    public List<Usuario> getJugadores() {
    return jugadores;
  }

  public void setJugadores(List<Usuario> jugadores) {
    this.jugadores = jugadores;
  }

  public CrearPartidaBody modoDeJuego(ModoDeJuego modoDeJuego) {
    this.modoDeJuego = modoDeJuego;
    return this;
  }

  /**
   * Get modoDeJuego
   * @return modoDeJuego
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public ModoDeJuego getModoDeJuego() {
    return modoDeJuego;
  }

  public void setModoDeJuego(ModoDeJuego modoDeJuego) {
    this.modoDeJuego = modoDeJuego;
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
    return Objects.equals(this.provincia, crearPartidaBody.provincia) &&
        Objects.equals(this.cantidadMunicipios, crearPartidaBody.cantidadMunicipios) &&
        Objects.equals(this.jugadores, crearPartidaBody.jugadores) &&
        Objects.equals(this.modoDeJuego, crearPartidaBody.modoDeJuego);
  }

  @Override
  public int hashCode() {
    return Objects.hash(provincia, cantidadMunicipios, jugadores, modoDeJuego);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CrearPartidaBody {\n");
    
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
    sb.append("    cantidadMunicipios: ").append(toIndentedString(cantidadMunicipios)).append("\n");
    sb.append("    jugadores: ").append(toIndentedString(jugadores)).append("\n");
    sb.append("    modoDeJuego: ").append(toIndentedString(modoDeJuego)).append("\n");
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
