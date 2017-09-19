package com.giwiro.vertigo.core.auth;

import com.giwiro.vertigo.core.auth.request.*;
import com.giwiro.vertigo.core.auth.response.AuthenticateUserResponse;
import com.giwiro.vertigo.core.gateways.database.DatabaseGateway;
import com.giwiro.vertigo.core.gateways.database.UserGateway;
import com.giwiro.vertigo.core.models.CoreUser;
import io.vertx.core.Future;

public class AuthInteractor {
    private DatabaseGateway database;
    private UserGateway userGateway;

    public AuthInteractor(DatabaseGateway database) {
        this.database = database;
        this.userGateway = this.database.getUserGateway();
    }

    public Future<CoreUser> authenticateUser(AuthenticateUserRequest request) {
        Future<CoreUser> result = Future.future();
        Future<CoreUser> future = this.userGateway.findByUsername(request.getUsername());
        future.setHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                CoreUser user = future.result();
                if (user != null && user.comparePassword(request.getPassword())) {
                    result.complete(user);
                }else {
                    result.complete(null);
                }
            } else {
                result.fail(asyncResult.cause());
            }
        });
        return result;
    }

    public Future<CoreUser> registerUser(RegisterUserRequest request) {
        Future<CoreUser> result = Future.future();
        Future<CoreUser> futureFind
                = this.userGateway.findByEmailOrUsername(request.getUsername(), request.getEmail());

        // Validate
        futureFind.setHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                CoreUser user = asyncResult.result();
                System.out.println("found user is null: " + (user == null ? "NULL" : "Not null"));
                if (user == null) {
                    Future<CoreUser> future
                            = this.userGateway.insertUser(request.getUsername(), request.getPassword(), request.getEmail());
                    future.setHandler(asyncResult2 -> {
                        if (asyncResult2.succeeded()) {
                            CoreUser r = future.result();
                            System.out.println("gonna return user: " + r.getEmail());
                            result.complete(r);
                        } else {
                            result.fail(asyncResult2.cause());
                        }
                    });
                }else {
                    result.complete(null);
                }
            } else {
                result.fail(asyncResult.cause());
            }
        });
        return result;
    }
}
