package com.characterGen.characterservice.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
//@Entity
public class CharacterObj {

   String name;

    String char_json;

    public String getChar_json() {
        return char_json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChar_json(String char_json) {
        this.char_json = char_json;
    }
}
