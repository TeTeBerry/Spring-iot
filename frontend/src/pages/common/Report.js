import React, { Component } from "react";
import { Card } from "antd";
import axios from "axios";
import { Chart, Geom, Axis, Tooltip } from "bizcharts";

var currentDate = new Date();
var date = currentDate.getDate();
var month = currentDate.getMonth();
var year = currentDate.getFullYear();
var dateString = year + "-" + (month + 1) + "-" + date;

class Report extends Component {
  state = {
    dayData: [],
    weekData: [],
    monthData: []
  };

  componentDidMount() {
    this.getDailyReport();
    this.getWeeklyReport();
    this.getMonthlyReport();
  }

  getDailyReport() {
    axios
      .get(
        `http://localhost:8080/iot/data/getDailyData?meterName=Sensor-1&date=2019-7-3`
      )
      .then(res => {
        if (res.data.code === 200) {
          this.setState({
            dayData: res.data.data
          });
        }
        console.log(dateString);
      })
      .catch(error => {
        console.log(error);
      });
  }

  getWeeklyReport() {
    axios
      .get(
        `http://localhost:8080/iot/data/getWeeklyData?meterName=Sensor-1&date=2019-7-3`
      )
      .then(res => {
        if (res.data.code === 200) {
          this.setState({
            weekData: res.data.data
          });
        }
      })
      .catch(error => {
        console.log(error);
      });
  }

  getMonthlyReport() {
    axios
      .get(
        `http://localhost:8080/iot/data/getMonthlyData?meterName=Sensor-1&date=${dateString}`
      )
      .then(res => {
        if (res.data.code === 200) {
          this.setState({
            monthData: res.data.data
          });
        }
      })
      .catch(error => {
        console.log(error);
      });
  }

  render() {
    const { dayData } = this.state;
    const daycols = {
      totalMilliters: {
        tickInterval: 1500
      }
    };
    const { weekData } = this.state;
    const weekcols = {
      totalMilliters: {
        tickInterval: 2000
      }
    };
    const { monthData } = this.state;
    const monthcols = {
      totalMilliters: {
        tickInterval: 3000
      }
    };

    return (
      <div>
        <Card title="Water report">
          <p
            style={{
              fontSize: 14,
              color: "rgba(0, 0, 0, 0.85)",
              marginBottom: 16,
              fontWeight: 500
            }}
          />

          <Card type="inner" title="Daliy report">
            <Chart height={400} data={dayData} scale={daycols} forceFit>
              <Axis name="hour" />
              <Axis name="totalMilliters" />
              <Tooltip
                crosshairs={{
                  type: "y"
                }}
              />
              <Geom type="interval" position="hour*totalMilliters" />
            </Chart>
          </Card>

          <Card type="inner" title="Weekly report">
            <Chart height={500} data={weekData} scale={weekcols} forceFit>
              <Axis name="week" />
              <Axis name="totalMilliters" />
              <Tooltip
                crosshairs={{
                  type: "y"
                }}
              />
              <Geom type="interval" position="week*totalMilliters" />
            </Chart>
          </Card>

          <Card type="inner" title="Monthly report">
            <Chart height={500} data={monthData} scale={monthcols} forceFit>
              <Axis name="day" />
              <Axis name="totalMilliters" />
              <Tooltip
                crosshairs={{
                  type: "y"
                }}
              />
              <Geom type="interval" position="day*totalMilliters" />
            </Chart>
          </Card>
        </Card>
      </div>
    );
  }
}

export default Report;
