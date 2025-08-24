package graphplotter.logic;

import graphplotter.model.Token;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

import static graphplotter.model.Token.BRACKET_END;
import static graphplotter.model.Token.BRACKET_START;

@Component
public class ExpressionTokenizer {
    public Deque<String> tokenizeRawExpression(String expression) {
        System.out.println("Tokenizing expression: " + expression);
        Deque<String> tokenizedExpression = new ArrayDeque<>();

        tokenizedExpression.addLast(BRACKET_START.getHumanText());

        int i = 0;
        while(i<expression.length()) {
            boolean matched = false;
            for (Token token : Token.values()) {
                if (expression.substring(i).startsWith(token.getHumanText())) {
                    tokenizedExpression.addLast(token.getHumanText());
                    matched = true;
                    i += token.getHumanText().length();
                    break;
                }
            }
            if (!matched) {
                // substring did not match with any predefined token, meaning its a constant or some invalid character.
                StringBuilder number = new StringBuilder();
                while(i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                tokenizedExpression.addLast(number.toString());
            }
        }

        tokenizedExpression.addLast(BRACKET_END.getHumanText());
        System.out.println("Tokenized expression is: " + tokenizedExpression);
        return tokenizedExpression;
    }
}
