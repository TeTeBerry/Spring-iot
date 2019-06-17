package com.iot.smart.water.meter.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
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
@SpringBootTest()
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); //初始化MockMvc对象
        session = new MockHttpSession();

    }

    @Test
    public void register() {
    }

    @Test
    public void login() throws Exception{

        String json="{\"userName\":\"admin\",\"password\":\"111\"\"}";
        mvc.perform(MockMvcRequestBuilders.post("/iot/admin/login")
                .accept(MediaType.APPLICATION_JSON)
                .content(json.getBytes()) //传json参数
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void updatePassword() {
    }

    @Test
    public void addMeter() {
    }
}