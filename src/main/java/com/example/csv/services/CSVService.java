package com.example.csv.services;

import com.example.csv.helper.SCVHelper;
import com.example.csv.model.Tutorial;
import com.example.csv.repo.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {

    @Autowired
    TutorialRepository tutorialRepository;

    public void saveSCV(MultipartFile file){
        try{
            List<Tutorial> tutorials = SCVHelper.csvToTutos(file.getInputStream());
            tutorialRepository.saveAll(tutorials);
        }catch(IOException e){
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    //retournant une liste des Tutorials
    public List<Tutorial> getAllTutorials(){
        return tutorialRepository.findAll();
    }


    // la possibilité d'ajouter une 1 tutorial à la base de
    //données
    public void addTutorial(Tutorial tutorial){
        tutorialRepository.save(tutorial);
    }

    //la possibilité d'ajouter une 1 Tutorial à la base de
    //données en générant des valeurs aléatoires
    public void addAleatoireTutorial() {
        Tutorial tutorial = new Tutorial();
        tutorial.setTitle(generateRandomString());
        tutorial.setDescription(generateRandomString());
        tutorial.setPublished(Math.random() < 0.5);
        System.out.println(tutorial);
        tutorialRepository.save(tutorial);
    }

    private String generateRandomString() {
        // Logic to generate a random string (you can customize this based on your requirements)
        return "tutorial" + Math.random() + "Tuto";
    }

}
