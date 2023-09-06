package com.cg.service;

import com.cg.model.OcrResult;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class OcrService {
    @Autowired
    private Tesseract tesseract;

    @Autowired
    private ResourceLoader resourceLoader;

    public OcrResult ocr(MultipartFile file) throws TesseractException, IOException {
        File convFile = convert(file);
//        ClassPathResource classPathResource = new ClassPathResource("/tessdata");
//        Resource resource = resourceLoader.getResource("tessdata");
//        InputStream inputStream = resource.getInputStream();
//        String dataFolder = inputStream.toString();
//        tesseract.setDatapath("./static/tessdata");
        String text = tesseract.doOCR(convFile);
        OcrResult ocrResult = new OcrResult();
        ocrResult.setResult(text);
        return ocrResult;
    }

    public static File convert(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
        File convFile = new File("bsx.png");
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
