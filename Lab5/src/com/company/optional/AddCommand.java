package com.company.optional;

import com.company.compulsory.Document;
import com.company.exceptions.*;

import java.io.IOException;

public class AddCommand extends Command {

    public AddCommand(CatalogShell shell, String... args) throws InvalidArgumentsException {
        super(shell, args);
        if(args.length < 4) throw new InvalidArgumentsException("Usage: add <docID> <docName> <docPath>");
    }

    @Override
    public void execute() throws IOException, InvalidCatalogException, NoCatalogException, InvalidArgumentsException, InexistentDocumentException, DocumentExistsException {
        var catalog = shell.getCatalog();
        if(catalog == null) throw new NoCatalogException();

        if(catalog.findByID(args.get(1)) != null) throw new DocumentExistsException(args.get(1));

        shell.getCatalog().addDocument(new Document(args.get(1),args.get(2),args.get(3)));
    }
}
