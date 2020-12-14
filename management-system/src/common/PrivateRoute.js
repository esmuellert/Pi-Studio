// This is used to determine if a user is authenticated and
// if they are allowed to visit the page they navigated to.

// If they are: they proceed to the page
// If not: they are redirected to the login page.
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Redirect, Route } from "react-router-dom";
const PrivateRoute = ({ component: Component, ...rest }) => {
  // Add your own authentication on the below line.
  const [isAuthencated, setIsAuthencated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    let ignore = false;
    async function request() {
      await axios
        .post(`${process.env.REACT_APP_BACKEND_URL}/login`, {
          token: localStorage.getItem("token"),
        })
        .then(() => {
          if (!ignore) {
            setIsAuthencated(true);
            setIsLoading(false);
          }
        })
        .catch((error) => {
          console.log(error);
          setIsAuthencated(false);
          setIsLoading(false);
        });
    }
    request();
    return () => {
      ignore = true;
    };
  }, []);
  return (
    <Route
      {...rest}
      render={(props) => {
        if (isLoading) {
          return null;
        }
        if (isAuthencated) {
          return <Component {...props} />;
        } else {
          return (
            <Redirect
              to={{ pathname: "/login", state: { from: props.location } }}
            />
          );
        }
      }}
    />
  );
};

export default PrivateRoute;
