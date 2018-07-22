package de.alarm_monitor.parsing;


import com.google.inject.Inject;
import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.exception.OcrParserException;
import de.alarm_monitor.main.SystemInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class OCRProcessorImpl1 implements OCRProcessor {

    private final static Logger logger = LogManager.getLogger(OCRProcessorImpl1.class);
    private static String tPath = null;
    private final MainConfiguration configuration;
    private final PngConverter pngConverter;
    private final SystemInformation systemInformation;

    @Inject
    public OCRProcessorImpl1(final Provider<MainConfiguration> configurationProvider, PngConverter pngConverter, SystemInformation systemInformation) {
        configuration = configurationProvider.get();
        this.pngConverter = pngConverter;
        this.systemInformation = systemInformation;
    }


    private String getOCROfFile(final File pdf) throws IOException, TikaException, SAXException {
        if (tPath == null) {
            tPath = configuration.path_tesseract();
        }
        final Parser parser = new AutoDetectParser();
        final BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);
        final TesseractOCRConfig tesseractOCRConfig = new TesseractOCRConfig();
        tesseractOCRConfig.setTesseractPath(tPath);
        tesseractOCRConfig.setLanguage(configuration.getOcrPacket());
        logger.trace("Used language for OCR: " + tesseractOCRConfig.getLanguage());
        final PDFParserConfig pdfConfig = new PDFParserConfig();
        pdfConfig.setExtractInlineImages(true);
        pdfConfig.setExtractUniqueInlineImagesOnly(false); // set to false if
        // pdf contains
        // multiple images.
        final ParseContext parseContext = new ParseContext();
        parseContext.set(TesseractOCRConfig.class, tesseractOCRConfig);
        parseContext.set(PDFParserConfig.class, pdfConfig);
        // need to add this to make sure recursive parsing happens!
        parseContext.set(Parser.class, parser);
        final FileInputStream stream = new FileInputStream(pdf);
        final Metadata metadata = new Metadata();
        parser.parse(stream, handler, metadata, parseContext);
        return handler.toString();
    }





    @Override
    public String pdfToString(final File pdf) throws OcrParserException {
        try {

            if(configuration.convertToPng()){
                logger.info("Converting PDF {} to PNG" , pdf.getAbsolutePath());
                String parsedText = getOCROfFile(new File(pngConverter.convertToPng(pdf)));
                writeIntoFile( parsedText);
                return parsedText;

            }else{
                String parsedText = getOCROfFile(pdf);
                writeIntoFile( parsedText);
                return parsedText;
            }
        } catch (final Exception e) {
            throw new OcrParserException(e);
        }
    }




    public void writeIntoFile(String text){

        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_YYYY_HH_mm_ss");

        String metadata = "Metadaten: \n OCR_Packet" + configuration.getOcrPacket() +
                "\n Zeitpunkt des OCR Vorgangs: " + sdf.format(System.currentTimeMillis()) +
                "\nConvert to Png before: " + configuration.convertToPng()+
                "\nDPI der PNG Datei: " + configuration.getDpiPng() +
                "\n\n #########  Text ###########\n\n";








        try(FileWriter writer = new FileWriter(new File(  systemInformation.getWorkingFolder(), sdf.format(System.currentTimeMillis())+ ".txt"))){

            writer.write(metadata + text);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
