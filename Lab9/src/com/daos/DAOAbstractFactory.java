package com.daos;

import com.daos.controll.*;
import com.daos.repo.*;
import com.daos.repu.*;
import com.entities.*;

public class DAOAbstractFactory {

    private static DAOAbstractFactory factory;

    private DAOAbstractFactory(){}

    public static DAOAbstractFactory getFactory(){
        if(factory == null) factory = new DAOAbstractFactory();

        return factory;
    }

    public DAO getDAO(Class objectClass, FactoryTypes method){

        if(objectClass == Artist.class){
            switch (method){
                case HIBERNATE: return new ArtistsRepository();

                case JDBC: return new ArtistController();

                case PERSISTENCE: return new ArtistRepusitory();

                default: throw new IllegalStateException("Unexpected method value: " + method);
            }
        }
        if(objectClass == Album.class){
            switch (method){
                case HIBERNATE: return new AlbumsRepository();

                case JDBC: return new AlbumController();

                case PERSISTENCE: return new AlbumRepusitory();

                default: throw new IllegalStateException("Unexpected method value: " + method);
            }
        }
        if(objectClass == Genre.class){
            switch (method){
                case HIBERNATE: return new GenreRepository();

                case JDBC: return new GenreController();

                case PERSISTENCE: return new GenreRepusitory();

                default: throw new IllegalStateException("Unexpected method value: " + method);
            }
        }
        if(objectClass == Chart.class){
            switch (method){
                case HIBERNATE: return new ChartsRepository();

                case JDBC: return new ChartController();

                case PERSISTENCE: return new ChartsRepusitory();

                default: throw new IllegalStateException("Unexpected method value: " + method);
            }
        }
        if(objectClass == ClasamentEntry.class){
            switch (method){
                case HIBERNATE: return new ClasamentsRepository();

                case JDBC: return new ClasamentsController();

                case PERSISTENCE: return new ClasamentsRepusitory();

                default: throw new IllegalStateException("Unexpected method value: " + method);
            }
        }


        throw new IllegalStateException("Unexpected class value: " + objectClass);

    }
}
