import React, { useState } from "react";
import background from "./images/background_image.png";
import logo from "./images/logo.png";
import { useNavigate } from "react-router-dom";


function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate();

  const pageStyle = {
    position: "relative",
    backgroundImage: `url(${background})`,
    backgroundSize: "cover",
    backgroundPosition: "center",
    backgroundRepeat: "no-repeat",
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  };

  const formContainerStyle = {
    backgroundColor: "#00000000",
    padding: 30,
    borderRadius: 8,
    textAlign: "center",
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:5000/users/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
          password,
        }),
          mode: "cors"
      });

      const data = await response.json();

      if (!response.ok) {
        setErrorMessage(data.error || "Login failed");
        return;
      }

      // Login success
      console.log("User logged in:", data.user);
      setErrorMessage("");
      navigate("/dashboard")
    } catch (error) {
      setErrorMessage("Something went wrong. Try again.");
    }
  };

  return (
    <div style={pageStyle}>
      {/* Logo top-left */}
      <div
        style={{
          position: "absolute",
          left: 16,
          top: 16,
          backgroundColor: "rgba(255, 255, 255, 0.3)",
          padding: 8,
          borderRadius: 8,
          cursor: "pointer",
        }}
      >
        <a href="/">
          <img src={logo} alt="Logo" style={{ height: 110, width: "auto" }} />
        </a>
      </div>

      {/* Login Form */}
      <div style={formContainerStyle}>
        <h2 style={{ color: "#fff", fontSize: "26px", marginBottom: "20px" }}>
          Sign In
        </h2>
        <form
          onSubmit={handleLogin}
          style={{
            display: "flex",
            flexDirection: "column",
            gap: 16,
            maxWidth: 300,
            margin: "auto",
          }}
        >
          <input
            type="text"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={{
              padding: 10,
              backgroundColor: "transparent",
              border: "1px solid #fff",
              borderRadius: 4,
              color: "#fff",
              fontSize: 16,
              outline: "none",
            }}
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={{
              padding: 10,
              backgroundColor: "transparent",
              border: "1px solid #fff",
              borderRadius: 4,
              color: "#fff",
              fontSize: 16,
              outline: "none",
            }}
          />

          {errorMessage && (
            <div style={{ color: "red", fontSize: 14 }}>{errorMessage}</div>
          )}

          <button
            type="submit"
            style={{
              padding: "10px",
              backgroundColor: "#4F46E5",
              color: "#fff",
              border: "none",
              borderRadius: 4,
              fontSize: "16px",
              fontWeight: "bold",
              cursor: "pointer",
            }}
          >
            Log In
          </button>
        </form>
      </div>
    </div>
  );
}

export default LoginPage;
