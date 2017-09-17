package com.giwiro.vertigo.core.auth;

import com.giwiro.vertigo.core.gateways.database.DatabaseGateway;
import com.giwiro.vertigo.core.gateways.database.UserGateway;
import com.giwiro.vertigo.core.models.CoreUser;
import io.vertx.core.Future;

public class AuthInteractor {
    private DatabaseGateway database;
    private UserGateway<Future> userGateway;

    public AuthInteractor(DatabaseGateway database) {
        this.database = database;
        this.userGateway = this.database.getUserGateway();
    }

    public Future<String> authenticateUser(AuthRequests.AuthenticateUserRequest request) {
        Future<String> result = Future.future();
        Future<CoreUser> future = this.userGateway.findByUsername(request.getUsername());
        future.setHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                result.complete("Holis");
            } else {
                result.fail(asyncResult.cause());
            }
        });
        return result;
    }
}
