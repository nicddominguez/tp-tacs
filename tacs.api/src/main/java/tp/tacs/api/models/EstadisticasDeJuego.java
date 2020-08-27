package tp.tacs.api.models;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * EstadisticasDeJuego
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-27T18:00:32.244-03:00[America/Buenos_Aires]")
public class EstadisticasDeJuego   {
  @JsonProperty("gamesCreated")
  private Integer gamesCreated = null;

  @JsonProperty("gamesInProgress")
  private Integer gamesInProgress = null;

  @JsonProperty("gamesFinished")
  private Integer gamesFinished = null;

  @JsonProperty("gamesCanceled")
  private Integer gamesCanceled = null;

  public EstadisticasDeJuego gamesCreated(Integer gamesCreated) {
    this.gamesCreated = gamesCreated;
    return this;
  }

  /**
   * Get gamesCreated
   * @return gamesCreated
  **/
  @ApiModelProperty(value = "")
  
    public Integer getGamesCreated() {
    return gamesCreated;
  }

  public void setGamesCreated(Integer gamesCreated) {
    this.gamesCreated = gamesCreated;
  }

  public EstadisticasDeJuego gamesInProgress(Integer gamesInProgress) {
    this.gamesInProgress = gamesInProgress;
    return this;
  }

  /**
   * Get gamesInProgress
   * @return gamesInProgress
  **/
  @ApiModelProperty(value = "")
  
    public Integer getGamesInProgress() {
    return gamesInProgress;
  }

  public void setGamesInProgress(Integer gamesInProgress) {
    this.gamesInProgress = gamesInProgress;
  }

  public EstadisticasDeJuego gamesFinished(Integer gamesFinished) {
    this.gamesFinished = gamesFinished;
    return this;
  }

  /**
   * Get gamesFinished
   * @return gamesFinished
  **/
  @ApiModelProperty(value = "")
  
    public Integer getGamesFinished() {
    return gamesFinished;
  }

  public void setGamesFinished(Integer gamesFinished) {
    this.gamesFinished = gamesFinished;
  }

  public EstadisticasDeJuego gamesCanceled(Integer gamesCanceled) {
    this.gamesCanceled = gamesCanceled;
    return this;
  }

  /**
   * Get gamesCanceled
   * @return gamesCanceled
  **/
  @ApiModelProperty(value = "")
  
    public Integer getGamesCanceled() {
    return gamesCanceled;
  }

  public void setGamesCanceled(Integer gamesCanceled) {
    this.gamesCanceled = gamesCanceled;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EstadisticasDeJuego estadisticasDeJuego = (EstadisticasDeJuego) o;
    return Objects.equals(this.gamesCreated, estadisticasDeJuego.gamesCreated) &&
        Objects.equals(this.gamesInProgress, estadisticasDeJuego.gamesInProgress) &&
        Objects.equals(this.gamesFinished, estadisticasDeJuego.gamesFinished) &&
        Objects.equals(this.gamesCanceled, estadisticasDeJuego.gamesCanceled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gamesCreated, gamesInProgress, gamesFinished, gamesCanceled);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EstadisticasDeJuego {\n");
    
    sb.append("    gamesCreated: ").append(toIndentedString(gamesCreated)).append("\n");
    sb.append("    gamesInProgress: ").append(toIndentedString(gamesInProgress)).append("\n");
    sb.append("    gamesFinished: ").append(toIndentedString(gamesFinished)).append("\n");
    sb.append("    gamesCanceled: ").append(toIndentedString(gamesCanceled)).append("\n");
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
