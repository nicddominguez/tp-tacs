/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.17-SNAPSHOT).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package tp.tacs.api.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import tp.tacs.api.model.Provincia;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-08-29T21:54:36.724-03:00[America/Buenos_Aires]")
@Api(value = "provincias", description = "the provincias API")
public interface ProvinciasApi {

    @ApiOperation(value = "Permite listar las provincias", nickname = "listarProvincias", notes = "", response = Provincia.class, responseContainer = "List", authorizations = {
        @Authorization(value = "bearerAuth")    }, tags={ "Provincias", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Provincia.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Page not found") })
    @RequestMapping(value = "/provincias",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Provincia>> listarProvincias(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "page", required = true) Integer page
,@ApiParam(value = "", defaultValue = "10") @Valid @RequestParam(value = "pageSize", required = false, defaultValue="10") Integer pageSize
);

}