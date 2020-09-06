/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.21).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package tp.tacs.api.controllers;

import tp.tacs.api.model.ListarUsuariosResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-05T20:37:59.553716700-03:00[America/Buenos_Aires]")
@Api(value = "usuarios", description = "the usuarios API")
public interface UsuariosApi {

    @ApiOperation(value = "Permite listar a los usuarios", nickname = "listarUsuarios", notes = "", response = ListarUsuariosResponse.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Usuarios", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ListarUsuariosResponse.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Page not found") })
    @RequestMapping(value = "/usuarios",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ListarUsuariosResponse> listarUsuarios(@ApiParam(value = "") @Valid @RequestParam(value = "filter", required = false) String filter
,@ApiParam(value = "", defaultValue = "10") @Valid @RequestParam(value = "tamanioPagina", required = false, defaultValue="10") Long tamanioPagina
,@ApiParam(value = "", defaultValue = "0") @Valid @RequestParam(value = "pagina", required = false, defaultValue="0") Long pagina
);

}

