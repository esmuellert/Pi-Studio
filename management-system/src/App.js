import "./App.css";
import SignIn from "./Signin";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";
import Dashboard from "./dashboard/Dashboard";
import PrivateRoute from "./PrivateRoute"

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/login" component={SignIn} />
        <PrivateRoute exact path="/dashboard" component={Dashboard} />
        <Redirect path="*" to="/login"/>
      </Switch>
    </Router>
  );
}

export default App;
