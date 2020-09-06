package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import tp.tacs.api.model.PartidaModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ListarPartidasResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-05T20:37:59.553716700-03:00[America/Buenos_Aires]")


public class ListarPartidasResponse   {
  @JsonProperty("partidas")
  @Valid
  private List<PartidaModel> partidas = null;

  public ListarPartidasResponse partidas(List<PartidaModel> partidas) {
    this.partidas = partidas;
    return this;
  }

  public ListarPartidasResponse addPartidasItem(PartidaModel partidasItem) {
    if (this.partidas == null) {
      this.partidas = new ArrayList<PartidaModel>();
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
    public List<PartidaModel> getPartidas() {
    return partidas;
  }

  public void setPartidas(List<PartidaModel> partidas) {
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
