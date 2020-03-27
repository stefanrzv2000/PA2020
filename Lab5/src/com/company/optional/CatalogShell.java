package com.company.optional;

import com.company.compulsory.Catalog;
import com.company.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CatalogShell {

    private List<Command> previousCommands;

    private Scanner in;

    private String path;

    boolean active;

    Catalog catalog = null;

    public CatalogShell(){
        previousCommands = new ArrayList<>();
        path = System.getProperty("user.dir");
        in = new Scanner(System.in);
        active = true;
    }

    public void start(){

        System.out.println("Welcome to catalog shell for help type help");

        while(active){
            printNewLine();
            Command command = null;
            try {
                command = readCommand();
            } catch (InvalidArgumentsException e) {
                e.printReason();
            }

            if(command == null) continue;
            try {
                command.execute();
            } catch (IOException | InvalidCatalogException e) {
                e.printStackTrace();
            } catch (NoCatalogException e){
                e.printReason();
            } catch (InvalidArgumentsException e) {
                e.printReason();
            } catch (InexistentDocumentException e) {
                System.err.println(e.toString());
            } catch (DocumentExistsException e) {
                System.err.println(e.toString());
            }
            previousCommands.add(command);
        }

    }

    private void printNewLine(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print(path + ">>");
        System.out.flush();
    }

    Command readCommand() throws InvalidArgumentsException {
        String s = in.nextLine();
        String[] args = s.split(" ");
        switch(args[0]){
            case "load":
                return new LoadCommand(this, args);

            case "save":
                return new SaveCommand(this,args);

            case "create":
                return new CreateCommand(this,args);

            case "help":
                return new HelpCommand();

            case "cd":
                return new ChangeDirectoryCommand(this,args);

            case "add":
                return new AddCommand(this,args);

            case "report":
                return new ReportCommand(this,args);

            case "info":
                return new InfoCommand(this,args);

            case "exit":
                active = false;
                return null;

            case "list":
                return new ListCommand(this,args);

            case "view":
                return new ViewCommand(this,args);

            default:
                System.err.println("Invalid command, try help for a list of available commands");
                return null;
        }
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
