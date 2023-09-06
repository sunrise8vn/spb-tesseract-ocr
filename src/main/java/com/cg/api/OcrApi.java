package com.cg.api;


import com.cg.model.OcrResult;
import com.cg.service.OcrService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/ocr")
public class OcrApi {

    @Autowired
    private OcrService ocrService;

//    @GetMapping("/test")
//    public ResponseEntity<?> test() {
//        String uri = "http://localhost:28201/api/test";
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map result = restTemplate.getForObject(uri, Map.class);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        Map<String, String> result = new HashMap<>();
        result.put("message", "Hello world");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException, TesseractException {
        OcrResult ocrResult = ocrService.ocr(file);

        String origin = ocrResult.getResult();
        String mix = removeNonAlphabet(ocrResult.getResult());

        Map<String, String> result = new HashMap<>();
        result.put("origin", origin);
        result.put("mix", mix);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/upload-base64")
    public ResponseEntity<?> uploadBase64(@RequestBody OcrResult ocrResult) throws IOException, TesseractException {

//        String base64String = "Base64 string here";
        byte[] bytes = Base64.getDecoder().decode(ocrResult.getResult().getBytes());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        MultipartFile multipartFile = new MockMultipartFile("file", inputStream);

        ocrResult = ocrService.ocr(multipartFile);

        String origin = ocrResult.getResult();
        String mix = removeNonAlphabet(ocrResult.getResult());
//        String mix = removingSpecialCharacters(ocrResult.getResult());
        mix = removingSpecialCharacters(ocrResult.getResult());

//        Map<String, String> result = new HashMap<>();
//        result.put("origin", origin);
//        result.put("mix", mix);

        return ResponseEntity.ok(mix);
    }

//    @PostMapping("/upload-base64")
//    public ResponseEntity<?> uploadBase64(@RequestBody OcrResult ocrResult) throws IOException, TesseractException {
//
//
////        String base64String = "Base64 string here";
//        byte[] bytes = Base64.getDecoder().decode(ocrResult.getResult().getBytes());
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
//        MultipartFile multipartFile = new MockMultipartFile("file", inputStream);
//
//        ocrResult = ocrService.ocr(multipartFile);
//
//        String origin = ocrResult.getResult();
//        String mix = removeNonAlphabet(ocrResult.getResult());
////        String mix = removingSpecialCharacters(ocrResult.getResult());
//        mix = removingSpecialCharacters(ocrResult.getResult());
//
//        Map<String, String> result = new HashMap<>();
//        result.put("origin", origin);
//        result.put("mix", mix);
//
//        return ResponseEntity.ok(result);
//    }

    public String removeNonAlphabet(String text) {
        text = text.replaceAll("—", "");
        text = text.replaceAll("_", "");
        text = text.replaceAll(",", "");
        text = text.replaceAll("\n", "");
        text = text.replaceAll("\\.", "");
        text = text.replaceAll("-", "");
        text = text.replaceAll("‘", "");
        text = text.replaceAll(" ", "");
        return text;
    }

    public static String removingLowerCaseCharacters(String str)
    {

        // Create a regular expression
        String regex = "[a-z]";

        // Compile the regex to create pattern
        // using compile() method
        Pattern pattern = Pattern.compile(regex);

        // Get a matcher object from pattern
        Matcher matcher = pattern.matcher(str);

        // Replace every matched pattern with the
        // target string using replaceAll() method
        return matcher.replaceAll("");
    }

    public static String removingSpecialCharacters(String str)
    {

        // Create a regular expression
        String regex = "[^A-Z0-9]";

        // Compile the regex to create pattern
        // using compile() method
        Pattern pattern = Pattern.compile(regex);

        // Get a matcher object from pattern
        Matcher matcher = pattern.matcher(str);

        // Replace every matched pattern with the
        // target string using replaceAll() method
        return matcher.replaceAll("");
    }
}
