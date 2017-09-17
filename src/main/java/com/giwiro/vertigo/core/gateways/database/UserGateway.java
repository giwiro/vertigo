package com.giwiro.vertigo.core.gateways.database;

public interface UserGateway<F> {
    public F findByUsername(String username);
}
