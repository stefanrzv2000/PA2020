package com.company;

import com.company.compulsory.*;
import com.company.exceptions.InvalidCatalogException;
import com.company.exceptions.InvalidDocumentException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //String currentDir = System.getProperty("user.dir");
        //System.out.println("Current dir using System:" +currentDir);

        Main app = new Main();
        try {
            app.testCreateSave();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            app.testLoadView();
        } catch (IOException | InvalidCatalogException | InvalidDocumentException e) {
            e.printStackTrace();
        }

    }

    private void testCreateSave() throws IOException {
        String currentDir = System.getProperty("user.dir");

        Catalog catalog = new Catalog("Java Resources",currentDir + "/resources/catalog1.ser");
        Document document = new Document("java1", "Java Course 1", "https://profs.info.uaic.ro/~acf/java/slides/en/intro_slide_en.pdf");
        document.addTag("type","Slides");
        Document document1 = new Document("java2","Curs java",currentDir + "/resources/intro_slide_en.pdf");
        catalog.addDocument(document);
        catalog.addDocument(document1);

        CatalogUtil.save(catalog);
    }

    private void testLoadView() throws IOException, InvalidCatalogException, InvalidDocumentException {
        String currentDir = System.getProperty("user.dir");

        Catalog catalog = CatalogUtil.load(currentDir + "/resources/catalog1.ser");
        assert catalog != null;
        Document document1 = catalog.findByID("java1");
        Document document2 = catalog.findByID("java2");
        Document document3 = catalog.findByID("java3");

        CatalogUtil.view(document1);
        CatalogUtil.view(document2);
        CatalogUtil.view(document3);

    }

}
