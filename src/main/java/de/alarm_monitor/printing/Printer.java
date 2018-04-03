package de.alarm_monitor.printing;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;


class Printer {


    public static void print(final File file, final int numberOfCopies) throws PrinterException, IOException {

        if (file == null) {
            throw new NullPointerException();
        }
        if (numberOfCopies < 1) {
            throw new IllegalArgumentException("Number of copies was negative: " + numberOfCopies);
        }

        final PDDocument doc = PDDocument.load(file);
        final PrinterJob job = PrinterJob.getPrinterJob();
        final PageFormat pf = job.defaultPage(); // standard PageFormat holen (bei
        // uns a4 - beinhaltet groe�e in
        // pixeln und r�nder
        final Paper temp = pf.getPaper();
        temp.setImageableArea(0, 0, temp.getWidth(), temp.getHeight());
        pf.setPaper(temp); // Den Bedruckbaren Rand auf Seitengr��e anpassen
        job.setPrintable(new PDFPrintable(doc), pf);
        job.setCopies(numberOfCopies);
        job.print();
        doc.close();
    }
}
