package com.company.optional;

import com.company.compulsory.Catalog;
import com.company.exceptions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class Command {

    protected CatalogShell shell;

    protected List<String> args;

    public Command(CatalogShell shell, String ...args) {
        this.shell = shell;
        this.args = Arrays.asList(args);
    }

    public abstract void execute() throws IOException, InvalidCatalogException, NoCatalogException, InvalidArgumentsException, InexistentDocumentException, DocumentExistsException;
}