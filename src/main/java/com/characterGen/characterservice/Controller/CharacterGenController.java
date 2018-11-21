package com.characterGen.characterservice.Controller;


import com.characterGen.characterservice.Entity.CharacterGen;
import com.characterGen.characterservice.Serivce.Characterservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
Scenario: Happy path
Given A running client with an internet connection.
When I call /character/gen/Garth/Warrior
Then I am returned a JSON character object using the following rules:

The values for int, wis, cha, str, des returned are generated at Random and all fall between 8 and 18.
If class = Warrior then the highest number should be assigned to Str and the lowest to Int,
if the class = Archer then the highest number goes to Dex and lowested to Cha,
if the class is Wizard then the highest number goes to Int and the lowest to Str,
if the class is Rogue then highest goes to Cha and the lowest goes to Str.
 IF any other class is provided it should return an error.

The character's hitPoint value should be set to con * 2.

The character should also be added to the Object Repository.
 */





@RestController
@RequestMapping("/api")
public class CharacterGenController {

    private final Characterservice char_service;

    @Autowired
    public CharacterGenController(Characterservice char_service){this.char_service = char_service;}

   @GetMapping("/ping")
   public String ping(){
       return "This is character generation Controller";
   }

   @PostMapping("/create/{char_name}/{class_name}")
   @ResponseStatus(HttpStatus.OK)
    public CharacterGen createCharacter(@PathVariable String char_name, @PathVariable String class_name) {
       return char_service.saveCharacter(char_name,class_name);
   }

   @GetMapping ("/update/{char_id}/{location}")
   @ResponseStatus(HttpStatus.OK)
    public CharacterGen updateCharacter(@PathVariable Long char_id,@PathVariable int location){
        return char_service.updateCharacter(char_id,location);
   }

   @GetMapping("/get/{char_id}")
    public CharacterGen getCharById(@PathVariable Long char_id){
        return char_service.getCharById(char_id);
   }
    @GetMapping("/get/location/{char_id}")
    public Integer getLocation(@PathVariable Long char_id){
        return char_service.getLocation(char_id);
    }

    @GetMapping("/get/hitpoints/{char_id}")
    public Integer getHitPoints(@PathVariable Long char_id){
        return char_service.getHitPoints(char_id);
    }
}

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Character generation fails for given name")
class CharacterGenFails extends RuntimeException{

}