package com.daos;

public enum FactoryTypes {
    HIBERNATE,JDBC,PERSISTENCE;

    @Override
    public String toString() {
        switch (this){
            case PERSISTENCE: return "Persistence";
            case HIBERNATE: return "Hibernate";
            case JDBC: return "JDBC";
            default: return "";
        }
    }
}
