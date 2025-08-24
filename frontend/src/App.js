import logo from './logo.svg';
import './App.css';
import PlotCanvas from './components/plot-canvas/PlotCanvas';
import React, { useState } from "react";

function App() {
  const [inputExpr, setInputExpr] = useState("sin(x^2)"); // textbox value
  const [expr, setExpr] = useState("sin(x^2)");           // value passed to PlotCanvas

  const handlePlot = () => {
    setExpr(inputExpr); // commit the input only when "Plot" is clicked
  };

  return (
    <div>
      <h2>Curve Plotter</h2>
      <input
        type="text"
        value={inputExpr}
        onChange={(e) => setInputExpr(e.target.value)}
        placeholder="Enter expression e.g. sin(x)"
      />
      <button onClick={handlePlot}>Plot</button>

      {/* Pass the committed expression */}
      <PlotCanvas expression={expr} drawType="curve"/>
    </div>
  );
}

export default App;
