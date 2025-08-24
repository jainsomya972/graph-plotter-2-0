package graphplotter.manager;

import graphplotter.converter.InfixToPostfixConverter;
import graphplotter.logic.ExpressionTokenizer;
import graphplotter.logic.PostfixEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ExpressionManager {
    private final InfixToPostfixConverter infixToPostfixConverter;
    private final ExpressionTokenizer expressionTokenizer;
    private final PostfixEvaluator postfixEvaluator;

    public List<String> preEvaluate(String expression) {
        Deque<String> tokenizedExpression = expressionTokenizer.tokenizeRawExpression(expression);
        Deque<String> postfixExpression = infixToPostfixConverter.convert(tokenizedExpression);
        return new ArrayList<>(postfixExpression);
    }

    public Map<Double, Double> evaluate(List<String> postfixExpression, Double xValueFrom, Double xValueTo, Double xValueStep) {
        Deque<String> postfixDeque = new ArrayDeque<>(postfixExpression);
        Map<Double, Double> expressionOutput = new HashMap<>();
        for(double i = xValueFrom; i <= xValueTo; i += xValueStep) {
            expressionOutput.put(i, postfixEvaluator.evaluate(postfixDeque, i));
        }
        return expressionOutput;
    }
}
