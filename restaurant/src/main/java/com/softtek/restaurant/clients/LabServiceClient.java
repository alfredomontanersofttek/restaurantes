package com.softtek.restaurant.clients;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class LabServiceClient extends WebServiceGatewaySupport {

    public Object labService(Object peticionRequest) {
        return getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/services/restaurante/labService", peticionRequest);
    }
}
