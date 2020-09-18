package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Par de coordenadas en el mundo.
 */
@ApiModel(description = "Par de coordenadas en el mundo.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-17T12:33:52.369403200-03:00[America/Buenos_Aires]")


public class CoordenadasModel   {
  @JsonProperty("lat")
  private Long lat = null;

  @JsonProperty("lon")
  private Long lon = null;

  public CoordenadasModel lat(Long lat) {
    this.lat = lat;
    return this;
  }

  /**
   * Latitud
   * @return lat
  **/
  @ApiModelProperty(required = true, value = "Latitud")
      @NotNull

    public Long getLat() {
    return lat;
  }

  public void setLat(Long lat) {
    this.lat = lat;
  }

  public CoordenadasModel lon(Long lon) {
    this.lon = lon;
    return this;
  }

  /**
   * Longitud
   * @return lon
  **/
  @ApiModelProperty(required = true, value = "Longitud")
      @NotNull

    public Long getLon() {
    return lon;
  }

  public void setLon(Long lon) {
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
    CoordenadasModel coordenadasModel = (CoordenadasModel) o;
    return Objects.equals(this.lat, coordenadasModel.lat) &&
        Objects.equals(this.lon, coordenadasModel.lon);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lat, lon);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoordenadasModel {\n");
    
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
