package tp.tacs.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * GoogleAuthModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-03T22:53:49.293-03:00[America/Buenos_Aires]")
public class GoogleAuthModel   {
  @JsonProperty("idToken")
  private String idToken = null;

  public GoogleAuthModel idToken(String idToken) {
    this.idToken = idToken;
    return this;
  }

  /**
   * Google Id Token del usuario
   * @return idToken
  **/
  @ApiModelProperty(required = true, value = "Google Id Token del usuario")
      @NotNull

    public String getIdToken() {
    return idToken;
  }

  public void setIdToken(String idToken) {
    this.idToken = idToken;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GoogleAuthModel googleAuthModel = (GoogleAuthModel) o;
    return Objects.equals(this.idToken, googleAuthModel.idToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoogleAuthModel {\n");
    
    sb.append("    idToken: ").append(toIndentedString(idToken)).append("\n");
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
