package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.MeterService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;


import static org.junit.Assert.*;

/**
 *
 * Created by Chenziyu on 2019/6/22
**/

@RunWith(SpringRunner.class)
@WebMvcTest(MeterController.class)
public class MeterControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private MeterService service;


    @Test
    public void getMeters() throws Exception {

    }



    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}