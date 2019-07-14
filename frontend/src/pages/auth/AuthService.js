export default class AuthService {
  isLoggedIn = () => {
    // Checks if there is a saved token and it's still valid
    const token = this.getToken(); // Getting token from localstorage
    return !!token; // handwaiving here
  };

  setUserName = userName => {
    localStorage.setItem("currentUser", userName);
  };

  getUserName = () => {
    return localStorage.getItem("currentUser");
  };

  setToken = idToken => {
    //Saves user token to localStorage
    localStorage.setItem("id_token", idToken);
  };

  getToken = () => {
    // Retrieves the user token from localStorage
    return localStorage.getItem("id_token");
    // return localStorage.clear();
  };

  logout = () => {
    // Clear user token and profile data from localStorage
    return localStorage.removeItem("id_token");
  };
}
