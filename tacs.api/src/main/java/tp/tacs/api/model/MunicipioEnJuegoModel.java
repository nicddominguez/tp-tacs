package tp.tacs.api.model;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Municipio en juego.
 */
@ApiModel(description = "Municipio en juego.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:39.417-03:00[America/Buenos_Aires]")
public class MunicipioEnJuegoModel {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("ubicacion")
  private CoordenadasModel ubicacion = null;

  @JsonProperty("duenio")
  private UsuarioModel duenio = null;

  @JsonProperty("gauchos")
  private Integer gauchos = null;

  @JsonProperty("modo")
  private ModoDeMunicipioModel modo = null;

  @JsonProperty("estaBloqueado")
  private Boolean estaBloqueado = null;

  public MunicipioEnJuegoModel id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Id del municipio.
   * @return id
  **/
  @ApiModelProperty(required = true, value = "Id del municipio.")
      @NotNull

    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public MunicipioEnJuegoModel nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Nombre del municipio.
   * @return nombre
  **/
  @ApiModelProperty(required = true, value = "Nombre del municipio.")
      @NotNull

    public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public MunicipioEnJuegoModel ubicacion(CoordenadasModel ubicacion) {
    this.ubicacion = ubicacion;
    return this;
  }

  /**
   * Get ubicacion
   * @return ubicacion
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public CoordenadasModel getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(CoordenadasModel ubicacion) {
    this.ubicacion = ubicacion;
  }

  public MunicipioEnJuegoModel duenio(UsuarioModel duenio) {
    this.duenio = duenio;
    return this;
  }

  /**
   * Get duenio
   * @return duenio
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    @Valid
    public UsuarioModel getDuenio() {
    return duenio;
  }

  public void setDuenio(UsuarioModel duenio) {
    this.duenio = duenio;
  }

  public MunicipioEnJuegoModel gauchos(Integer gauchos) {
    this.gauchos = gauchos;
    return this;
  }

  /**
   * Cantidad de gauchos en el municipio.
   * @return gauchos
  **/
  @ApiModelProperty(required = true, value = "Cantidad de gauchos en el municipio.")
      @NotNull

    public Integer getGauchos() {
    return gauchos;
  }

  public void setGauchos(Integer gauchos) {
    this.gauchos = gauchos;
  }

  public MunicipioEnJuegoModel modo(ModoDeMunicipioModel modo) {
    this.modo = modo;
    return this;
  }

  /**
   * Get modo
   * @return modo
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public ModoDeMunicipioModel getModo() {
    return modo;
  }

  public void setModo(ModoDeMunicipioModel modo) {
    this.modo = modo;
  }

  public MunicipioEnJuegoModel estaBloqueado(Boolean estaBloqueado) {
    this.estaBloqueado = estaBloqueado;
    return this;
  }

  /**
   * Si el municipio está bloqueado.
   * @return estaBloqueado
  **/
  @ApiModelProperty(value = "Si el municipio está bloqueado.")
  
    public Boolean isEstaBloqueado() {
    return estaBloqueado;
  }

  public void setEstaBloqueado(Boolean estaBloqueado) {
    this.estaBloqueado = estaBloqueado;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MunicipioEnJuegoModel municipioEnJuegoModel = (MunicipioEnJuegoModel) o;
    return Objects.equals(this.id, municipioEnJuegoModel.id) &&
        Objects.equals(this.nombre, municipioEnJuegoModel.nombre) &&
        Objects.equals(this.ubicacion, municipioEnJuegoModel.ubicacion) &&
        Objects.equals(this.duenio, municipioEnJuegoModel.duenio) &&
        Objects.equals(this.gauchos, municipioEnJuegoModel.gauchos) &&
        Objects.equals(this.modo, municipioEnJuegoModel.modo) &&
        Objects.equals(this.estaBloqueado, municipioEnJuegoModel.estaBloqueado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, ubicacion, duenio, gauchos, modo, estaBloqueado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MunicipioEnJuego {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    ubicacion: ").append(toIndentedString(ubicacion)).append("\n");
    sb.append("    duenio: ").append(toIndentedString(duenio)).append("\n");
    sb.append("    gauchos: ").append(toIndentedString(gauchos)).append("\n");
    sb.append("    modo: ").append(toIndentedString(modo)).append("\n");
    sb.append("    estaBloqueado: ").append(toIndentedString(estaBloqueado)).append("\n");
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
