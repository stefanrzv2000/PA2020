package com.entities;

import java.util.Map;

public abstract class ObjectEntity<T extends ObjectEntity<T>> {

    private Class<T> objectClass;

    public abstract int ID();

    public abstract Map<String,String> toMap();

}
