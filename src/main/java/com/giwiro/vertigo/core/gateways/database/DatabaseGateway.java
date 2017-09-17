package com.giwiro.vertigo.core.gateways.database;

public abstract class DatabaseGateway {
    protected UserGateway User;

    public DatabaseGateway() { }

    public UserGateway getUserGateway() {
        return User;
    }
}
