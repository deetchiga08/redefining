package com.briller.Response;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ftdcResponse {

        @JsonProperty("isOk")
        private String isOk;

        @JsonProperty("result")
        private String result = "Success";

        @JsonProperty("message")
        private String message;

        @JsonProperty("data")
        private Object object;

    @JsonProperty("return")
    private long returnCode = 1;

    @JsonCreator
    public ftdcResponse(Object object, String message){
        this.message = message;
        this.object = object;
    }

    @JsonCreator
    public ftdcResponse(String isOk, String message){
        this.isOk = isOk;
        this.message = message;
    }

    @JsonCreator
    public ftdcResponse(String isOk, Object object, String message){
        this.isOk = isOk;
        this.message = message;
        this.object = object;
    }

   @JsonCreator
    public ftdcResponse(long returnCode, String result, String message){
        this.returnCode = returnCode;
        this.result = result;
        this.message = message;
    }


    @JsonCreator
    public ftdcResponse(String message){
        this.message = message;
    }

}

