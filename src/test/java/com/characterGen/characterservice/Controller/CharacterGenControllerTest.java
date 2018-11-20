package com.characterGen.characterservice.Controller;


import com.characterGen.characterservice.Entity.CharacterGen;
import com.characterGen.characterservice.Entity.CharacterObj;
import com.characterGen.characterservice.Serivce.Characterservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CharacterGenControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    Characterservice char_service;


    @Before
    public void setUp(){}



    @Test
    public void createCharacterTest() throws Exception {
        CharacterObj characterObj = new CharacterObj();
        when(char_service.saveCharacter("test","Warrior")).thenReturn(characterObj);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/api/create/test/Warrior")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(characterObj))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        verify(char_service,times(1)).saveCharacter("test","Warrior");
    }
}
