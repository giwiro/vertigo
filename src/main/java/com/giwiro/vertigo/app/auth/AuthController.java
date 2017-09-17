package com.giwiro.vertigo.app.auth;

import com.giwiro.vertigo.core.auth.AuthInteractor;
import com.giwiro.vertigo.core.auth.AuthRequests;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class AuthController {
    private AuthInteractor interactor;

    public AuthController(AuthInteractor interactor) {
        this.interactor = interactor;
    }

    public void authenticateUser(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        final AuthRequests.AuthenticateUserRequest request
                = Json.decodeValue(routingContext.getBodyAsString(), AuthRequests.AuthenticateUserRequest.class);
        Future<String> future = this.interactor.authenticateUser(request);
        future.setHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                response.end(asyncResult.result());
            } else {
                System.out.println("ERROR !!!");
                routingContext.fail(500);
            }
        });
    }


}
