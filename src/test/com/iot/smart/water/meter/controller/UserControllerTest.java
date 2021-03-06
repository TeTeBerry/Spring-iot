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

@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;
    private MockHttpSession session;

    private static final String testUserName = "test";

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        session = new MockHttpSession();
    }

    @Test
    public void register() throws Exception {
        String paramJson = "{\"username\":\"test\",\"password\":\"1111\"}";
        mvc.perform(MockMvcRequestBuilders.post("/iot/admin/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(paramJson)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void login() throws Exception {
        String paramJson = "{\"username\":\"" + testUserName + "\",\"password\":\"1111\"}";
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
                .header("token", "QQQWWWEEE")
                .param("oldPwd", "1111")
                .param("newPwd", "1234")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}