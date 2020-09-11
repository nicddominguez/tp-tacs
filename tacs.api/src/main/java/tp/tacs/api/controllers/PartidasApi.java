/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.21).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package tp.tacs.api.controllers;

import tp.tacs.api.model.AtacarMunicipioBody;
import tp.tacs.api.model.AtacarMunicipioResponse;
import tp.tacs.api.model.CrearPartidaBody;
import java.util.Date;
import tp.tacs.api.model.EstadoDeJuegoModel;
import tp.tacs.api.model.ListarPartidasResponse;
import tp.tacs.api.model.MoverGauchosBody;
import tp.tacs.api.model.MoverGauchosResponse;
import tp.tacs.api.model.MunicipioEnJuegoModel;
import tp.tacs.api.model.PartidaModel;
import tp.tacs.api.model.SimularAtacarMunicipioBody;
import tp.tacs.api.model.SimularAtacarMunicipioResponse;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-10T19:10:01.693073800-03:00[America/Buenos_Aires]")
@Api(value = "partidas", description = "the partidas API")
public interface PartidasApi {

    @ApiOperation(value = "Actualiza el estado de una partida", nickname = "actualizarEstadoPartida", notes = "", authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Partidas", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/partidas/{idPartida}",
        consumes = { "application/json" },
        method = RequestMethod.PATCH)
    ResponseEntity<Void> actualizarEstadoPartida(@ApiParam(value = "",required=true) @PathVariable("idPartida") Long idPartida
,@ApiParam(value = ""  )  @Valid @RequestBody PartidaModel body
);


    @ApiOperation(value = "Actualiza el estado de un municipio en juego", nickname = "actualizarMunicipio", notes = "", authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Partidas", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/partidas/{idPartida}/municipios/{idMunicipio}",
        consumes = { "application/json" },
        method = RequestMethod.PATCH)
    ResponseEntity<Void> actualizarMunicipio(@ApiParam(value = "",required=true) @PathVariable("idPartida") Long idPartida
,@ApiParam(value = "",required=true) @PathVariable("idMunicipio") Long idMunicipio
,@ApiParam(value = ""  )  @Valid @RequestBody MunicipioEnJuegoModel body
);


    @ApiOperation(value = "Ataca a un municipio. Al atacar, se ataca con todos los gauchos del municipio atacante.", nickname = "atacarMunicipio", notes = "", response = AtacarMunicipioResponse.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Partidas", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = AtacarMunicipioResponse.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/partidas/{idPartida}/ataques",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<AtacarMunicipioResponse> atacarMunicipio(@ApiParam(value = "",required=true) @PathVariable("idPartida") Long idPartida
,@ApiParam(value = ""  )  @Valid @RequestBody AtacarMunicipioBody body
);


    @ApiOperation(value = "Crear una nueva partida", nickname = "crearPartida", notes = "", authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Partidas", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/partidas",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> crearPartida(@ApiParam(value = ""  )  @Valid @RequestBody CrearPartidaBody body
);


    @ApiOperation(value = "Retorna la partida con id: idPartida. Incluye los datos del juego.", nickname = "getPartida", notes = "", response = PartidaModel.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Partidas", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = PartidaModel.class),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/partidas/{idPartida}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<PartidaModel> getPartida(@ApiParam(value = "Id de la partida",required=true) @PathVariable("idPartida") Long idPartida
);


    @ApiOperation(value = "Permite listar partidas. No incluye los datos de juego asociados a las mismas.", nickname = "listarPartidas", notes = "", response = ListarPartidasResponse.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Partidas", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = ListarPartidasResponse.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/partidas",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ListarPartidasResponse> listarPartidas(@ApiParam(value = "") @Valid @RequestParam(value = "fechaInicio", required = false) Date fechaInicio
,@ApiParam(value = "") @Valid @RequestParam(value = "fechaFin", required = false) Date fechaFin
,@ApiParam(value = "") @Valid @RequestParam(value = "estado", required = false) EstadoDeJuegoModel estado
,@ApiParam(value = "Campos por los que ordenar separados por coma. Los campos soportados son fecha y estado.") @Valid @RequestParam(value = "ordenarPor", required = false) String ordenarPor
,@ApiParam(value = "", defaultValue = "10") @Valid @RequestParam(value = "tamanioPagina", required = false, defaultValue="10") Long tamanioPagina
,@ApiParam(value = "", defaultValue = "0") @Valid @RequestParam(value = "pagina", required = false, defaultValue="0") Long pagina
);


    @ApiOperation(value = "Mueve gauchos de un municipio a otro", nickname = "moverGauchos", notes = "", response = MoverGauchosResponse.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Partidas", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = MoverGauchosResponse.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/partidas/{idPartida}/movimientos",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<MoverGauchosResponse> moverGauchos(@ApiParam(value = "",required=true) @PathVariable("idPartida") Long idPartida
,@ApiParam(value = ""  )  @Valid @RequestBody MoverGauchosBody body
);


    @ApiOperation(value = "Ataca a un municipio", nickname = "simularAtacarMunicipio", notes = "", response = SimularAtacarMunicipioResponse.class, authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Partidas", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = SimularAtacarMunicipioResponse.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Not found") })
    @RequestMapping(value = "/partidas/{idPartida}/ataques/simulaciones",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<SimularAtacarMunicipioResponse> simularAtacarMunicipio(@ApiParam(value = "",required=true) @PathVariable("idPartida") Long idPartida
,@ApiParam(value = ""  )  @Valid @RequestBody SimularAtacarMunicipioBody body
);

}

