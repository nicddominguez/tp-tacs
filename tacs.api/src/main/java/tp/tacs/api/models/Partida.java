package tp.tacs.api.models;

import java.math.BigDecimal;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-27T18:00:32.244-03:00[America/Buenos_Aires]")
public class Partida   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("provincia")
  private Provincia provincia = null;

  @JsonProperty("cantidadMunicipios")
  private BigDecimal cantidadMunicipios = null;

  @JsonProperty("estado")
  private EstadoDeJuego estado = null;

  @JsonProperty("jugadores")
  @Valid
  private List<Usuario> jugadores = new ArrayList<Usuario>();

  @JsonProperty("modoDeJuego")
  private ModoDeJuego modoDeJuego = null;

  @JsonProperty("fecha")
  private LocalDate fecha = null;

  @JsonProperty("informacionDeJuego")
  private DatosDeJuego informacionDeJuego = null;

  @JsonProperty("idGanador")
  private String idGanador = null;

  public Partida id(Integer id) {
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

  public Partida provincia(Provincia provincia) {
    this.provincia = provincia;
    return this;
  }

  /**
   * Get provincia
   * @return provincia
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public Provincia getProvincia() {
    return provincia;
  }

  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
  }

  public Partida cantidadMunicipios(BigDecimal cantidadMunicipios) {
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
    public BigDecimal getCantidadMunicipios() {
    return cantidadMunicipios;
  }

  public void setCantidadMunicipios(BigDecimal cantidadMunicipios) {
    this.cantidadMunicipios = cantidadMunicipios;
  }

  public Partida estado(EstadoDeJuego estado) {
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
    public EstadoDeJuego getEstado() {
    return estado;
  }

  public void setEstado(EstadoDeJuego estado) {
    this.estado = estado;
  }

  public Partida jugadores(List<Usuario> jugadores) {
    this.jugadores = jugadores;
    return this;
  }

  public Partida addJugadoresItem(Usuario jugadoresItem) {
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
    public List<Usuario> getJugadores() {
    return jugadores;
  }

  public void setJugadores(List<Usuario> jugadores) {
    this.jugadores = jugadores;
  }

  public Partida modoDeJuego(ModoDeJuego modoDeJuego) {
    this.modoDeJuego = modoDeJuego;
    return this;
  }

  /**
   * Get modoDeJuego
   * @return modoDeJuego
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public ModoDeJuego getModoDeJuego() {
    return modoDeJuego;
  }

  public void setModoDeJuego(ModoDeJuego modoDeJuego) {
    this.modoDeJuego = modoDeJuego;
  }

  public Partida fecha(LocalDate fecha) {
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

  public Partida informacionDeJuego(DatosDeJuego informacionDeJuego) {
    this.informacionDeJuego = informacionDeJuego;
    return this;
  }

  /**
   * Get informacionDeJuego
   * @return informacionDeJuego
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public DatosDeJuego getInformacionDeJuego() {
    return informacionDeJuego;
  }

  public void setInformacionDeJuego(DatosDeJuego informacionDeJuego) {
    this.informacionDeJuego = informacionDeJuego;
  }

  public Partida idGanador(String idGanador) {
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
    Partida partida = (Partida) o;
    return Objects.equals(this.id, partida.id) &&
        Objects.equals(this.provincia, partida.provincia) &&
        Objects.equals(this.cantidadMunicipios, partida.cantidadMunicipios) &&
        Objects.equals(this.estado, partida.estado) &&
        Objects.equals(this.jugadores, partida.jugadores) &&
        Objects.equals(this.modoDeJuego, partida.modoDeJuego) &&
        Objects.equals(this.fecha, partida.fecha) &&
        Objects.equals(this.informacionDeJuego, partida.informacionDeJuego) &&
        Objects.equals(this.idGanador, partida.idGanador);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, provincia, cantidadMunicipios, estado, jugadores, modoDeJuego, fecha, informacionDeJuego, idGanador);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Partida {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
    sb.append("    cantidadMunicipios: ").append(toIndentedString(cantidadMunicipios)).append("\n");
    sb.append("    estado: ").append(toIndentedString(estado)).append("\n");
    sb.append("    jugadores: ").append(toIndentedString(jugadores)).append("\n");
    sb.append("    modoDeJuego: ").append(toIndentedString(modoDeJuego)).append("\n");
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
