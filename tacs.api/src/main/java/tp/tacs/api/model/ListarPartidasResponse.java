package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ListarPartidasResponse
 */
@Validated


public class ListarPartidasResponse   {
  @JsonProperty("partidas")
  @Valid
  private List<PartidaSinInfoModel> partidas = null;

  public ListarPartidasResponse partidas(List<PartidaSinInfoModel> partidas) {
    this.partidas = partidas;
    return this;
  }

  public ListarPartidasResponse addPartidasItem(PartidaSinInfoModel partidasItem) {
    if (this.partidas == null) {
      this.partidas = new ArrayList<PartidaSinInfoModel>();
    }
    this.partidas.add(partidasItem);
    return this;
  }

  /**
   * Get partidas
   * @return partidas
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<PartidaSinInfoModel> getPartidas() {
    return partidas;
  }

  public void setPartidas(List<PartidaSinInfoModel> partidas) {
    this.partidas = partidas;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListarPartidasResponse listarPartidasResponse = (ListarPartidasResponse) o;
    return Objects.equals(this.partidas, listarPartidasResponse.partidas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partidas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListarPartidasResponse {\n");
    
    sb.append("    partidas: ").append(toIndentedString(partidas)).append("\n");
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
