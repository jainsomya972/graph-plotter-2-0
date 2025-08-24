package graphplotter.logic;

import graphplotter.model.Token;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;

import static graphplotter.model.TokenType.OPERATOR;
import static graphplotter.model.TokenType.VARIABLE;

@Component
public class PostfixEvaluator {
    public Double evaluate(Deque<String> postfixExpression, Double xValue) {
        return evaluate(postfixExpression, Map.of(Token.VAR_X, xValue));
    }

    public Double evaluate(Deque<String> postfixExpression, Double xValue, Double yValue) {
        return evaluate(postfixExpression, Map.of(Token.VAR_X, xValue,
                                                  Token.VAR_Y, yValue
                ));
    }

    private Double evaluate(Deque<String> postfixExpression, Map<Token, Double> variableValues) {
        Deque<Double> evaluatedStack = new ArrayDeque<>();

        for(String tokenText: postfixExpression) {
            Token token = Token.fromHumanText(tokenText);
            if(Objects.isNull(token)) {
                evaluatedStack.addLast(Double.parseDouble(tokenText));
            }
            else if(VARIABLE.equals(token.getType())) {
                evaluatedStack.addLast(variableValues.get(token));
            }
            else if(OPERATOR.equals(token.getType()) && token.getOperandCount().equals(2)) {
                Double operand1 = evaluatedStack.pollLast();
                Double operand2 = evaluatedStack.pollLast();
                Double output = token.operate(operand2, operand1);
                evaluatedStack.addLast(output);
            }
            else if(OPERATOR.equals(token.getType()) && token.getOperandCount().equals(1)) {
                Double operand1 = evaluatedStack.pollLast();
                Double output = token.operate(operand1);
                evaluatedStack.addLast(output);
            }
        }

        if (evaluatedStack.size() == 1) {
            return evaluatedStack.peekLast();
        }

        throw new IllegalArgumentException(
                "Malformed postfix expression provided. Remaining temp stack while converting infix to postfix: " + evaluatedStack);
    }
}
