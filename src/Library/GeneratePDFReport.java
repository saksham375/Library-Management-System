/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
package Library;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


public class GeneratePDFReport {
    Connection con = DBManager.getConnection();

    public void createPDF(String query, Object[] parameters, String[] columnNames, String reportTitle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String filePath = "report_" + timestamp + ".pdf";

        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Font.UNDERLINE);
            Paragraph heading = new Paragraph("Library Management System Report", headingFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);

            document.add(Chunk.NEWLINE);

            Paragraph intro = new Paragraph("This report contains information about the library system.", FontFactory.getFont(FontFactory.HELVETICA, 12));
            intro.add(Chunk.NEWLINE);
            intro.add(new Phrase(reportTitle, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            intro.add(Chunk.NEWLINE);
            intro.add(new Phrase("Library Statistics for the Current Moment", FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(intro);

            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(columnNames.length);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = new float[columnNames.length];
            Arrays.fill(columnWidths, 1f / columnNames.length); // Equal width for all columns

            table.setWidths(columnWidths);

            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            for (String columnName : columnNames) {
                PdfPCell cell = new PdfPCell(new Phrase(columnName, tableHeaderFont));
                table.addCell(cell);
            }

            try (Connection con = DBManager.getConnection();
                 PreparedStatement stmt = con.prepareStatement(query)) {

                // Set the parameter values
                for (int i = 0; i < parameters.length; i++) {
                    stmt.setObject(i + 1, parameters[i]);
                }

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    for (String columnName : columnNames) {
                        String columnValue = rs.getString(columnName);
                        table.addCell(columnValue);
                    }

                    // Calculate the required height of the table
                    float tableHeight = table.calculateHeights();
                    float remainingHeight = writer.getVerticalPosition(true) - document.bottomMargin();

                    // Check if the table exceeds the remaining height on the page
                    if (tableHeight > remainingHeight) {
                        document.add(table); // Add the current table to the document
                        document.newPage(); // Create a new page for the next set of data
                        table.deleteBodyRows(); // Clear the table's current content
                    }
                }

                // Add the remaining table to the document
                document.add(table);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            document.close();

            System.out.println("PDF report created successfully: " + filePath);

            // Print the PDF document
            printPDF(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printPDF(String filePath) {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(PDDocument.load(new java.io.File(filePath))));
            job.print();
        } catch (PrinterException | IOException e) {
            e.printStackTrace();
        }
    }
}
*/
package Library;
import java.awt.print.PrinterException;
import java.io.IOException;

import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.print.PrinterJob;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class GeneratePDFReport {
    Connection con = DBManager.getConnection();

    public void createPDF(String query, Object[] parameters, String[] columnNames, String reportTitle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String filePath = "report_" + timestamp + ".pdf";

        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Font.UNDERLINE);
            Paragraph heading = new Paragraph("Library Management System Report", headingFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);

            document.add(Chunk.NEWLINE);

            Paragraph intro = new Paragraph("This report contains information about the library system.",
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            intro.add(Chunk.NEWLINE);
            intro.add(new Phrase(reportTitle, FontFactory.getFont(FontFactory.HELVETICA, 12)));
            intro.add(Chunk.NEWLINE);
            intro.add(new Phrase("Library Statistics for the Current Moment",
                    FontFactory.getFont(FontFactory.HELVETICA, 12)));
            document.add(intro);

            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(columnNames.length);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = new float[columnNames.length];
            Arrays.fill(columnWidths, 1f / columnNames.length); // Equal width for all columns

            table.setWidths(columnWidths);

            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            for (String columnName : columnNames) {
                PdfPCell cell = new PdfPCell(new Phrase(columnName, tableHeaderFont));
                table.addCell(cell);
            }

            try (Connection con = DBManager.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {

                // Set the parameter values
                for (int i = 0; i < parameters.length; i++) {
                    stmt.setObject(i + 1, parameters[i]);
                }

                ResultSet rs = stmt.executeQuery();

                int rowCount = 0;
                int maxRowsPerPage = getMaxRowsPerPage(document, table, writer);

                while (rs.next()) {
                    rowCount++;

                    // Add data to the table
                    for (String columnName : columnNames) {
                        String columnValue = rs.getString(columnName);
                        table.addCell(columnValue);
                    }

                    // Check if the table exceeds the maximum rows per page
                    if (rowCount % maxRowsPerPage == 0) {
                        document.add(table); // Add the current table to the document
                        document.newPage(); // Create a new page for the next set of data
                        table.deleteBodyRows(); // Clear the table's current content
                    }
                }

                // Add the remaining table to the document
                document.add(table);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            document.close();

            System.out.println("PDF report created successfully: " + filePath);

            // Print the PDF document
            printPDF(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getMaxRowsPerPage(Document document, PdfPTable table, PdfWriter writer) {
        float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
        float rowHeight = table.getTotalHeight() / table.size();

        return (int) (documentHeight / rowHeight);
    }

    private void printPDF(String filePath) {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(PDDocument.load(new java.io.File(filePath))));
            job.print();
        } catch (PrinterException | IOException e) {
            e.printStackTrace();
        }
    }
}


