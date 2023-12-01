package com.example.csv.controller;

import com.example.csv.helper.SCVHelper;
import com.example.csv.message.ResponseMessage;
import com.example.csv.model.Tutorial;
import com.example.csv.services.CSVService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    CSVService csvService;

    // Gère l'upload d'un fichier CSV.
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file){
        String massage = "";  // the message to send to the user
        if(SCVHelper.isCSVFormat(file)){
            try{
                // Enregistre le fichier CSV dans le service CSV
                csvService.saveSCV(file);

                massage = "file uploaded successfully";

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(massage));
            }catch (Exception e){
                massage  = "failed to upload the File";
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(massage));
            }
        }

        // Si le fichier n'est pas au format CSV, retourne une réponse HTTP BAD_REQUEST avec un message approprié
        massage = "the uploaded file is not a csv file !";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(massage));
    }


    // retourner la liste des Objets
    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getTutorials(){
        try{
            List<Tutorial> tutorials = csvService.getAllTutorials();

            if(tutorials.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(tutorials,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // ajouter un objet à la base de données
    @PostMapping("/addTuto")
    public ResponseEntity<ResponseMessage> addTutorial(@RequestBody Tutorial tutorial){
        if(tutorial != null){
            csvService.addTutorial(tutorial);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Tutorial added successfully"));
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("invalid Tutorial Object! "));
    }

    // ajouter un Objet géneré aleatoirement à la base de données
    @PostMapping("/addAleatoireTutorial")
    public ResponseEntity<ResponseMessage> addAleatoireTito(){
        csvService.addAleatoireTutorial();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Tutorial added successfully"));
    }



}
