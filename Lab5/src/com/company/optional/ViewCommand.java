package com.company.optional;

import com.company.compulsory.Document;
import com.company.exceptions.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ViewCommand extends Command {

    public ViewCommand(CatalogShell shell, String... args) throws InvalidArgumentsException {
        super(shell, args);
        if(args.length < 2) throw new InvalidArgumentsException("Usage: view <DocumentID>");
    }

    @Override
    public void execute() throws IOException, InvalidCatalogException, NoCatalogException, InvalidArgumentsException, InexistentDocumentException {
        if(shell.getCatalog() == null) throw new NoCatalogException();

        String documentID = args.get(1);

        Document document = shell.getCatalog().findByID(documentID);

        if(document == null) throw new InexistentDocumentException(documentID);

        Desktop desktop = Desktop.getDesktop();
        boolean ok = false;
        try {
            File file = new File(document.getLocation());
            if(file.exists())
            {
                desktop.open(file);
                ok = true;
            }
            if(!ok) {
                desktop.browse(URI.create(document.getLocation()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
