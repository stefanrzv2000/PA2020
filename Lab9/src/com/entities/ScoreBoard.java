package com.entities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    String title;
    List<ScoreBoardEntry> entries;

    public ScoreBoard(String title){
        this.title = title;
        entries = new ArrayList<>();
    }

    public void print(int number){

        entries.sort(ScoreBoardEntry::compareTo);

        int place = 0;

        System.out.println(title);
        System.out.println("|-------|-------------------------------|-------|");
        System.out.println("|Place\t|Name\t\t\t\t\t\t\t|Score\t|");
        System.out.println("|-------|-------------------------------|-------|");

        for(var entry: entries){
            place++;
            //System.out.println("|" + place + ". \t|" + entry.getArtist().getName() + "\t|" + entry.getScore() + "\t|");
            System.out.printf("|%d. \t|%31s|%2.2f\t|\n",place,entry.getArtist().getName(),entry.getScore());
            if(place >= number) break;
        }
        System.out.println("|-------|-------------------------------|-------|");
    }

    public void printHTML(String fileName, int number){

        entries.sort(ScoreBoardEntry::compareTo);

        DecimalFormat df2 = new DecimalFormat("#.##");

        try(PrintWriter out = new PrintWriter(new FileWriter(fileName))){

            out.println("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "table {\n" +
                    "  font-family: arial, sans-serif;\n" +
                    "  border-collapse: collapse;\n" +
                    "  width: 100%;\n" +
                    "}\n" +
                    "\n" +
                    "td, th {\n" +
                    "  border: 1px solid #dddddd;\n" +
                    "  text-align: left;\n" +
                    "  padding: 8px;\n" +
                    "}\n" +
                    "\n" +
                    "tr:nth-child(even) {\n" +
                    "  background-color: #dddddd;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>");

            out.println("<h2>" + title + "</h2>");

            out.println("<table>\n" +
                    "  <tr>\n" +
                    "    <th>Place</th>\n" +
                    "    <th>Name</th>\n" +
                    "    <th>Country</th>\n" +
                    "    <th>Score</th>\n" +
                    "  </tr>");

            int place = 0;

            for(var entry: entries){
                place++;
                //System.out.println("|" + place + ". \t|" + entry.getArtist().getName() + "\t|" + entry.getScore() + "\t|");
                //System.out.printf("|%d. \t|%31s|%2.2f\t|\n",place,entry.getArtist().getName(),entry.getScore());

                out.println("<tr>\n" +
                        "    <td>" + place + "</td>\n" +
                        "    <td>" + entry.getArtist().getName() + "</td>\n" +
                        "    <td>" + entry.getArtist().getCountry() + "</td>\n" +
                        "    <td>" + df2.format(entry.getScore()) + "</td>\n" +
                        "  </tr>");

                if(place >= number) break;
            }

            out.println("</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addEntry(ScoreBoardEntry entry){
        entries.add(entry);
    }
}
