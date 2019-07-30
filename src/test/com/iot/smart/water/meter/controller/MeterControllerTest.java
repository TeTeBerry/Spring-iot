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

    private static final String testToken= "QQQWWWEEE";

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        session = new MockHttpSession();
    }

    @Test
    public void getWaterBill() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/iot/meter/getWaterBill")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("meterName","123")
                .param("password","1111")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void setMemberVolume() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/iot/meter/setVolume")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("token", "MEMBERQQQWWW")
                .param("member_id", "49")
                .param("meter_id", "64")
                .param("volume", "500")
                .param("password","1234")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

//    @Test
//    public void getMeters() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.get("/iot/meter/getMeters")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .session(session))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }

    @Test
    public void update() throws Exception {
        String json = "{\"mid\":\"18\",\"meterName\":\"Sensor-1\",\"meterDesc\":\"G3&4flowSensor\",\"name\":\"tete\",\"room\":\"B123\",\"contact\":\"test111111@qq.com\"}";
        mvc.perform(MockMvcRequestBuilders.post("/iot/meter/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token", "QQQWWWEEE")
                .content(json)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/iot/meter/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("token", testToken)
                .param("mid", "58")
                .param("bid","42")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void addMeter() throws Exception {
        String paramJson = "{\"meterName\":\"sensor-2\",\"meterDesc\":\"tebafao\",\"name\":\"tetebaobao\",\"room\":\"1222\",\"contact\":\"t11111@qq.com\",\"password\":\"1234\"}";
        mvc.perform(MockMvcRequestBuilders.post("/iot/meter/addMeter")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("token", "QQQWWWEEE")
                .param("user_id", "11")
                .content(paramJson)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
