package com.example.csv;

import com.example.csv.services.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileReader;

@Component
public class CSVReader implements CommandLineRunner {

    private final CSVService csvService;

    @Autowired
    public CSVReader(CSVService csvService, ResourceLoader resourceLoader) {
        this.csvService = csvService;

    }


    @Override
    public void run(String... args) throws Exception {
        // Lisez le fichier CSV et créez une liste d'objets
        csvService.readAndSaveCSV();

    }
}
