package com.iot.smart.water.meter.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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



/**
 *
 * Created by Chenziyu on 2019/6/15
 **/

@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;
    private MockHttpSession session;

    private static final String testToken = "72F97DC34A9D0FFD45E5FC1D963EB01A";
    private static final String testUserName = "test";

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); //初始化MockMvc对象
        session = new MockHttpSession();
    }


    @Test
    public void register() throws Exception {
        String paramJson = "{\"userName\":\"testnen\",\"password\":\"111\"}";
        mvc.perform(MockMvcRequestBuilders.post("/iot/admin/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(paramJson)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void login() throws Exception {
        String paramJson = "{\"userName\":\"" + testUserName + "\",\"password\":\"1112\"}";
        String response = mvc.perform(MockMvcRequestBuilders.post("/iot/admin/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(paramJson)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
        JSONObject resultJson = JSON.parseObject(response);
        if (resultJson != null) {
            String token = resultJson.getString("msg");
            System.out.println("token: " + token);
        }
    }

    @Test
    public void updatePassword() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/iot/admin/updatePassword")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("userName", "admin")
                .param("oldPwd", "1111")
                .param("newPwd", "1112")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addMeter() throws Exception {
        String paramJson = "{\"meterName\":\"sensorasdfte\",\"meterDesc\":\"tebaobasdfao\",\"memberName\":\"tete\",\"room\":\"1\",\"memberContact\":\"t111111@qq.com\"}";
        mvc.perform(MockMvcRequestBuilders.post("/iot/admin/addMeter")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(paramJson)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}