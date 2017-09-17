package com.giwiro.vertigo.database.mongo;

import com.giwiro.vertigo.database.mongo.models.User;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import com.giwiro.vertigo.core.gateways.database.DatabaseGateway;

public class Database extends DatabaseGateway{
    private MongoClient mongo;

    public Database(Vertx vertx, JsonObject conf) {
        this.mongo = MongoClient.createShared(vertx, conf);
        this.User = new User(this.mongo);
    }
}
