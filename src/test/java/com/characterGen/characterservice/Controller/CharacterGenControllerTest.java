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

import java.util.Optional;

import static org.mockito.Mockito.*;
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
        CharacterGen characterObj = new CharacterGen();
        when(char_service.saveCharacter(anyString(),anyString())).thenReturn(characterObj);
        mockMvc.perform(MockMvcRequestBuilders
        .post("/api/create/{char_name}/{class_name}","test","Warrior")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        verify(char_service,times(1)).saveCharacter(anyString(),anyString());
        verifyNoMoreInteractions(char_service);
    }

    @Test
    public void createNonCharTest() throws Exception {
        when(char_service.saveCharacter(anyString(),anyString())).thenThrow(new CharacterGenFails());
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/create/{char_name}/{class_name}","test","test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
        verify(char_service,times(1)).saveCharacter(anyString(),anyString());
        verifyNoMoreInteractions(char_service);
    }

    @Test
    public void updateCharTest() throws Exception {
        CharacterGen characterObj = new CharacterGen();
        when(char_service.updateCharacter(anyLong(),anyInt())).thenReturn(characterObj);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/update/{char_id}/{location}",1,4)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        verify(char_service,times(1)).updateCharacter(anyLong(),anyInt());
        verifyNoMoreInteractions(char_service);
    }


    @Test
    public void getCharByIdTest() throws Exception {
        CharacterGen characterGen = new CharacterGen();
        when(char_service.getCharById(anyLong())).thenReturn(characterGen);

        mockMvc.perform(MockMvcRequestBuilders
        .get("/api/get/{char_id}",6)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        verify(char_service,times(1)).getCharById(anyLong());
    }

    @Test
    public void getCharLocationByIdTest() throws Exception {
        when(char_service.getLocation(anyLong())).thenReturn(anyInt());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/get/location/{char_id}",6)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        verify(char_service,times(1)).getLocation(anyLong());
    }

    @Test
    public void getHitPointsTest() throws Exception {
        when(char_service.getHitPoints(anyLong())).thenReturn(anyInt());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/get/hitpoints/{char_id}",6)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        verify(char_service,times(1)).getHitPoints(anyLong());
    }

}
