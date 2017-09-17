package com.giwiro.vertigo.database.mongo.models;

import com.giwiro.vertigo.core.gateways.database.UserGateway;
import com.giwiro.vertigo.core.models.CoreUser;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class User implements UserGateway<Future> {
    private MongoClient mongo;

    public User(MongoClient mongo) {
        this.mongo = mongo;
    }

    @Override
    public Future<CoreUser> findByUsername(String username) {
        Future<CoreUser> future = Future.future();
        JsonObject query = new JsonObject();
        query.put("username", username);
        mongo.findOne("user", query, null, res -> {
            if (res.succeeded()) {
                JsonObject json = res.result();
                future.complete(null);
                // System.out.println(json.encodePrettily());
            } else {
                future.fail(res.cause());
                res.cause().printStackTrace();
            }
        });
        return future;
    }
}
