package com.co.alianza.coreclient.config;

import com.co.alianza.coreclient.constants.UrlMappingConstant;
import com.co.alianza.coreclient.dto.ResponseErrorModel;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiResponse(responseCode = "400", description = "", content = {
        @Content(mediaType = UrlMappingConstant.PRODUCES, schema = @Schema(implementation = ResponseErrorModel.class))})
@ApiResponse(responseCode = "401", description = "", content = {
        @Content(mediaType = UrlMappingConstant.PRODUCES, schema = @Schema(implementation = ResponseErrorModel.class))})
@ApiResponse(responseCode = "403", description = "", content = {
        @Content(mediaType = UrlMappingConstant.PRODUCES, schema = @Schema(implementation = ResponseErrorModel.class))})
@ApiResponse(responseCode = "404", description = "", content = {
        @Content(mediaType = UrlMappingConstant.PRODUCES, schema = @Schema(implementation = ResponseErrorModel.class))})
@ApiResponse(responseCode = "500", description = "", content = {
        @Content(mediaType = UrlMappingConstant.PRODUCES, schema = @Schema(implementation = ResponseErrorModel.class))})
public @interface ApiResponseErrorModel {
}
