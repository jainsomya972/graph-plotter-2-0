package org.example;

import graphplotter.converter.InfixToPostfixConverter;
import graphplotter.logic.ExpressionTokenizer;
import graphplotter.logic.PostfixEvaluator;

import java.util.Deque;

public class Main {
    public static void main(String[] args) {
        ExpressionTokenizer tokenizer = new ExpressionTokenizer();
        Deque<String> infixExpression = tokenizer.tokenizeRawExpression("(sin(x))^2+(cos(x))^2");

        InfixToPostfixConverter inToPostConverter = new InfixToPostfixConverter();
        Deque<String> postfixExpression = inToPostConverter.convert(infixExpression);

        PostfixEvaluator evaluator = new PostfixEvaluator();
        Double result = evaluator.evaluate(postfixExpression, 5d);
    }
}