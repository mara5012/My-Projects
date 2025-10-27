import React, { useEffect, useState } from "react";
import leftCurtainImg from "./left_curtain.png";  // adjust path as needed
import rightCurtainImg from "./right_curtain.png";
import "./BookCurtain.css";

const BookCurtain = ({ onFinish }) => {
  const [visible, setVisible] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setVisible(false);
      if (onFinish) onFinish();
    }, 6000); // curtains open in 3 seconds

    return () => clearTimeout(timer);
  }, [onFinish]);

  if (!visible) return null;

  return (
    <div className="curtain-container">
      <div
        className="curtain left-curtain"
        style={{ backgroundImage: `url(${leftCurtainImg})` }}
      />
      <div
        className="curtain right-curtain"
        style={{ backgroundImage: `url(${rightCurtainImg})` }}
      />
    </div>
  );
};

export default BookCurtain;
