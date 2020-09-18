/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.21).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package tp.tacs.api.controllers;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tp.tacs.api.model.GoogleAuthModel;
import tp.tacs.api.model.NuevoJWTModel;
import tp.tacs.api.model.RefreshAccessTokenBody;

import javax.validation.Valid;

@Api(value = "auth", description = "the auth API")
public interface AuthApi {

    @ApiOperation(value = "login", nickname = "logIn", notes = "Loguea a un usuario validando su Google Id Token y retornando un JWT de la aplicación", response = NuevoJWTModel.class, tags={ "Auth", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = NuevoJWTModel.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Fail") })
    @RequestMapping(value = "/auth/logIn",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<NuevoJWTModel> logIn(@ApiParam(value = "" ,required=true )  @Valid @RequestBody GoogleAuthModel body
);


    @ApiOperation(value = "Permite obtener un nuevo JWT", nickname = "refreshAccessToken", notes = "", response = NuevoJWTModel.class, tags={ "Auth", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = NuevoJWTModel.class),
        @ApiResponse(code = 401, message = "Unauthorized") })
    @RequestMapping(value = "/auth/refresh",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<NuevoJWTModel> refreshAccessToken(@ApiParam(value = ""  )  @Valid @RequestBody RefreshAccessTokenBody body
);


    @ApiOperation(value = "singUp", nickname = "singUp", notes = "Registro a un usuario nuevo y retorna un JWT de la aplicación", response = NuevoJWTModel.class, tags={ "Auth", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = NuevoJWTModel.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Fail") })
    @RequestMapping(value = "/auth/signUp",
        produces = { "application/json" },
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<NuevoJWTModel> singUp(@ApiParam(value = "" ,required=true )  @Valid @RequestBody GoogleAuthModel body
);

}

