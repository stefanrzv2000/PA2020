package com.company.optional;

import com.company.compulsory.Document;
import com.company.exceptions.InvalidArgumentsException;
import com.company.exceptions.InvalidCatalogException;
import com.company.exceptions.NoCatalogException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SaveCommand extends Command {

    public SaveCommand(CatalogShell shell, String... args) {
        super(shell, args);
    }

    @Override
    public void execute() throws IOException, NoCatalogException, InvalidArgumentsException {
        if(shell.getCatalog() == null) throw new NoCatalogException("Nu ati deschis un catalog inca!");

        if(args.size() < 2) throw new InvalidArgumentsException("Usage: save {-b|-t} <FileName>");

        if(!args.contains("-t") & !args.contains("-b")) throw new InvalidArgumentsException("Usage: save {-b|-t} <FileName>");

        shell.getCatalog().setPath(shell.getPath() + "/" + args.get(2));

        if( args.contains("-b")) {
            try (var oos = new ObjectOutputStream(new FileOutputStream(shell.getCatalog().getPath()))) {
                oos.writeObject(shell.getCatalog());
            }
            return;
        }


        try{
            FileOutputStream file = new FileOutputStream(shell.getCatalog().getPath());
            Writer writer = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8));

            var documents = shell.getCatalog().getDocuments();

            writer.write("name: " + shell.getCatalog().getName() + "\n");
            writer.write("path: " + shell.getCatalog().getPath() + "\n");
            writer.write("Catalog_Size: " + documents.size() + "\n");

            int i = 0;
            for (Document document : documents){
                writer.write("Doocument[" + i + "]:" + document.toString() + "\n");
                i++;
            }

            writer.close();
            file.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
