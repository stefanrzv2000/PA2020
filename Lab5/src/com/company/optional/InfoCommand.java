package com.company.optional;

import com.company.compulsory.Document;
import com.company.exceptions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class InfoCommand extends Command {
    public InfoCommand(CatalogShell shell, String... args) throws InvalidArgumentsException {
        super(shell, args);
        if(args.length < 2) throw new InvalidArgumentsException("Usage: info <DocumentID>");
    }

    @Override
    public void execute() throws IOException, InvalidCatalogException, NoCatalogException, InvalidArgumentsException, InexistentDocumentException, DocumentExistsException {
        if(shell.getCatalog() == null) throw new NoCatalogException();

        String documentID = args.get(1);

        Document document = shell.getCatalog().findByID(documentID);

        if(document == null) throw new InexistentDocumentException(documentID);

        try {
            File f = new File(document.getLocation());
            if(!f.exists())
            {
                throw new InvalidArgumentsException("Inexistent file (url files are not supported)");
            }

            Path file = Paths.get(document.getLocation());

            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            System.out.println("creationTime: " + attr.creationTime());
            System.out.println("lastAccessTime: " + attr.lastAccessTime());
            System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

            System.out.println("isDirectory: " + attr.isDirectory());
            System.out.println("isOther: " + attr.isOther());
            System.out.println("isRegularFile: " + attr.isRegularFile());
            System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
            System.out.println("size: " + attr.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
