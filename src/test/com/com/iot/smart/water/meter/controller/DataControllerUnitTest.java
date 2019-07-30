package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.model.DailyData;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.MonthlyData;
import com.iot.smart.water.meter.model.WeeklyData;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.DataService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import java.util.List;



public class DataControllerUnitTest {


    @Mock
    private DataService dataService;

    @InjectMocks DataController dataController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getDailyData(){
        Meter meter = new Meter();
        meter.setMeterName("Sensor1");
        List<DailyData> list = new ArrayList<>();
        DailyData dailyData = new DailyData();
        dailyData.setHour("00:00");
        dailyData.setTotalMilliters(1000);
        list.add(dailyData);

        Mockito.when(dataService.getDailyData(meter.getMeterName(),"2019-07-24")).thenReturn(list);
        Response result = dataController.getDailyData(meter.getMeterName(),"2019-07-24");
        Assertions.assertThat(result.getCode()).isEqualTo(200);
    }

    @Test
    public void getMonthlyData() {
        Meter meter = new Meter();
        meter.setMeterName("Sensor1");
        List<MonthlyData> list = new ArrayList<>();
        MonthlyData monthlyData = new MonthlyData();
        monthlyData.setDay("1");
        monthlyData.setTotalMilliters(1000);
        list.add(monthlyData);

        Mockito.when(dataService.getMonthlyData(meter.getMeterName(),"2019-07-01")).thenReturn(list);
        Response result = dataController.getDailyData(meter.getMeterName(),"2019-07-01");
        Assertions.assertThat(result.getCode()).isEqualTo(200);
    }

    @Test
    public void getWeeklyData() {
        Meter meter = new Meter();
        meter.setMeterName("Sensor1");
        List<WeeklyData> list = new ArrayList<>();
        WeeklyData weeklyData = new WeeklyData();
        weeklyData.setWeek("Monday");
        weeklyData.setTotalMilliters(1000);
        list.add(weeklyData);

        Mockito.when(dataService.getWeeklyData(meter.getMeterName(),"2019-07-24")).thenReturn(list);
        Response result = dataController.getDailyData(meter.getMeterName(),"2019-07-24");
        Assertions.assertThat(result.getCode()).isEqualTo(200);
    }
}