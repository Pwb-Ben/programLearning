package com.programlearning.learning.tesseract;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class TesseractTest {

    public static void main(String[] args) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        tesseract.setLanguage("chi_sim");

        File imageFile = new File("C:\\Users\\ecut\\Desktop\\008.png");
        String result = tesseract.doOCR(imageFile);

        System.out.println(result.replaceAll(" ", ""));
    }

}
