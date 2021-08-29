package com.carcheck.config;


public class FileConfig {


    public FileParser fileParser(String filePath) {
        return new FileParser(filePath);
    }
}
