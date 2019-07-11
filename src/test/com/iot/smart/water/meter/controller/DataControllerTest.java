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
public class DataControllerTest {
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
    public void getDailyData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/iot/data/getDailyData")
                .param("meterName","Sensor-1")
                .param("date","2019-07-03")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void getWeeklyData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/iot/data/getWeeklyData")
                .param("meterName","Sensor-1")
                .param("date","2019-07-01")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void getMonthlyData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/iot/data/getMonthlyData")
                .param("meterName","Sensor-1")
                .param("date","2019-07-01")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
