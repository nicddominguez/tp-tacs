package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import tp.tacs.api.model.ProvinciaModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListarProvinciasResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-10T19:10:01.693073800-03:00[America/Buenos_Aires]")


public class ListarProvinciasResponse   {
  @JsonProperty("provincias")
  @Valid
  private List<ProvinciaModel> provincias = null;

  public ListarProvinciasResponse provincias(List<ProvinciaModel> provincias) {
    this.provincias = provincias;
    return this;
  }

  public ListarProvinciasResponse addProvinciasItem(ProvinciaModel provinciasItem) {
    if (this.provincias == null) {
      this.provincias = new ArrayList<ProvinciaModel>();
    }
    this.provincias.add(provinciasItem);
    return this;
  }

  /**
   * Get provincias
   * @return provincias
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<ProvinciaModel> getProvincias() {
    return provincias;
  }

  public void setProvincias(List<ProvinciaModel> provincias) {
    this.provincias = provincias;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListarProvinciasResponse listarProvinciasResponse = (ListarProvinciasResponse) o;
    return Objects.equals(this.provincias, listarProvinciasResponse.provincias);
  }

  @Override
  public int hashCode() {
    return Objects.hash(provincias);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListarProvinciasResponse {\n");
    
    sb.append("    provincias: ").append(toIndentedString(provincias)).append("\n");
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
