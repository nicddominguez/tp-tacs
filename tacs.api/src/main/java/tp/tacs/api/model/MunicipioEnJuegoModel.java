package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tp.tacs.api.model.CoordenadasModel;
import tp.tacs.api.model.ModoDeMunicipioModel;
import tp.tacs.api.model.UsuarioModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Municipio en juego.
 */
@ApiModel(description = "Municipio en juego.")
@Validated


public class MunicipioEnJuegoModel   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  @JsonProperty("ubicacion")
  private CoordenadasModel ubicacion = null;

  @JsonProperty("altura")
  private Long altura = null;

  @JsonProperty("duenio")
  private UsuarioModel duenio = null;

  @JsonProperty("gauchos")
  private Long gauchos = null;

  @JsonProperty("puntosDeDefensa")
  private Float puntosDeDefensa = null;

  @JsonProperty("produccionDeGauchos")
  private Long produccionDeGauchos = null;

  @JsonProperty("modo")
  private ModoDeMunicipioModel modo = null;

  @JsonProperty("estaBloqueado")
  private Boolean estaBloqueado = null;

  @JsonProperty("urlImagen")
  private String urlImagen = null;

  public MunicipioEnJuegoModel id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Id del municipio.
   * @return id
  **/
  @ApiModelProperty(value = "Id del municipio.")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
  @ApiModelProperty(value = "Nombre del municipio.")
  
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
  @ApiModelProperty(value = "")
  
    @Valid
    public CoordenadasModel getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(CoordenadasModel ubicacion) {
    this.ubicacion = ubicacion;
  }

  public MunicipioEnJuegoModel altura(Long altura) {
    this.altura = altura;
    return this;
  }

  /**
   * Altura en metros del centroide del municipio
   * @return altura
  **/
  @ApiModelProperty(value = "Altura en metros del centroide del municipio")
  
    public Long getAltura() {
    return altura;
  }

  public void setAltura(Long altura) {
    this.altura = altura;
  }

  public MunicipioEnJuegoModel duenio(UsuarioModel duenio) {
    this.duenio = duenio;
    return this;
  }

  /**
   * Get duenio
   * @return duenio
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public UsuarioModel getDuenio() {
    return duenio;
  }

  public void setDuenio(UsuarioModel duenio) {
    this.duenio = duenio;
  }

  public MunicipioEnJuegoModel gauchos(Long gauchos) {
    this.gauchos = gauchos;
    return this;
  }

  /**
   * Cantidad de gauchos en el municipio
   * @return gauchos
  **/
  @ApiModelProperty(value = "Cantidad de gauchos en el municipio")
  
    public Long getGauchos() {
    return gauchos;
  }

  public void setGauchos(Long gauchos) {
    this.gauchos = gauchos;
  }

  public MunicipioEnJuegoModel puntosDeDefensa(Float puntosDeDefensa) {
    this.puntosDeDefensa = puntosDeDefensa;
    return this;
  }

  /**
   * Get puntosDeDefensa
   * @return puntosDeDefensa
  **/
  @ApiModelProperty(value = "")
  
    public Float getPuntosDeDefensa() {
    return puntosDeDefensa;
  }

  public void setPuntosDeDefensa(Float puntosDeDefensa) {
    this.puntosDeDefensa = puntosDeDefensa;
  }

  public MunicipioEnJuegoModel produccionDeGauchos(Long produccionDeGauchos) {
    this.produccionDeGauchos = produccionDeGauchos;
    return this;
  }

  /**
   * Get produccionDeGauchos
   * @return produccionDeGauchos
  **/
  @ApiModelProperty(value = "")
  
    public Long getProduccionDeGauchos() {
    return produccionDeGauchos;
  }

  public void setProduccionDeGauchos(Long produccionDeGauchos) {
    this.produccionDeGauchos = produccionDeGauchos;
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

  public MunicipioEnJuegoModel urlImagen(String urlImagen) {
    this.urlImagen = urlImagen;
    return this;
  }

  /**
   * URL a una imagen del municipio
   * @return urlImagen
  **/
  @ApiModelProperty(value = "URL a una imagen del municipio")
  
    public String getUrlImagen() {
    return urlImagen;
  }

  public void setUrlImagen(String urlImagen) {
    this.urlImagen = urlImagen;
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
        Objects.equals(this.altura, municipioEnJuegoModel.altura) &&
        Objects.equals(this.duenio, municipioEnJuegoModel.duenio) &&
        Objects.equals(this.gauchos, municipioEnJuegoModel.gauchos) &&
        Objects.equals(this.puntosDeDefensa, municipioEnJuegoModel.puntosDeDefensa) &&
        Objects.equals(this.produccionDeGauchos, municipioEnJuegoModel.produccionDeGauchos) &&
        Objects.equals(this.modo, municipioEnJuegoModel.modo) &&
        Objects.equals(this.estaBloqueado, municipioEnJuegoModel.estaBloqueado) &&
        Objects.equals(this.urlImagen, municipioEnJuegoModel.urlImagen);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, ubicacion, altura, duenio, gauchos, puntosDeDefensa, produccionDeGauchos, modo, estaBloqueado, urlImagen);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MunicipioEnJuegoModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    ubicacion: ").append(toIndentedString(ubicacion)).append("\n");
    sb.append("    altura: ").append(toIndentedString(altura)).append("\n");
    sb.append("    duenio: ").append(toIndentedString(duenio)).append("\n");
    sb.append("    gauchos: ").append(toIndentedString(gauchos)).append("\n");
    sb.append("    puntosDeDefensa: ").append(toIndentedString(puntosDeDefensa)).append("\n");
    sb.append("    produccionDeGauchos: ").append(toIndentedString(produccionDeGauchos)).append("\n");
    sb.append("    modo: ").append(toIndentedString(modo)).append("\n");
    sb.append("    estaBloqueado: ").append(toIndentedString(estaBloqueado)).append("\n");
    sb.append("    urlImagen: ").append(toIndentedString(urlImagen)).append("\n");
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
