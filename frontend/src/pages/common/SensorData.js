import React from "react";
import axios from "axios";
import FusionCharts from "fusioncharts";
import Charts from "fusioncharts/fusioncharts.charts";
import Widgets from "fusioncharts/fusioncharts.widgets";
import ReactFC from "react-fusioncharts";
import FusionTheme from "fusioncharts/themes/fusioncharts.theme.fusion";
import "bootstrap/dist/css/bootstrap.min.css";
import "./SensorData.css";
import SensorName from "./SensorCard";

ReactFC.fcRoot(FusionCharts, Charts, Widgets, FusionTheme);

class SensorData extends React.Component {
  chartRef = null;
  state = {
    showChart: false,
    initValue: 0,
    flowRate: "",
    flowMilliLitres: "",
    totalMilliLitres: "",
    sensorName: "",
    dataSource: {
      chart: {
        caption: "Real Time Water Flow Rate",
        subCaption: "",
        xAxisName: "Local Time",
        yAxisName: "Flow Rate",
        numberPrefix: "L/min",
        refreshinterval: "2",
        slantLabels: "1",
        numdisplaysets: "10",
        labeldisplay: "rotate",
        showValues: "0",
        showRealTimeValue: "0",
        theme: "fusion"
      },
      categories: [
        {
          category: [
            {
              label: this.clientDateTime().toString()
            }
          ]
        }
      ],
      dataset: [
        {
          data: [
            {
              value: 0
            }
          ]
        }
      ]
    }
  };
  chartConfigs = {
    type: "realtimeline",
    renderAt: "container",
    width: "100%",
    height: "400",
    dataFormat: "json"
  };

  componentDidMount() {
    this.getDataFor();
  }

  startUpdatingData() {
    this.timerID = setInterval(() => {
      axios.get("http://172.20.10.9:8088/react.php").then(d => {
        let x_axis = this.clientDateTime();
        let y_axis = d.data.flowRate;

        console.log(d.data);
        this.chartRef.feedData("&label=" + x_axis + "&value=" + y_axis);
        this.setState({
          sensorName: d.data.sensorName,
          flowRate: d.data.flowRate,
          flowMilliLitres: d.data.flowMilliLitres,
          totalMilliLitres: d.data.totalMilliLitres
        });
      });
    }, 1000);
  }

  getDataFor() {
    axios
      .get("http://172.20.10.9:8088/react.php", {
        mode: "cors"
      })
      .then(d => {
        const dataSource = this.state.dataSource;
        dataSource.chart.yAxisMaxValue = 100;
        dataSource.chart.yAxisMinValue = 0;
        dataSource.dataset[0]["data"][0].value = d.data.flowRate;

        this.setState(
          {
            showChart: true,
            dataSource: dataSource,
            initValue: d.data.flowRate
          },
          () => {
            this.startUpdatingData();
          }
        );
      });
  }

  static addLeadingZero(num) {
    return num <= 9 ? "0" + num : num;
  }

  clientDateTime() {
    var date_time = new Date();
    console.log(date_time);
    var curr_hour = date_time.getHours();
    var zero_added_curr_hour = SensorData.addLeadingZero(curr_hour);
    var curr_min = date_time.getMinutes();
    var curr_sec = date_time.getSeconds();
    var curr_time = zero_added_curr_hour + ":" + curr_min + ":" + curr_sec;
    return curr_time;
  }

  getChartRef(chart) {
    this.chartRef = chart;
  }

  componentWillUnmount() {
    clearInterval(this.timerID);
  }

  render() {
    return (
      <div className="row mt-5 mt-xs-4">
        <div className="col-12 mb-3">
          <div className="card-deck custom-card-deck">
            <SensorName
              header="Sensor Name"
              alt="fireSpot"
              label="(Meter Name)"
              value={this.state.sensorName}
            />
            <SensorName
              header="Flow Rate"
              alt="fireSpot"
              label="(L/min)"
              value={this.state.flowRate}
            />
            <SensorName
              header="Current Liquid Flowing"
              alt="fireSpot"
              label="(mL/Sec)"
              value={this.state.flowMilliLitres}
            />
            <SensorName
              header="Output Liquid Quantity"
              alt="fireSpot"
              label="(mL)"
              value={this.state.totalMilliLitres}
            />
          </div>
        </div>
        <div className="col-12">
          <div className="card custom-card mb-5 mb-xs-4">
            <div className="card-body">
              {this.state.showChart ? (
                <ReactFC
                  {...this.chartConfigs}
                  dataSource={this.state.dataSource}
                  onRender={this.getChartRef.bind(this)}
                />
              ) : null}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default SensorData;
