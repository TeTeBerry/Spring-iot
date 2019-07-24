package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.model.DailyData;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.service.DataService;
import org.apache.catalina.filters.CorsFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class DataControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private DataService dataService;

    @InjectMocks DataController dataController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(dataController)
                .addFilters(new CorsFilter())
                .build();
    }

//    @Test
//    public void getDailyData() throws Exception{
//        Meter meter = new Meter();
//        meter.setMeterName("Sensor1");
//        List<DailyData> dailyDataList = Arrays.asList(
//                new DailyData("00:00",0),
//                new DailyData("00:01",0));
//
//        Mockito.when(dataService.getDailyData(meter.getMeterName(),"2019-07-24")).thenReturn(dailyDataList);
//    }

    @Test
    public void getMonthlyData() {
    }

    @Test
    public void getWeeklyData() {
    }
}