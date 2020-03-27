package com.company.optional;

import com.company.exceptions.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ReportCommand extends Command{

    public ReportCommand(CatalogShell shell, String... args) throws InvalidArgumentsException {
        super(shell, args);
        if(args.length < 2) throw new InvalidArgumentsException("Usage: report {html|pdf|xls}");
    }

    @Override
    public void execute() throws IOException, InvalidCatalogException, NoCatalogException, InvalidArgumentsException, InexistentDocumentException, DocumentExistsException {
        var catalog = shell.getCatalog();
        if(catalog == null) throw new NoCatalogException();

        switch (args.get(1)){
            case "html":
                try {
                    FileOutputStream file = new FileOutputStream(shell.getPath() + "/" + catalog.getName() + ".html");
                    Writer writer = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8));

                    writer.write("<!DOCTYPE html>\n\t<head> \n \t\t<title> " + catalog.getName() +" </title> \n\t</head> \n");
                    writer.write("\t<body> \n");

                    writer.write(catalog.toHTML());

                    writer.write("\t</body> \n</html>");

                    writer.flush();
                    file.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                return;

            case "pdf":
                try {
                    PDDocument pdf = new PDDocument();

                    PDPage blankPage = new PDPage();
                    pdf.addPage(blankPage);

                    PDPage page = pdf.getPage(0);

                    PDPageContentStream contentStream = new PDPageContentStream(pdf, page);
                    contentStream.beginText();
                    contentStream.setFont( PDType1Font.TIMES_ROMAN, 16 );
                    contentStream.setLeading(14.5f);
                    contentStream.newLineAtOffset(25, 725);

                    var lines = catalog.toString().split("\n");
                    for(var line: lines) {
                        contentStream.showText(line);
                        contentStream.newLine();
                    }


                    contentStream.endText();
                    contentStream.close();

                    pdf.save(shell.getPath() + "/" + catalog.getName() + ".pdf");

                    pdf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;

            //case "xls":
            //    return;

            default: throw new InvalidArgumentsException("Format " + args.get(1) + " not suported.");
        }
    }
}
