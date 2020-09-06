package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ScoreboardResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-05T20:37:59.553716700-03:00[America/Buenos_Aires]")


public class ScoreboardResponse   {
  @JsonProperty("scoreboard")
  @Valid
  private List<EstadisticasDeUsuarioModel> scoreboard = null;

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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScoreboardResponse scoreboardResponse = (ScoreboardResponse) o;
    return Objects.equals(this.scoreboard, scoreboardResponse.scoreboard);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scoreboard);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScoreboardResponse {\n");
    
    sb.append("    scoreboard: ").append(toIndentedString(scoreboard)).append("\n");
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
