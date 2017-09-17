package com.giwiro.vertigo.app.auth;

import com.giwiro.vertigo.core.auth.AuthInteractor;
import com.giwiro.vertigo.core.gateways.database.DatabaseGateway;
import io.vertx.ext.web.Router;

public class AuthModule {
    AuthInteractor interactor;
    AuthController controller;

    public AuthModule(DatabaseGateway database, Router router) {
        this.interactor = new AuthInteractor(database);
        this.controller = new AuthController(this.interactor);

        this.setRoutes(router);
    }

    private void setRoutes(Router router) {
        router.post("/user/auth").handler(this.controller::authenticateUser);
    }
}
