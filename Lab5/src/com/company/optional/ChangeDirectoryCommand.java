package com.company.optional;

import com.company.exceptions.InvalidCatalogException;

import java.io.IOException;

public class ChangeDirectoryCommand extends Command {

    ChangeDirectoryCommand(CatalogShell shell,String ...args){
        super(shell,args);
    }

    @Override
    public void execute() throws IOException, InvalidCatalogException {
        String newPath;
        if(args.size() < 2){
            newPath = System.getProperty("user.dir");
        }
        else{
            newPath = shell.getPath() + "/" + args.get(1);
        }
        shell.setPath(newPath);
    }
}
