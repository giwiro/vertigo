package com.giwiro.vertigo.database.mongo.common;

public class ObjectId {
    private String $oid;

    public ObjectId() { }

    public ObjectId(String $oid) {
        this.$oid = $oid;
    }

    public String get$oid() {
        return $oid;
    }

    public void set$oid(String $oid) {
        this.$oid = $oid;
    }
}
