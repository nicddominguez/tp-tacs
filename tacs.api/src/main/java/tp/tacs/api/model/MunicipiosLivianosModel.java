package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;

/**
 * MunicipiosLivianosModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-10-06T22:54:33.699Z[GMT]")


public class MunicipiosLivianosModel   {
  @JsonProperty("id")
  private Long id = null;

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

  public MunicipiosLivianosModel id(Long id) {
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

  public MunicipiosLivianosModel gauchos(Long gauchos) {
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

  public MunicipiosLivianosModel puntosDeDefensa(Float puntosDeDefensa) {
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

  public MunicipiosLivianosModel produccionDeGauchos(Long produccionDeGauchos) {
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

  public MunicipiosLivianosModel modo(ModoDeMunicipioModel modo) {
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

  public MunicipiosLivianosModel estaBloqueado(Boolean estaBloqueado) {
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MunicipiosLivianosModel municipiosLivianosModel = (MunicipiosLivianosModel) o;
    return Objects.equals(this.id, municipiosLivianosModel.id) &&
        Objects.equals(this.gauchos, municipiosLivianosModel.gauchos) &&
        Objects.equals(this.puntosDeDefensa, municipiosLivianosModel.puntosDeDefensa) &&
        Objects.equals(this.produccionDeGauchos, municipiosLivianosModel.produccionDeGauchos) &&
        Objects.equals(this.modo, municipiosLivianosModel.modo) &&
        Objects.equals(this.estaBloqueado, municipiosLivianosModel.estaBloqueado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, gauchos, puntosDeDefensa, produccionDeGauchos, modo, estaBloqueado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MunicipiosLivianosModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    gauchos: ").append(toIndentedString(gauchos)).append("\n");
    sb.append("    puntosDeDefensa: ").append(toIndentedString(puntosDeDefensa)).append("\n");
    sb.append("    produccionDeGauchos: ").append(toIndentedString(produccionDeGauchos)).append("\n");
    sb.append("    modo: ").append(toIndentedString(modo)).append("\n");
    sb.append("    estaBloqueado: ").append(toIndentedString(estaBloqueado)).append("\n");
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
