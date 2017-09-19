package com.giwiro.vertigo.database.mongo.schema;

import com.giwiro.vertigo.core.models.CoreUser;
import com.giwiro.vertigo.database.mongo.common.ObjectId;
import com.giwiro.vertigo.utils.Crypto;

public class UserSchema extends CoreUser{
    private ObjectId _id;
    private String passwordHashed;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public boolean comparePassword(String password) {
        return Crypto.checkPassword(password, this.passwordHashed);
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }
}
