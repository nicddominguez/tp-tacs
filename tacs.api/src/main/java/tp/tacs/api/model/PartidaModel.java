package tp.tacs.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.threeten.bp.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Una partida
 */
@ApiModel(description = "Una partida")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:39.417-03:00[America/Buenos_Aires]")
public class PartidaModel {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("provincia")
  private ProvinciaModel provinciaModel = null;

  @JsonProperty("cantidadMunicipios")
  private Integer cantidadMunicipios = null;

  @JsonProperty("estado")
  private EstadoDeJuegoModel estado = null;

  @JsonProperty("jugadores")
  @Valid
  private List<UsuarioModel> jugadores = new ArrayList<UsuarioModel>();

  @JsonProperty("modoDeJuego")
  private ModoDeJuegoModel modoDeJuegoModel = null;

  @JsonProperty("fecha")
  private LocalDate fecha = null;

  @JsonProperty("informacionDeJuego")
  private DatosDeJuegoModel informacionDeJuego = null;

  @JsonProperty("idGanador")
  private String idGanador = null;

  public PartidaModel id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Id de la partida
   * @return id
  **/
  @ApiModelProperty(required = true, value = "Id de la partida")
      @NotNull

    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PartidaModel provincia(ProvinciaModel provinciaModel) {
    this.provinciaModel = provinciaModel;
    return this;
  }

  /**
   * Get provincia
   * @return provincia
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public ProvinciaModel getProvincia() {
    return provinciaModel;
  }

  public void setProvincia(ProvinciaModel provinciaModel) {
    this.provinciaModel = provinciaModel;
  }

  public PartidaModel cantidadMunicipios(Integer cantidadMunicipios) {
    this.cantidadMunicipios = cantidadMunicipios;
    return this;
  }

  /**
   * Cantidad de municipios que se usan
   * @return cantidadMunicipios
  **/
  @ApiModelProperty(required = true, value = "Cantidad de municipios que se usan")
      @NotNull

    @Valid
    public Integer getCantidadMunicipios() {
    return cantidadMunicipios;
  }

  public void setCantidadMunicipios(Integer cantidadMunicipios) {
    this.cantidadMunicipios = cantidadMunicipios;
  }

  public PartidaModel estado(EstadoDeJuegoModel estado) {
    this.estado = estado;
    return this;
  }

  /**
   * Get estado
   * @return estado
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public EstadoDeJuegoModel getEstado() {
    return estado;
  }

  public void setEstado(EstadoDeJuegoModel estado) {
    this.estado = estado;
  }

  public PartidaModel jugadores(List<UsuarioModel> jugadores) {
    this.jugadores = jugadores;
    return this;
  }

  public PartidaModel addJugadoresItem(UsuarioModel jugadoresItem) {
    this.jugadores.add(jugadoresItem);
    return this;
  }

  /**
   * Usuarios que juegan esta partida
   * @return jugadores
  **/
  @ApiModelProperty(required = true, value = "Usuarios que juegan esta partida")
      @NotNull
    @Valid
    public List<UsuarioModel> getJugadores() {
    return jugadores;
  }

  public void setJugadores(List<UsuarioModel> jugadores) {
    this.jugadores = jugadores;
  }

  public PartidaModel modoDeJuego(ModoDeJuegoModel modoDeJuegoModel) {
    this.modoDeJuegoModel = modoDeJuegoModel;
    return this;
  }

  /**
   * Get modoDeJuego
   * @return modoDeJuego
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public ModoDeJuegoModel getModoDeJuego() {
    return modoDeJuegoModel;
  }

  public void setModoDeJuego(ModoDeJuegoModel modoDeJuegoModel) {
    this.modoDeJuegoModel = modoDeJuegoModel;
  }

  public PartidaModel fecha(LocalDate fecha) {
    this.fecha = fecha;
    return this;
  }

  /**
   * Fecha de inicio de la partida
   * @return fecha
  **/
  @ApiModelProperty(required = true, value = "Fecha de inicio de la partida")
      @NotNull

    @Valid
    public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public PartidaModel informacionDeJuego(DatosDeJuegoModel informacionDeJuego) {
    this.informacionDeJuego = informacionDeJuego;
    return this;
  }

  /**
   * Get informacionDeJuego
   * @return informacionDeJuego
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public DatosDeJuegoModel getInformacionDeJuego() {
    return informacionDeJuego;
  }

  public void setInformacionDeJuego(DatosDeJuegoModel informacionDeJuego) {
    this.informacionDeJuego = informacionDeJuego;
  }

  public PartidaModel idGanador(String idGanador) {
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
    PartidaModel partidaModel = (PartidaModel) o;
    return Objects.equals(this.id, partidaModel.id) &&
        Objects.equals(this.provinciaModel, partidaModel.provinciaModel) &&
        Objects.equals(this.cantidadMunicipios, partidaModel.cantidadMunicipios) &&
        Objects.equals(this.estado, partidaModel.estado) &&
        Objects.equals(this.jugadores, partidaModel.jugadores) &&
        Objects.equals(this.modoDeJuegoModel, partidaModel.modoDeJuegoModel) &&
        Objects.equals(this.fecha, partidaModel.fecha) &&
        Objects.equals(this.informacionDeJuego, partidaModel.informacionDeJuego) &&
        Objects.equals(this.idGanador, partidaModel.idGanador);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, provinciaModel, cantidadMunicipios, estado, jugadores, modoDeJuegoModel, fecha, informacionDeJuego, idGanador);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Partida {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provinciaModel)).append("\n");
    sb.append("    cantidadMunicipios: ").append(toIndentedString(cantidadMunicipios)).append("\n");
    sb.append("    estado: ").append(toIndentedString(estado)).append("\n");
    sb.append("    jugadores: ").append(toIndentedString(jugadores)).append("\n");
    sb.append("    modoDeJuego: ").append(toIndentedString(modoDeJuegoModel)).append("\n");
    sb.append("    fecha: ").append(toIndentedString(fecha)).append("\n");
    sb.append("    informacionDeJuego: ").append(toIndentedString(informacionDeJuego)).append("\n");
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
