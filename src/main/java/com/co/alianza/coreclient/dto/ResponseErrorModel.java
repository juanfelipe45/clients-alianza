package com.co.alianza.coreclient.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class ResponseErrorModel {

    private HeaderResponse errorHeader;
    private ErrorDetail errorDetail;
}
