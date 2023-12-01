package com.example.csv.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// cette class represente Les messages qui doivent etre transmit
// a l'utilisateure lors d'une request
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private String message;

}
