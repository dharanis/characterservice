package com.characterGen.characterservice.Serivce;

import com.characterGen.characterservice.Entity.CharacterGen;
import com.characterGen.characterservice.Entity.CharacterObj;
import com.characterGen.characterservice.Repository.CharacterGenRepo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;


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

@Service
public class Characterservice {


    CharacterGenRepo char_gen_repo;

    @Autowired
    public Characterservice(CharacterGenRepo char_gen_repo) {
        this.char_gen_repo = char_gen_repo;
    }

    public CharacterGen saveCharacter( String char_name, String class_name) throws JsonProcessingException {

        CharacterGen char_req = generateCharacter(char_name, class_name);
        return char_gen_repo.save(char_req);
    }

    public CharacterGen generateCharacter(String char_name, String class_name) throws JsonProcessingException {

        int[] ran = randomNum().toArray();
        CharacterGen char_gen = new CharacterGen();
        CharacterObj char_obj_json = new CharacterObj();
        if (class_name.equals("Warrior")) {
            char_gen.setChar_name(char_name);
            char_gen.setChar_class("Warrior");
            char_gen.setStrength(ran[0]);
            char_gen.setIntelligence(ran[4]);
            char_gen.setCharacter(ran[1]);
            char_gen.setDexterity(ran[2]);
            char_gen.setWisdom(ran[3]);

        } else if (class_name.equals("Archer")) {
            char_gen.setChar_name(char_name);
            char_gen.setChar_class(class_name);
            char_gen.setDexterity(ran[0]);
            char_gen.setCharacter(ran[4]);
            char_gen.setIntelligence(ran[1]);
            char_gen.setStrength(ran[2]);
            char_gen.setWisdom(ran[3]);

        } else if (class_name.equals("Wizard")) {

            char_gen.setChar_name(char_name);
            char_gen.setChar_class(class_name);
            char_gen.setIntelligence(ran[0]);
            char_gen.setStrength(ran[4]);
            char_gen.setCharacter(ran[1]);
            char_gen.setDexterity(ran[2]);
            char_gen.setWisdom(ran[3]);
        } else if (class_name.equals("Rogue")) {

            char_gen.setChar_name(char_name);
            char_gen.setChar_class(class_name);
            char_gen.setCharacter(ran[0]);
            char_gen.setStrength(ran[4]);
            char_gen.setIntelligence(ran[1]);
            char_gen.setDexterity(ran[2]);
            char_gen.setWisdom(ran[3]);
        }
        else
        {
            char_gen.setChar_name(HttpStatus.NOT_FOUND.toString());
            char_gen.setChar_class("Not Found");
        }
        char_gen.setCon(2);
        char_gen.setHitPoints(char_gen.getCon() * 2);
        char_gen.setInventory(4);
        char_gen.setLocation(6);
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(char_gen);
        char_obj_json.setChar_json(json);

        return char_gen;
    }

    public IntStream randomNum() {
        Random ran = new Random();
        return ran.ints(5, 8, 18).sorted();

    }

}
