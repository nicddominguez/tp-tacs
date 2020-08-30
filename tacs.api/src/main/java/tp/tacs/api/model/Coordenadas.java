package tp.tacs.api.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Par de coordenadas en el mundo.
 */
@ApiModel(description = "Par de coordenadas en el mundo.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:39.417-03:00[America/Buenos_Aires]")
public class Coordenadas   {
  @JsonProperty("lat")
  private BigDecimal lat = null;

  @JsonProperty("lon")
  private BigDecimal lon = null;

  public Coordenadas lat(BigDecimal lat) {
    this.lat = lat;
    return this;
  }

  /**
   * Latitud
   * @return lat
  **/
  @ApiModelProperty(required = true, value = "Latitud")
      @NotNull

    @Valid
    public BigDecimal getLat() {
    return lat;
  }

  public void setLat(BigDecimal lat) {
    this.lat = lat;
  }

  public Coordenadas lon(BigDecimal lon) {
    this.lon = lon;
    return this;
  }

  /**
   * Longitud
   * @return lon
  **/
  @ApiModelProperty(required = true, value = "Longitud")
      @NotNull

    @Valid
    public BigDecimal getLon() {
    return lon;
  }

  public void setLon(BigDecimal lon) {
    this.lon = lon;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coordenadas coordenadas = (Coordenadas) o;
    return Objects.equals(this.lat, coordenadas.lat) &&
        Objects.equals(this.lon, coordenadas.lon);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lat, lon);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Coordenadas {\n");
    
    sb.append("    lat: ").append(toIndentedString(lat)).append("\n");
    sb.append("    lon: ").append(toIndentedString(lon)).append("\n");
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
