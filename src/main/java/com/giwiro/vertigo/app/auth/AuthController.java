package com.giwiro.vertigo.app.auth;

import com.giwiro.vertigo.core.auth.AuthInteractor;
import com.giwiro.vertigo.core.auth.request.*;
import com.giwiro.vertigo.core.auth.response.AuthenticateUserResponse;
import com.giwiro.vertigo.core.auth.response.RegisterUserResponse;
import com.giwiro.vertigo.core.models.CoreUser;
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
        final AuthenticateUserRequest request
                = Json.decodeValue(routingContext.getBodyAsString(), AuthenticateUserRequest.class);
        Future<CoreUser> future = this.interactor.authenticateUser(request);
        future.setHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                CoreUser user = asyncResult.result();
                AuthenticateUserResponse r;
                if (user == null) {
                    r = new AuthenticateUserResponse("Wrong credentials", false);
                    response
                        .setStatusCode(401)
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(r));
                }else {
                    r = new AuthenticateUserResponse("User successfully authenticated", true, user);
                    response
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(r));
                }
            } else {
                System.out.println("ERROR !!!");
                routingContext.fail(500);
            }
        });
    }

    public void registerUser(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        final RegisterUserRequest request
                = Json.decodeValue(routingContext.getBodyAsString(), RegisterUserRequest.class);
        Future<CoreUser> future = this.interactor.registerUser(request);
        future.setHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                CoreUser user = asyncResult.result();
                RegisterUserResponse r;

                if (user != null) {
                    r = new RegisterUserResponse(
                            "User registered", true, user);
                    response
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(r));
                }else {
                    r = new RegisterUserResponse(
                            "Same email or username", false);
                    response
                        .setStatusCode(409)
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(r));
                }
            } else {
                System.out.println("ERROR !!!");
                routingContext.fail(500);
            }
        });
    }
}
