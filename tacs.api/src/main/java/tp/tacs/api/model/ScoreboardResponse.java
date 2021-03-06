package tp.tacs.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ScoreboardResponse
 */
@Validated


public class ScoreboardResponse   {
  @JsonProperty("scoreboard")
  @Valid
  private List<EstadisticasDeUsuarioModel> scoreboard = null;

  @JsonProperty("cantidadUsuarios")
  private Long cantidadUsuarios = null;

  public ScoreboardResponse scoreboard(List<EstadisticasDeUsuarioModel> scoreboard) {
    this.scoreboard = scoreboard;
    return this;
  }

  public ScoreboardResponse addScoreboardItem(EstadisticasDeUsuarioModel scoreboardItem) {
    if (this.scoreboard == null) {
      this.scoreboard = new ArrayList<EstadisticasDeUsuarioModel>();
    }
    this.scoreboard.add(scoreboardItem);
    return this;
  }

  /**
   * Get scoreboard
   * @return scoreboard
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<EstadisticasDeUsuarioModel> getScoreboard() {
    return scoreboard;
  }

  public void setScoreboard(List<EstadisticasDeUsuarioModel> scoreboard) {
    this.scoreboard = scoreboard;
  }

  public ScoreboardResponse cantidadUsuarios(Long cantidadUsuarios) {
    this.cantidadUsuarios = cantidadUsuarios;
    return this;
  }

  /**
   * Cantidad total de usuarios existentes
   * @return cantidadUsuarios
  **/
  @ApiModelProperty(value = "Cantidad total de usuarios existentes")
  
    public Long getCantidadUsuarios() {
    return cantidadUsuarios;
  }

  public void setCantidadUsuarios(Long cantidadUsuarios) {
    this.cantidadUsuarios = cantidadUsuarios;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScoreboardResponse scoreboardResponse = (ScoreboardResponse) o;
    return Objects.equals(this.scoreboard, scoreboardResponse.scoreboard) &&
        Objects.equals(this.cantidadUsuarios, scoreboardResponse.cantidadUsuarios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scoreboard, cantidadUsuarios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScoreboardResponse {\n");
    
    sb.append("    scoreboard: ").append(toIndentedString(scoreboard)).append("\n");
    sb.append("    cantidadUsuarios: ").append(toIndentedString(cantidadUsuarios)).append("\n");
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
