import React, { Component } from "react";
import { Layout, Menu, Dropdown, Icon, message } from "antd";
import "./App.css";
import AuthService from "./pages/auth/AuthService";
import { Radio } from "antd";
import ChangePw from "./pages/adminpage/ChangePW";
import { Switch, Route, Link, withRouter } from "react-router-dom";
import Meter from "./pages/adminpage/Meter";
import Report from "./pages/common/Report";
import WaterBill from "./pages/common/WaterBill";
import Member from "./pages/memberpage/Member";
import NormalLoginForm from "./pages/auth/Login";
import SensorData from "./pages/common/SensorData";

const menu = (
  <Menu>
    <Menu.Item>
      <a target="_self" href="/changepw">
        Change password
      </a>
    </Menu.Item>
  </Menu>
);

const { Header, Content, Footer } = Layout;

const Auth = new AuthService();

const MenuItem = withRouter(({ history }) => {
  const isAdmin = (localStorage.getItem("currentUser") || "") === "admin";
  return (
    <Menu
      theme="dark"
      mode="horizontal"
      defaultSelectedKeys={["1"]}
      selectedKeys={[history.location.pathname]}
      style={{ lineHeight: "64px" }}
    >
      {isAdmin ? (
        <Menu.Item key="1">
          <Icon type="user" />
          <span>Admin</span>
          <Link to="/admin" />
        </Menu.Item>
      ) : (
        <Menu.Item key="2">
          <Icon type="user" />
          <span>Member</span>
          <Link to="/member" />
        </Menu.Item>
      )}
    </Menu>
  );
});

class App extends Component {
  state = {
    isAdmin: (localStorage.getItem("currentUser") || "") === "admin"
  };

  Success = () => {
    message.success("You are logged out from the system");
  };

  _handleLogout = () => {
    Auth.logout();
    this.props.history.push("/login");
    this.Success();
  };

  componentWillMount() {
    if (!Auth.isLoggedIn()) {
      this.props.history.push("/login");
    }
  }

  render() {
    console.log("Rendering Appjs!");
    const isAdmin = (localStorage.getItem("currentUser") || "") === "admin";
    return (
      <div>
        <Layout className="layout">
          {this.props.location.pathname === "/login" || (
            <Header style={{ position: "fixed", zIndex: 1, width: "100%" }}>
              <div className="logo">
                <p>IoT Water System</p>
              </div>
              <div className="logout">
                <Radio.Button value="small" onClick={this._handleLogout}>
                  LOGOUT
                  <Link to="/login" />
                </Radio.Button>
              </div>
              <div className="welcome">
                <span>
                  <h5>
                    Welcome!
                    {isAdmin ? (
                      <Dropdown overlay={menu}>
                        <a className="ant-dropdown-link" href="/changepw">
                          {localStorage.getItem("currentUser")}
                          <Icon type="down" />
                        </a>
                      </Dropdown>
                    ) : (
                      <b>{localStorage.getItem("currentUser")}</b>
                    )}
                  </h5>
                </span>
              </div>

              <MenuItem />
            </Header>
          )}

          <Content style={{ padding: "50px 50px" }}>
            <div style={{ background: "#fff", padding: 24, minHeight: 650 }}>
              <Switch>
                <Route path="/changepw" component={ChangePw} />
                <Route path="/admin" component={Meter} />
                <Route path="/report" component={Report} />
                <Route path="/waterbill" component={WaterBill} />
                <Route path="/member" component={Member} />
                <Route path="/SensorData" component={SensorData} />
              </Switch>
            </div>
          </Content>

          <Footer style={{ textAlign: "center" }}>
            IoT Water System©2019 Created by ZiyuChen&&ZengyuLi
          </Footer>
          <Route path="/login" component={NormalLoginForm} />
        </Layout>
      </div>
    );
  }
}

export default withRouter(App);
