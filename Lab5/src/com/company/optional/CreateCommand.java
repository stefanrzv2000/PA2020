package com.company.optional;

import com.company.compulsory.Catalog;
import com.company.exceptions.InvalidArgumentsException;
import com.company.exceptions.InvalidCatalogException;
import com.company.exceptions.NoCatalogException;

import java.io.IOException;

public class CreateCommand extends Command {

    public CreateCommand(CatalogShell shell, String... args) throws InvalidArgumentsException {
        super(shell, args);
        if(args.length < 2) throw new InvalidArgumentsException("Usage: create <CatalogName>");
    }

    @Override
    public void execute() throws IOException, InvalidCatalogException, NoCatalogException {

        Catalog catalog = new Catalog(args.get(1),shell.getPath() + "/" + "empty");
        shell.setCatalog(catalog);
    }
}
