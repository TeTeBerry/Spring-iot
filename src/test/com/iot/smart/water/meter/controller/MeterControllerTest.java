package com.iot.smart.water.meter.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeterControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); //初始化MockMvc对象
        session = new MockHttpSession();
    }

    @Test
    public void getWaterBill() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/iot/meter/getWaterBill")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void setMemberVolume() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/iot/meter/setMemberVolume")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("memberName", "tetebaobao")
                .param("volume", "100.0f")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getMeters() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/iot/meter/getMeters")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update() throws Exception {
        String json = "{\"mid\":\"18\",\"meterName\":\"Sensor-1\",\"meterDesc\":\"G3&4flowSensor\",\"memberName\":\"tete\",\"room\":\"B123\",\"memberContact\":\"test111111@qq.com\"}";
        mvc.perform(MockMvcRequestBuilders.post("/iot/meter/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/iot/meter/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("mid", "8")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
