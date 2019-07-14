import React from "react";
import { Table } from "antd";
import axios from "axios";

class WaterBill extends React.Component {
  state = {
    waterBill: []
  };

  componentDidMount() {
    this.getWaterBill();
  }

  getWaterBill() {
    axios
      .get("http://localhost:8080/iot/meter/getWaterBill")
      .then(res => {
        const mm = res.data.data[0].month;
        var months = [
          "January",
          "February",
          "March",
          "April",
          "May",
          "June",
          "July",
          "August",
          "September",
          "October",
          "November",
          "December"
        ];
        const month = months[mm - 1] || "";
        console.log(month);
        if (res.data.code === 200) {
          this.setState({
            waterBill: res.data.data
          });
        }
      })

      .catch(error => {
        console.log(error);
      });
  }

  render() {
    const columns = [
      {
        title: "Meter Name",
        dataIndex: "meterName"
      },
      {
        title: "Member Name",
        dataIndex: "memberName"
      },
      {
        title: "Month",
        dataIndex: "month"
      },
      {
        title: "Amount volume (totalMilliters /mL)",
        dataIndex: "totalMilliters"
      },
      {
        title: "Amount Fee(Bath)",
        dataIndex: "fee"
      }
    ];
    const { waterBill } = this.state;

    return (
      <Table
        rowKey={record => record.memberName}
        columns={columns}
        dataSource={waterBill}
        bordered
        title={() => "Water Bill"}
        footer={() => "25Bath per unit,one unit is 1000mL"}
      />
    );
  }
}
export default WaterBill;
