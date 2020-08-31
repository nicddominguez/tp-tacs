/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.17-SNAPSHOT).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package tp.tacs.api.controllers;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.threeten.bp.LocalDate;
import tp.tacs.api.model.EstadisticasDeJuego;
import tp.tacs.api.model.EstadisticasDeUsuario;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:36.724-03:00[America/Buenos_Aires]")
@Api(value = "admin", description = "the admin API")
public interface AdminApi {

    @ApiOperation(value = "Obtiene las estadísticas generales del juego", nickname = "getEstadisticas", notes = "", response = EstadisticasDeJuego.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Admin", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = EstadisticasDeJuego.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 403, message = "Forbbiden") })
    @RequestMapping(value = "/admin/estadisticas",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<EstadisticasDeJuego> getEstadisticas(@ApiParam(value = "") @Valid @RequestParam(value = "fechaInicio", required = false) LocalDate fechaInicio
,@ApiParam(value = "") @Valid @RequestParam(value = "fechaFin", required = false) LocalDate fechaFin
);


    @ApiOperation(value = "Obtiene las estadísticas particulares de un usuario", nickname = "getEstadisticasDeUsuario", notes = "", response = EstadisticasDeUsuario.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Admin", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = EstadisticasDeUsuario.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Page not found") })
    @RequestMapping(value = "/admin/estadisticas/usuarios/{idUsuario}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<EstadisticasDeUsuario> getEstadisticasDeUsuario(@ApiParam(value = "Id del usuario",required=true) @PathVariable("idUsuario") Integer idUsuario
);


    @ApiOperation(value = "Retorna los datos del scoreboard", nickname = "getScoreboard", notes = "", response = EstadisticasDeUsuario.class, responseContainer = "List", authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Admin", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = EstadisticasDeUsuario.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Page not found") })
    @RequestMapping(value = "/admin/scoreboard",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<EstadisticasDeUsuario>> getScoreboard(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "page", required = true) Integer page
,@ApiParam(value = "", defaultValue = "10") @Valid @RequestParam(value = "pageSize", required = false, defaultValue="10") Integer pageSize
);

}
