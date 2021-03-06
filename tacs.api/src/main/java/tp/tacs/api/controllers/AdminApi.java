/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.22).
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
import tp.tacs.api.model.EstadisticasDeJuegoModel;
import tp.tacs.api.model.EstadisticasDeUsuarioModel;
import tp.tacs.api.model.ScoreboardResponse;

import javax.validation.Valid;
import java.util.Date;

@Api(value = "admin", description = "the admin API")
public interface AdminApi {

    @ApiOperation(value = "Obtiene las estadísticas generales del juego", nickname = "getEstadisticas", notes = "", response = EstadisticasDeJuegoModel.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Admin", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = EstadisticasDeJuegoModel.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 403, message = "Forbbiden") })
    @RequestMapping(value = "/admin/estadisticas",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<EstadisticasDeJuegoModel> getEstadisticas(@ApiParam(value = "") @Valid @RequestParam(value = "fechaInicio", required = false) Date fechaInicio
,@ApiParam(value = "") @Valid @RequestParam(value = "fechaFin", required = false) Date fechaFin
);


    @ApiOperation(value = "Obtiene las estadísticas particulares de un usuario", nickname = "getEstadisticasDeUsuario", notes = "", response = EstadisticasDeUsuarioModel.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Admin", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = EstadisticasDeUsuarioModel.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Page not found") })
    @RequestMapping(value = "/admin/estadisticas/usuarios/{idUsuario}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<EstadisticasDeUsuarioModel> getEstadisticasDeUsuario(@ApiParam(value = "Id del usuario",required=true) @PathVariable("idUsuario") String idUsuario
);


    @ApiOperation(value = "Retorna los datos del scoreboard ordenados por partidas ganadas descendentemente.", nickname = "getScoreboard", notes = "", response = ScoreboardResponse.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Admin", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ScoreboardResponse.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Page not found") })
    @RequestMapping(value = "/admin/scoreboard",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ScoreboardResponse> getScoreboard(@ApiParam(value = "", defaultValue = "10") @Valid @RequestParam(value = "tamanioPagina", required = false, defaultValue="10") Long tamanioPagina
,@ApiParam(value = "", defaultValue = "0") @Valid @RequestParam(value = "pagina", required = false, defaultValue="0") Long pagina
);


    @ApiOperation(value = "Pasar de turno", nickname = "pasarTurnoAdmin", notes = "", tags={ "Admin", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success") })
    @RequestMapping(value = "/admin/pasarTurno/{idPartida}",
        method = RequestMethod.POST)
    ResponseEntity<Void> pasarTurnoAdmin(@ApiParam(value = "",required=true) @PathVariable("idPartida") String idPartida
);

    @ApiOperation(value = "Obtener JWT por id de usuario", nickname = "obtenerJwtUsuario", notes = "", tags={ "Admin", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success") })
    @ApiResponse(code = 400, message = "Bad request")
    @RequestMapping(value = "/admin/token/{idUsuario}",
            method = RequestMethod.GET)
    ResponseEntity<String> obtenerJwtUsuario(@ApiParam(value = "",required=true) @PathVariable("idUsuario") String idUsuario
    );

}

