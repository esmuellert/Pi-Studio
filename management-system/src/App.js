import "./App.css";
import SignIn from "./Signin";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect,
} from "react-router-dom";
import Dashboard from "./dashboard/Dashboard";
import PrivateRoute from "./PrivateRoute";

function App() {
  return (
    <Router>
      <Switch>
        <PrivateRoute exact path="/dashboard" component={Dashboard} />
        <Route exact path="/login" component={SignIn} />
        <Redirect path="*" to="/dashboard" />
      </Switch>
    </Router>
  );
}

export default App;
