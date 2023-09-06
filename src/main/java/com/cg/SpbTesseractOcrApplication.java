package com.cg;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpbTesseractOcrApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpbTesseractOcrApplication.class, args);
    }

    @Bean
    Tesseract getTesseract(){
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("./tessdata");
        tesseract.setLanguage("eng");
        tesseract.setTessVariable("user_defined_dpi", "70");
//        tesseract.setPageSegMode(1);
//        tesseract.setOcrEngineMode(1);
        return tesseract;
    }

}
