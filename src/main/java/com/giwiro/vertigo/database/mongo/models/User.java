package com.giwiro.vertigo.database.mongo.models;

import com.giwiro.vertigo.core.gateways.database.UserGateway;
import com.giwiro.vertigo.core.models.CoreUser;
import com.giwiro.vertigo.database.mongo.schema.UserSchema;
import com.giwiro.vertigo.utils.Crypto;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.Date;

public class User implements UserGateway {
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
                if (json == null) {
                    future.complete(null);
                }else {
                    final CoreUser user
                            = Json.decodeValue(json.toString(), UserSchema.class);
                    future.complete(user);
                }
            } else {
                future.fail(res.cause());
                res.cause().printStackTrace();
            }
        });
        return future;
    }

    @Override
    public Future<CoreUser> findByEmailOrUsername(String username, String email) {
        Future<CoreUser> future = Future.future();
        JsonObject queryOr = new JsonObject();
        JsonArray queryArray = new JsonArray();
        queryArray.add(new JsonObject().put("username", username));
        queryArray.add(new JsonObject().put("email", email));
        queryOr.put("$or", queryArray);
        mongo.findOne("user", queryOr, null, res -> {
            if (res.succeeded()) {
                JsonObject json = res.result();
                if (json == null) {
                    future.complete(null);
                }else {
                    final CoreUser user
                        = Json.decodeValue(json.toString(), UserSchema.class);
                    future.complete(user);
                }

            } else {
                future.fail(res.cause());
                res.cause().printStackTrace();
            }
        });
        return future;
    }

    @Override
    public Future<CoreUser> insertUser(String username, String password, String email) {
        Future<CoreUser> future = Future.future();
        JsonObject document = new JsonObject()
                .put("username", username)
                .put("passwordHashed", Crypto.hashPassword(password))
                .put("email", email);
        JsonObject dateField = new JsonObject();
        dateField.put("$date", new Date().getTime());
        // document.put("registeredAt", dateField);
        mongo.insert("user", document, res -> {
            if (res.succeeded()) {
                final String id = res.result();
                final UserSchema user
                        = Json.decodeValue(document.toString(), UserSchema.class);
                user.setId(id);
                future.complete(user);
            } else {
                future.fail(res.cause());
                res.cause().printStackTrace();
            }
        });
        return future;
    }
}
