import "./App.css";
import SignIn from "./common/Signin";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect,
} from "react-router-dom";
import Dashboard from "./dashboard/Dashboard";
import PrivateRoute from "./common/PrivateRoute";
import Orders from "./orders/Orders";
import { Widget } from "react-chat-widget";

function App() {
  return (
    <Router>
      <Switch>
        <PrivateRoute exact path="/dashboard" component={Dashboard} />
        <PrivateRoute exact path="/orders" component={Orders} />
        <Route exact path="/login" component={SignIn} />{" "}
        <Route path="/widget" component={Widget} />
        <Redirect path="*" to="/dashboard" />
      </Switch>
    </Router>
  );
}

export default App;
