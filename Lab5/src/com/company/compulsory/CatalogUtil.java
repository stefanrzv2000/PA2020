package com.company.compulsory;

import com.company.exceptions.InvalidCatalogException;
import com.company.exceptions.InvalidDocumentException;

import java.awt.*;
import java.io.*;
import java.net.URI;

public class CatalogUtil {
    public static void save(Catalog catalog) throws IOException {
        try(var oos = new ObjectOutputStream(new FileOutputStream(catalog.getPath()))){
            oos.writeObject(catalog);
        }
    }

    public static Catalog load(String path) throws InvalidCatalogException, IOException {
        try(var ois = new ObjectInputStream(new FileInputStream(path))){
            return (Catalog) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new InvalidCatalogException(e);
        }
    }

    public static void view(Document document) throws InvalidDocumentException {
        if(document == null) throw new InvalidDocumentException(new Exception("Document is null"));
        Desktop desktop = Desktop.getDesktop();
        boolean ok = false;
        try {
            File file = new File(document.getLocation());
            if(file.exists())
            {
                desktop.open(file);
                ok = true;
            }
            if(!ok) desktop.browse(URI.create(document.getLocation()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidDocumentException(e);
        }

        System.out.println(document.getLocation());
    }
}
