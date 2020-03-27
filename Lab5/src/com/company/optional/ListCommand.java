package com.company.optional;

import com.company.exceptions.InvalidArgumentsException;
import com.company.exceptions.InvalidCatalogException;
import com.company.exceptions.NoCatalogException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListCommand extends Command {

    public ListCommand(CatalogShell shell, String... args) throws InvalidArgumentsException {
        super(shell, args);
        if(args.length < 2) throw new InvalidArgumentsException("Usage: list -c|-d");
    }

    @Override
    public void execute() throws IOException, InvalidCatalogException, NoCatalogException, InvalidArgumentsException {
        switch (args.get(1)){
            case "-c":
                var catalog = shell.getCatalog();
                if(catalog == null){
                    throw new NoCatalogException("There is no catalog opened!");
                }
                if(catalog.getDocuments().isEmpty()){
                    System.out.println("The selected Catalog is empty.");
                }
                for(var document : catalog.getDocuments()){
                    System.out.println("Document: ID: " + document.getId() + " | name: " +  document.getName());
                }
                return;
            case "-d":
                File folder = new File(shell.getPath());

                File[] listOfFiles = folder.listFiles();

                assert listOfFiles != null;
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        System.out.println("File " + listOfFile.getName());
                    } else if (listOfFile.isDirectory()) {
                        System.out.println("Directory " + listOfFile.getName());
                    }
                }
                return;
            default: throw new InvalidArgumentsException("Usage: list -c|-d");

        }
    }
}
