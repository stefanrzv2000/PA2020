package com.company.optional;


import com.company.compulsory.Catalog;
import com.company.compulsory.Document;
import com.company.exceptions.InvalidArgumentsException;
import com.company.exceptions.InvalidCatalogException;
import com.company.exceptions.NoCatalogException;

import java.io.*;
import java.util.Scanner;

public class LoadCommand extends Command{

    String filename;
    boolean binary;

    LoadCommand(CatalogShell shell, String[] args) throws InvalidArgumentsException {
        super(shell, args);
        if(args.length < 3) throw new InvalidArgumentsException("Usage: load {-b|-t} <FileName> ");
        else {
            binary = this.args.contains("-b");
            filename = args[2];
        }
    }

    @Override
    public void execute() throws IOException, InvalidCatalogException, NoCatalogException {

        String path = shell.getPath() + "/" + filename;
        //var documents = shell.getCatalog().getDocuments();

        if(binary){
            try(var ois = new ObjectInputStream(new FileInputStream(path))){
                Catalog catalog =  (Catalog) ois.readObject();
                shell.setCatalog(catalog);
            } catch (ClassNotFoundException e) {
                throw new InvalidCatalogException(e);
            }
        }
        else{
            try {


                File file = new File(path);
                Scanner scanner = new Scanner(file);

                String junk;
                String[] args = new String[3];

                for(int j = 0; j < 3; j++) {
                    junk = scanner.next();
                    scanner.skip(" ");
                    args[j] = scanner.nextLine();
                    //System.out.println(args[j]);
                }

                Catalog catalog = new Catalog(args[0],args[1]);

                int nrDocuments = Integer.parseInt(args[2]);
                for (int i = 0; i < nrDocuments; i++){
                    junk = scanner.nextLine();
                    for(int j = 0; j < 3; j++) {
                        junk = scanner.next();
                        scanner.skip(" ");
                        args[j] = scanner.nextLine();
                        //System.out.println(args[j]);
                    }
                    catalog.getDocuments().add(new Document(args[0],args[1],args[2]));
                }

                shell.setCatalog(catalog);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}