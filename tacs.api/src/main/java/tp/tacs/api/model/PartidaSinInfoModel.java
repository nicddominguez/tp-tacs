package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tp.tacs.api.model.EstadoDeJuegoModel;
import tp.tacs.api.model.ModoDeJuegoModel;
import tp.tacs.api.model.ProvinciaModel;
import tp.tacs.api.model.UsuarioModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PartidaSinInfoModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-17T12:33:52.369403200-03:00[America/Buenos_Aires]")


public class PartidaSinInfoModel   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("provincia")
  private ProvinciaModel provincia = null;

  @JsonProperty("cantidadMunicipios")
  private Long cantidadMunicipios = null;

  @JsonProperty("estado")
  private EstadoDeJuegoModel estado = null;

  @JsonProperty("jugadores")
  @Valid
  private List<UsuarioModel> jugadores = null;

  @JsonProperty("modoDeJuego")
  private ModoDeJuegoModel modoDeJuego = null;

  @JsonProperty("fecha")
  private Date fecha = null;

  @JsonProperty("idGanador")
  private String idGanador = null;

  public PartidaSinInfoModel id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Id de la partida
   * @return id
  **/
  @ApiModelProperty(value = "Id de la partida")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PartidaSinInfoModel provincia(ProvinciaModel provincia) {
    this.provincia = provincia;
    return this;
  }

  /**
   * Get provincia
   * @return provincia
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public ProvinciaModel getProvincia() {
    return provincia;
  }

  public void setProvincia(ProvinciaModel provincia) {
    this.provincia = provincia;
  }

  public PartidaSinInfoModel cantidadMunicipios(Long cantidadMunicipios) {
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

  public PartidaSinInfoModel estado(EstadoDeJuegoModel estado) {
    this.estado = estado;
    return this;
  }

  /**
   * Get estado
   * @return estado
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public EstadoDeJuegoModel getEstado() {
    return estado;
  }

  public void setEstado(EstadoDeJuegoModel estado) {
    this.estado = estado;
  }

  public PartidaSinInfoModel jugadores(List<UsuarioModel> jugadores) {
    this.jugadores = jugadores;
    return this;
  }

  public PartidaSinInfoModel addJugadoresItem(UsuarioModel jugadoresItem) {
    if (this.jugadores == null) {
      this.jugadores = new ArrayList<UsuarioModel>();
    }
    this.jugadores.add(jugadoresItem);
    return this;
  }

  /**
   * Usuarios que juegan esta partida
   * @return jugadores
  **/
  @ApiModelProperty(value = "Usuarios que juegan esta partida")
      @Valid
    public List<UsuarioModel> getJugadores() {
    return jugadores;
  }

  public void setJugadores(List<UsuarioModel> jugadores) {
    this.jugadores = jugadores;
  }

  public PartidaSinInfoModel modoDeJuego(ModoDeJuegoModel modoDeJuego) {
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

  public PartidaSinInfoModel fecha(Date fecha) {
    this.fecha = fecha;
    return this;
  }

  /**
   * Fecha de inicio de la partida
   * @return fecha
  **/
  @ApiModelProperty(value = "Fecha de inicio de la partida")
  
    @Valid
    public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public PartidaSinInfoModel idGanador(String idGanador) {
    this.idGanador = idGanador;
    return this;
  }

  /**
   * Id del usuario ganador
   * @return idGanador
  **/
  @ApiModelProperty(value = "Id del usuario ganador")
  
    public String getIdGanador() {
    return idGanador;
  }

  public void setIdGanador(String idGanador) {
    this.idGanador = idGanador;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartidaSinInfoModel partidaSinInfoModel = (PartidaSinInfoModel) o;
    return Objects.equals(this.id, partidaSinInfoModel.id) &&
        Objects.equals(this.provincia, partidaSinInfoModel.provincia) &&
        Objects.equals(this.cantidadMunicipios, partidaSinInfoModel.cantidadMunicipios) &&
        Objects.equals(this.estado, partidaSinInfoModel.estado) &&
        Objects.equals(this.jugadores, partidaSinInfoModel.jugadores) &&
        Objects.equals(this.modoDeJuego, partidaSinInfoModel.modoDeJuego) &&
        Objects.equals(this.fecha, partidaSinInfoModel.fecha) &&
        Objects.equals(this.idGanador, partidaSinInfoModel.idGanador);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, provincia, cantidadMunicipios, estado, jugadores, modoDeJuego, fecha, idGanador);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartidaSinInfoModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
    sb.append("    cantidadMunicipios: ").append(toIndentedString(cantidadMunicipios)).append("\n");
    sb.append("    estado: ").append(toIndentedString(estado)).append("\n");
    sb.append("    jugadores: ").append(toIndentedString(jugadores)).append("\n");
    sb.append("    modoDeJuego: ").append(toIndentedString(modoDeJuego)).append("\n");
    sb.append("    fecha: ").append(toIndentedString(fecha)).append("\n");
    sb.append("    idGanador: ").append(toIndentedString(idGanador)).append("\n");
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
