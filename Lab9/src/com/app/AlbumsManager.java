package com.app;

import com.daos.*;
import com.daos.repo.AbstractRepository;
import com.daos.repo.GenreRepository;
import com.entities.Album;
import com.entities.Artist;
import com.entities.Genre;
import com.graph.Graph;
import com.util.HibernateUtil;
import org.hibernate.Metamodel;
import org.hibernate.Session;

import javax.persistence.metamodel.EntityType;

import java.util.Optional;

public class AlbumsManager {

    private final static DAOAbstractFactory factory = DAOAbstractFactory.getFactory();

    public static void main(final String[] args) throws Exception {
        try (Session session = HibernateUtil.getSession()) {


            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();

            for (EntityType<?> entityType : metamodel.getEntities()) {
                Class entityClass = Class.forName("com.entities." + entityType.getName());
                String[] names = entityClass.getName().split("\\.");
                String name = names[names.length - 1];
                System.out.println("\nFor class: " + name);

                AbstractRepository ar = AbstractRepository.getInstanceFor(entityClass);
                System.out.println("Total count: " + ar.count());
                System.out.println("Next available ID is: " + ar.getNextId());

                Optional o = ar.findById(1);
                System.out.println("The first " + name + " is " + (o.isPresent() ? o.get().toString() : "null"));
            }

            System.out.println("I did it!");

            Artists artists = (Artists) factory.getDAO(Artist.class, FactoryTypes.HIBERNATE);
            Albums albums = (Albums) factory.getDAO(Album.class,FactoryTypes.PERSISTENCE);

            albums.assignRandomGenres();

            System.out.println("artists : " + artists.count());

            artists.scoreBoard("all").print(20);

            System.out.println(artists.findById(10) + "'s albums:");
            for(var album: albums.findByArtist(10)){
                System.out.println(album);
            }

            String genreName = "Heavy Metal";

            System.out.println(genreName + " albums:");
            for(Album album: albums.findByGenre(genreName)){
                System.out.println(album);
            }

            Graph graph = new Graph();

            System.out.println();
            System.out.println(graph);

        }
    }

    public static void initializeGenres(){
        Genres gr = (GenreRepository) AbstractRepository.getInstanceFor(Genre.class);

        gr.addGenre("Rock");
        gr.addGenre("Classical");
        gr.addGenre("Pop");
        gr.addGenre("Electronic");
        gr.addGenre("House");
        gr.addGenre("Blues");
        gr.addGenre("Jazz");
        gr.addGenre("Punk");
        gr.addGenre("Swing");
        gr.addGenre("Reggaeton");
        gr.addGenre("Salsa");
        gr.addGenre("Heavy Metal");
        gr.addGenre("R&B");
        gr.addGenre("Traditional");
        gr.addGenre("Hip Hop");
        gr.addGenre("Flamenco");
        gr.addGenre("Dubstep");
        gr.addGenre("Disco");
        gr.addGenre("Country");

        System.out.println("Genres Initialized");

        System.out.println("There are " + gr.count() + " different genres");
    }
}