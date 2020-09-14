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
 * SimularAtacarMunicipioResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-14T19:00:38.696940-03:00[America/Buenos_Aires]")


public class SimularAtacarMunicipioResponse   {
  @JsonProperty("exitoso")
  private Boolean exitoso = null;

  public SimularAtacarMunicipioResponse exitoso(Boolean exitoso) {
    this.exitoso = exitoso;
    return this;
  }

  /**
   * Si el ataque fue exitoso en la simulación
   * @return exitoso
  **/
  @ApiModelProperty(value = "Si el ataque fue exitoso en la simulación")
  
    public Boolean isExitoso() {
    return exitoso;
  }

  public void setExitoso(Boolean exitoso) {
    this.exitoso = exitoso;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SimularAtacarMunicipioResponse simularAtacarMunicipioResponse = (SimularAtacarMunicipioResponse) o;
    return Objects.equals(this.exitoso, simularAtacarMunicipioResponse.exitoso);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exitoso);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SimularAtacarMunicipioResponse {\n");
    
    sb.append("    exitoso: ").append(toIndentedString(exitoso)).append("\n");
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
