package net.clubedocomputador.portinari.PortinariMirror.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject{

    @PrimaryKey
    public String id;
    public String name;
    public String nickname;
    public String picture;

}
