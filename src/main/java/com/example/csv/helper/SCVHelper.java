package com.example.csv.helper;

import com.example.csv.model.Tutorial;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


// cette class permet d'upload un fichier .csv en verifiant le Type
// et on transform un csv en une List des Objets => Tutorials
public class SCVHelper {

    public static String TYPE = "application/csv";
    static String[] headers = {"Id","Title","Description","Published"};

    public static  boolean isCSVFormat(MultipartFile file){

        if(!TYPE.equals(file.getContentType())){
            System.out.println(file.getContentType());
            return false;
        }
        return true;
    }



    // cette methode est pour la lecture et l atransformation d'un fichier CSV en une list des objest
    //
    public static List<Tutorial> csvToTutos(InputStream in) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Tutorial> tutorials = new ArrayList<>();

            // Obtient les enregistrements CSV du parser
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                System.out.println("Headers: " + csvRecord.toMap().keySet());

                // Vérifie si l'enregistrement CSV contient l'en-tête "Id"
                if (csvRecord.toMap().keySet().contains("Id")) {
                    System.out.println("ok! ");
                }

                System.out.println("Values: " + csvRecord.toMap().values());

                // Crée un objet Tutorial à partir des données CSV
                Tutorial tutorial = new Tutorial(
                        Long.parseLong(getCleanedValue(csvRecord, "Id")),
                        getCleanedValue(csvRecord, "Title"),
                        getCleanedValue(csvRecord, "Description"),
                        Boolean.parseBoolean(getCleanedValue(csvRecord, "Published"))
                );

                // Ajoute le tutoriel à la liste
                tutorials.add(tutorial);
            }

            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


    // Pour extrait une valeur nettoyée d'un enregistrement CSV en fonction du nom d'en-tête spécifié.
    private static String getCleanedValue(CSVRecord csvRecord, String headerName) {

        // Convertit l'enregistrement CSV en une carte, filtre par headerName, et extrait la valeur nettoyée.
        return csvRecord.toMap().entrySet().stream()
                .filter(entry -> entry.getKey().trim().equalsIgnoreCase(headerName))
                .findFirst()
                .map(entry -> entry.getValue().trim())
                .orElseThrow(() -> new RuntimeException("Header not found: " + headerName));
    }

}
