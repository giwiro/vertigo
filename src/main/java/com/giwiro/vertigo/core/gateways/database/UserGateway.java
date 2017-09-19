package com.giwiro.vertigo.core.gateways.database;

import io.vertx.core.Future;

public interface UserGateway {
    public Future findByUsername(String username);
    public Future insertUser(String username, String password, String email);
    public Future findByEmailOrUsername(String username, String email);
}
