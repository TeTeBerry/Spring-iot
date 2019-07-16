import React, { Component } from "react";

import { Table, message, Modal, Button, Divider } from "antd";
import axios from "axios";
import CollectionUpdateForm from "./UpdateMeterForm";
import { Link } from "react-router-dom";
import CreateMeter from "./CreateMeter";
import AuthService from "../auth/AuthService";

const confirm = Modal.confirm;

const headers = {
  "Content-Type": "application/x-www-form-urlencoded"
};

class Meter extends Component {
  Auth = new AuthService();
  state = {
    memberList: []
  };

  error = () => {
    message.error("Opreating Fail");
  };

  showBill = () => {
    this.props.history.push("/waterbill");
  };

  showRealTime = () => {
    this.props.history.push("/SensorData");
  };

  showEditMoal = record => {
    const { form } = this.formRef.props;
    form.memberList = record;
    console.log(record.mid);
    const formFields = form.memberList;
    console.log(formFields);
    const formData = {
      mid: record.mid,
      meterName: record.meterName,
      meterDesc: record.meterDesc,
      memberName: record.memberName,
      room: record.room,
      memberContact: record.memberContact
    };
    form.setFieldsValue(formData);
    this.setState({
      visible: true
    });
  };

  handleCancel = () => {
    this.setState({ visible: false });
  };

  updateSuccess = () => {
    message.success("Update success");
  };

  deleteSuccess = () => {
    message.success("Delete success!");
  };

  handleUpdate = () => {
    const { form } = this.formRef.props;
    const formFields = form.getFieldsValue();
    console.log(formFields.mid);
    const formData = {
      mid: formFields.mid,
      meterName: formFields.meterName,
      meterDesc: formFields.meterDesc,
      room: formFields.room,
      memberContact: formFields.memberContact,
      memberName: formFields.memberName
    };
    console.log(formData);
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      console.log("Received values of form: ", values);
      form.resetFields();
      this.setState({ visible: false });
    });
    axios
      .post("/iot/meter/update", formData)
      .then(data => {
        console.log(data.data.code);
        if (data.data.code !== 200) {
          return this.error();
        }
        this.fetchMemberList();
        this.updateSuccess();
        console.log(data);
      })
      .catch(error => {
        console.log(error);
        this.error();
      });
  };

  saveFormRef = formRef => {
    this.formRef = formRef;
  };

  deleteMember = mid => {
    const params = { mid: mid };
    console.log(params);
    axios
      .delete(
        "/iot/meter/delete",
        { params: params },
        {
          headers
        }
      )
      .then(data => {
        this.setState({
          memberList: this.state.memberList.filter(item => item.mid !== mid)
        });
        this.deleteSuccess();
        console.log(data);
      })
      .catch(error => {
        console.log(error);
        this.error();
      });
  };

  showDeleteConfirm(mid, meterName) {
    console.log(mid);
    confirm({
      title: `Are you sure delete ${meterName}?`,
      okText: "Yes",
      okType: "danger",
      cancelText: "No",
      onOk: () => {
        this.deleteMember(mid);
        console.log("OK");
      },
      onCancel() {
        console.log("Cancel");
      }
    });
  }

  fetchMemberList = () => {
    axios
      .get("/iot/meter/getMeters")
      .then(res => {
        this.setState({
          memberList: res.data.data
        });
        console.log(res.data.data);
      })

      .catch(error => {
        console.log(error);
      });
  };

  componentDidMount() {
    this.fetchMemberList();
  }

  render() {
    const columns = [
      {
        title: "Meter Name",
        dataIndex: "meterName"
      },
      {
        title: "Descriptions",
        dataIndex: "meterDesc"
      },
      {
        title: "Member Name",
        dataIndex: "memberName"
      },
      {
        title: "Room",
        dataIndex: "room"
      },
      {
        title: "Member Contact",
        dataIndex: "memberContact"
      },
      {
        title: "Action",
        key: "action",
        render: record => (
          <span>
            <Button size={"small"} onClick={() => this.showEditMoal(record)}>
              Edit
            </Button>
            <Divider type="vertical" />
            <Button
              size={"small"}
              onClick={() =>
                this.showDeleteConfirm(record.mid, record.meterName)
              }
              type="danger"
            >
              Delete
            </Button>
            <Divider type="vertical" />
            <Button type="primary" size={"small"} onClick={this.showRealTime}>
              View Data
            </Button>
            <Divider type="vertical" />
            <Link to={"/report/" + record.meterName}>View Report</Link>
            <Divider type="vertical" />
            <Button type="primary" size={"small"} onClick={this.showBill}>
              Water Bill
            </Button>
          </span>
        )
      }
    ];

    const { memberList } = this.state;
    return (
      <div>
        <CreateMeter coolName={this.fetchMemberList} />
        <br />
        <Table
          rowKey={record => record.mid}
          columns={columns}
          dataSource={memberList}
        />
        <CollectionUpdateForm
          wrappedComponentRef={this.saveFormRef}
          visible={this.state.visible}
          onCancel={this.handleCancel}
          handleSubmit={this.handleUpdate}
        />
      </div>
    );
  }
}

export default Meter;
