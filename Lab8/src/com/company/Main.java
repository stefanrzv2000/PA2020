package com.company;

import com.company.compulsory.AlbumController;
import com.company.compulsory.ArtistController;
import com.company.compulsory.Database;
import com.company.exceptions.DataBaseException;
import com.company.exceptions.InvalidTableNameException;
import com.company.optional.Album;
import com.company.optional.Artist;
import com.company.optional.ChartController;

import java.sql.SQLException;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {

        printTableNames();

        //initializeCountryCharts();
        //initializePeriodCharts();

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        printScoreBoard("Romania",10);

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        printScoreBoard("all",20);

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printHTMLScoreBoard("all.html","all",20);

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

/*
        ArtistController artistController = ArtistController.getInstance();
        AlbumController albumController = AlbumController.getInstance();

        try {
            List<Artist> MJs = artistController.findByName("Michael Jackson");
            for(Artist MJ:MJs) System.out.println(MJ);


            List<Album> byMJ = albumController.findByArtistId(MJs.get(0).getId());
            for(Album album: byMJ) System.out.println(album);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printReason();
        }
*/

    }

    public static void createRandomArtists(int count){

        ArtistController artistController = ArtistController.getInstance();

        try {

            for(int i = 0; i < count; i ++) {
                if(artistController.createRandomArtist()<0) i--;
            }

        } catch (InvalidTableNameException e) {
            e.printReason();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createRandomArtists(int count, String country) throws InvalidTableNameException, SQLException {

        ArtistController artistController = ArtistController.getInstance();

        for(int i = 0; i < count; i ++) {
            if(artistController.createRandomArtist(country)<0) i--;
        }

    }

    public static void createRandomAlbum(int count) throws DataBaseException, SQLException {
        AlbumController albumController = AlbumController.getInstance();

        for(int i = 0; i < count; i++){
            if(albumController.createRandomAlbum()<0) i--;
        }
    }

    public static void initializeCountryCharts(){
        ChartController chartController = ChartController.getInstance();

        try {
            chartController.createChartByCountry("Romania",randDecade());
            chartController.createChartByCountry("Romania", randDecade());
            chartController.createChartByCountry("Romania", randDecade());
            chartController.createChartByCountry("United States of America", randDecade());
            chartController.createChartByCountry("United States of America", randDecade());
            chartController.createChartByCountry("United States of America", randDecade());
            chartController.createChartByCountry("United Kingdom", randDecade());
            chartController.createChartByCountry("United Kingdom", randDecade());
            chartController.createChartByCountry("France", randDecade());
            chartController.createChartByCountry("France", randDecade());
            chartController.createChartByCountry("Israel", randDecade());
            chartController.createChartByCountry("Israel", randDecade());
            chartController.createChartByCountry("Australia", randDecade());
            chartController.createChartByCountry("Australia", randDecade());
            chartController.createChartByCountry("Australia", randDecade());
            chartController.createChartByCountry("Turkey", randDecade());
            chartController.createChartByCountry("Turkey", randDecade());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printReason();
        }
    }

    public static void initializePeriodCharts(){
        ChartController chartController = ChartController.getInstance();

        try {
            //chartController.createChartByPeriod(2010,2020,50);

            for(int year = 1900; year < 2020; year+=10){
                chartController.createChartByPeriod(year,year+9,randDecade());
                chartController.createChartByPeriod(year,year+9,randDecade());
            }

            for(int year = 1900; year < 2020; year+=25){
                chartController.createChartByPeriod(year,year+24,randDecade());
                chartController.createChartByPeriod(year,year+24,randDecade());
                chartController.createChartByPeriod(year,year+24,randDecade());
            }

            for(int year = 1900; year < 2020; year+=100){
                chartController.createChartByPeriod(year,year+99,randDecade());
                chartController.createChartByPeriod(year,year+99,randDecade());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printReason();
        }
    }

    public static int randDecade(){
        int j = (int) (Math.random() * 5);
        if(j == 4) return 100;
        return (j+2)*10;
    }

    public static void printTableNames(){
        Database database = Database.getInstance();

        try {
            System.out.println(database.getTableNames());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printScoreBoard(String country,int count){
        try {
            ArtistController.getInstance().scoreBoard(country).print(count);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printReason();
        }
    }

    public static void printHTMLScoreBoard(String fileName,String country,int count){
        try {
            ArtistController.getInstance().scoreBoard(country).printHTML(fileName,count);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printReason();
        }
    }
}
