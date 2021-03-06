package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Par de coordenadas en el mundo.
 */
@ApiModel(description = "Par de coordenadas en el mundo.")
@Validated


public class CoordenadasModel   {
  @JsonProperty("lat")
  private Float lat = null;

  @JsonProperty("lon")
  private Float lon = null;

  public CoordenadasModel lat(Float lat) {
    this.lat = lat;
    return this;
  }

  /**
   * Latitud
   * @return lat
  **/
  @ApiModelProperty(required = true, value = "Latitud")
      @NotNull

    public Float getLat() {
    return lat;
  }

  public void setLat(Float lat) {
    this.lat = lat;
  }

  public CoordenadasModel lon(Float lon) {
    this.lon = lon;
    return this;
  }

  /**
   * Longitud
   * @return lon
  **/
  @ApiModelProperty(required = true, value = "Longitud")
      @NotNull

    public Float getLon() {
    return lon;
  }

  public void setLon(Float lon) {
    this.lon = lon;
  }


  @Override
  public boolean equals(Object o) {
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
