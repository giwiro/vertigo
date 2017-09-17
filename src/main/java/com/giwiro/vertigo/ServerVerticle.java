package com.giwiro.vertigo;

import com.giwiro.vertigo.app.auth.AuthModule;
import com.giwiro.vertigo.core.gateways.database.DatabaseGateway;
import com.giwiro.vertigo.database.mongo.Database;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;


public class ServerVerticle extends AbstractVerticle{
    private AuthModule authModule;


    @Override
    public void start(Future<Void> future) {
        startWebApp((http) -> completeStartup(http, future));
    }

    private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
        // Create a router object.
        JsonObject config = config();
        Router router = Router.router(vertx);
        System.out.println("config" + config);
        // Body parser
        router.route().handler(BodyHandler.create());
        DatabaseGateway database = new Database(vertx, config);

        this.setUpModules(database, router);

        vertx
            .createHttpServer()
            .requestHandler(router::accept)
            .listen(
                // Retrieve the port from the configuration,
                // default to 8080.
                config.getInteger("http.port", 8080),
                next::handle
            );
    }

    private void setUpModules(DatabaseGateway database, Router router) {
        this.authModule = new AuthModule(database, router);
    }

    private void completeStartup(AsyncResult<HttpServer> http, Future<Void> fut) {
        if (http.succeeded()) {
            fut.complete();
        } else {
            fut.fail(http.cause());
        }
    }

    @Override
    public void stop() throws Exception {
        // mongo.close();
    }
}
