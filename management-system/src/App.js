import "./App.css";
import SignIn from "./Signin";
import { BrowserRouter as Router, Route } from "react-router-dom";
import Dashboard from "./dashboard/Dashboard"
function App() {
  return (
    <Router>
      <div className="App">
        <Dashboard />
      </div>
    </Router>
  );
}

export default App;
