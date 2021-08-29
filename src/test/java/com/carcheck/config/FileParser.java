package com.carcheck.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    String filePath;
    String REG_REGEX="[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}|[A-Z][0-9]{1,3}[A-Z]{3}|[A-Z]{3}[0-9]{1,3}[A-Z]|[0-9]{1,4}[A-Z]{1,2}|[0-9]{1,3}[A-Z]{1,3}|[A-Z]{1,2}[0-9]{1,4}|[A-Z]{1,3}[0-9]{1,3}|[A-Z]{1,3}[0-9]{1,4}|[0-9]{3}[DX]{1}[0-9]{3}";

    public FileParser(String path) {
        this.filePath = path;
    }

    public List<String> getLines() {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getRegNos(){
        List<String> regNos = new ArrayList<>();
        getLines().forEach(line -> {
            Matcher matcher = Pattern.compile(REG_REGEX).matcher(line);
            while(matcher.find()) {
                regNos.add(matcher.group(0));
            }
        });
        return regNos;
    }
}
