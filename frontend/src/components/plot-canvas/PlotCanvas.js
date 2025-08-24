import React, { useEffect, useRef, useState } from "react";
import {preEvaluate, evaluate} from "./helper";
import JXG from "jsxgraph";

function fetchPointsAndPlot(boardRef, postfixRef) {
  const postfixExpression = postfixRef.current;
  const board = boardRef.current;
  if (!postfixExpression || !board) return;

  const bbox = board.getBoundingBox(); // [xmin, ymax, xmax, ymin]
  const xmin = bbox[0], xmax = bbox[2];
  evaluate(postfixExpression, xmin, xmax, Math.min(0.02, (xmax-xmin)/100.0))
  .then(points => plotPoints(board, points));
}

function plotPoints(board, points) {
  // Remove previous curves
  board.objectsList
    .filter(obj => obj.elType === "curve")
    .forEach(el => {
      board.removeObject(el)
    });

  if (!points || points.length === 0) return;

  const xs = points.map(function(p) { return p[0]; });
  const ys = points.map(function(p) { return p[1]; });
  board.create("curve", [xs, ys], { strokeColor: "#283593", strokeWidth: 2 });
}

function initBoard(boardRef, postfixRef, boxRef, handleMouseUpRef) {
  console.log("Initializing board");
  const board = JXG.JSXGraph.initBoard("jxg-box", {
    boundingbox: [-10, 6, 10, -6],
    axis: true,
    showNavigation: true,
    pan: { enabled: true , needShift: false},
    zoom: { enabled: true, factor: 1.2, wheel: true , needShift: false}
  });
  boardRef.current = board;

  if(handleMouseUpRef.current) {
    boxRef.current.removeEventListener("mouseup", handleMouseUpRef.current);
  }
  const handleMouseUp = () => {
    fetchPointsAndPlot(boardRef, postfixRef);
  };
  boxRef.current.addEventListener("mouseup", handleMouseUp);
  handleMouseUpRef.current = handleMouseUp;
  return board;
}

const PlotCanvas = ({ expression}) => {
  const boxRef = useRef(null);
  const boardRef = useRef(null);
  const postfixRef = useRef(null);
  const handleMouseUpRef = useRef(null);

  useEffect(() => {
    
    const board = boardRef.current || initBoard(boardRef, postfixRef, boxRef, handleMouseUpRef);
    
    preEvaluate(expression)
    .then((postfixExpression) => {
      postfixRef.current = postfixExpression;
      
      fetchPointsAndPlot(boardRef, postfixRef);
    });

    return () => {
      JXG.JSXGraph.freeBoard(board);
      boardRef.current = null;
    };
  }, [expression]);

  return <div id="jxg-box" ref={boxRef} style={{ width: "100vw", height: "90vh" }} />;
};

export default PlotCanvas;