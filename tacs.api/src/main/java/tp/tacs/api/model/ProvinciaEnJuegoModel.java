package tp.tacs.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Provincia en juego.
 */
@ApiModel(description = "Provincia en juego.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:39.417-03:00[America/Buenos_Aires]")
public class ProvinciaEnJuegoModel {
  @JsonProperty("municipios")
  @Valid
  private List<MunicipioEnJuegoModel> municipios = new ArrayList<MunicipioEnJuegoModel>();

  public ProvinciaEnJuegoModel municipios(List<MunicipioEnJuegoModel> municipios) {
    this.municipios = municipios;
    return this;
  }

  public ProvinciaEnJuegoModel addMunicipiosItem(MunicipioEnJuegoModel municipiosItem) {
    this.municipios.add(municipiosItem);
    return this;
  }

  /**
   * Municipios en juego que contiene la provincia.
   * @return municipios
  **/
  @ApiModelProperty(required = true, value = "Municipios en juego que contiene la provincia.")
      @NotNull
    @Valid
    public List<MunicipioEnJuegoModel> getMunicipios() {
    return municipios;
  }

  public void setMunicipios(List<MunicipioEnJuegoModel> municipios) {
    this.municipios = municipios;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProvinciaEnJuegoModel provinciaEnJuegoModel = (ProvinciaEnJuegoModel) o;
    return Objects.equals(this.municipios, provinciaEnJuegoModel.municipios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(municipios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProvinciaEnJuego {\n");
    
    sb.append("    municipios: ").append(toIndentedString(municipios)).append("\n");
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
