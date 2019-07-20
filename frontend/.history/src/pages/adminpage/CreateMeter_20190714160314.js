import React, { Component } from "react";
import { Button, message } from "antd";
import axios from "axios";
import CollectionCreateForm from "./CreateMeterForm";
import AuthService from "../auth/AuthService";

class CreateMeter extends Component {
  Auth = new AuthService();
  state = {
    visible: false
  };

  error = () => {
    message.error("Add Fail");
  };

  showModal = () => {
    this.setState({ visible: true });
  };

  handleCancel = () => {
    this.setState({ visible: false });
  };

  createSuccess = () => {
    message.success("Create success!");
  };

  handleCreate = () => {
    const { form } = this.formRef.props;
    const formFields = form.getFieldsValue();
    const member = {
      meterDesc: formFields.meterDesc,
      meterName: formFields.meterName,
      memberName: formFields.memberName,
      room: formFields.room,
      memberContact: formFields.memberContact
    };
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      console.log("Received values of form: ", values);
      this.setState({ visible: false });
      form.resetFields();
    });

    axios
      .post("/iot/meter/addMeter", member)
      .then(member => {
        if (member.data.code !== 200) {
          return this.error();
        }
        this.props.coolName(member);
        this.createSuccess();
      })
      .catch(error => {
        console.log(error);
        this.error();
      });
  };

  saveFormRef = formRef => {
    this.formRef = formRef;
  };

  render() {
    return (
      <div>
        <Button
          type="primary"
          onClick={this.showModal}
          style={{ marginTop: 6 }}
        >
          Create Meter
        </Button>
        <CollectionCreateForm
          wrappedComponentRef={this.saveFormRef}
          visible={this.state.visible}
          onCancel={this.handleCancel}
          handleSubmit={this.handleCreate}
        />
      </div>
    );
  }
}

export default CreateMeter;
