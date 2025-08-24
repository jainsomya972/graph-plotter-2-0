package graphplotter.converter;

import graphplotter.model.Token;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import static graphplotter.constant.GlobalConstants.MIN_OP_PRIORITY;
import static graphplotter.model.Token.*;
import static graphplotter.model.TokenType.VARIABLE;

@Component
public class InfixToPostfixConverter extends BaseConverter<Deque<String>, Deque<String>> {
    @Override
    public Deque<String> convert(Deque<String> infixStack) {
        Deque<String> tempStack = new ArrayDeque<>();
        Deque<String> postfixStack = new ArrayDeque<>();
        
        while(!infixStack.isEmpty()) {
            String infixElement = infixStack.pollFirst();
            // numeric constants (unidentified token)
            if (Objects.isNull(Token.fromHumanText(infixElement))) {
                postfixStack.addLast(infixElement);
            }
            // Bracket start
            else if (BRACKET_START.getHumanText().equals(infixElement)) {
                tempStack.addLast(BRACKET_START.getHumanText());
            }
            // Bracket end
            else if (infixElement == BRACKET_END.getHumanText()) {
                while (tempStack.peekLast() != BRACKET_START.getHumanText()) {
                    postfixStack.addLast(tempStack.peekLast());
                    tempStack.pollLast();
                }
                tempStack.pollLast();
            }
            // Variables
            else if (VARIABLE.equals(Token.fromHumanText(infixElement).getType())) {
                postfixStack.add(infixElement);
            }
//            else if (infixElement == VAR_X.getHumanText())
//                postfixStack.addLast(VAR_X.getHumanText());
//            else if (infixElement == VAR_Y.getHumanText())
//                postfixStack.addLast(VAR_Y.getHumanText());

            // Operators
            else if (Token.fromHumanText(infixElement).getOperatorPriority() >= MIN_OP_PRIORITY) {
                int currentOpPriority = Token.fromHumanText(infixElement).getOperatorPriority();
                while (true) { // condition is complex and needs repeated parsing, hence doing it within body

                    int tempOpPriority = MIN_OP_PRIORITY-1;
                    Token tempToken = Token.fromHumanText(tempStack.peekLast());
                    if (!Objects.isNull(tempToken)) {
                        tempOpPriority = tempToken.getOperatorPriority();
                    }

                    if (BRACKET_START.getHumanText().equals(tempStack.peekLast()) ||
                            (tempOpPriority >=MIN_OP_PRIORITY && tempOpPriority < currentOpPriority)) {
                        break;
                    }

                    postfixStack.addLast(tempStack.peekLast());
                    tempStack.pollLast();
                }
                tempStack.addLast(infixElement);
            }
//            else if (infixElement == PLUS.getHumanText() || infixElement == MINUS.getHumanText()) {
//                while (tempStack.peekLast() != BRACKET_START.getHumanText()) {
//                    postfixStack.addLast(tempStack.peekLast());
//                    tempStack.pollLast();
//                }
//                tempStack.addLast(infixElement);
//            }
//            else if (infixElement == MULTIPLY.getHumanText() || infixElement == DIVIDE.getHumanText()) {
//                while (!List.of(BRACKET_START.getHumanText(),
//                                PLUS.getHumanText(),
//                                MINUS.getHumanText())
//                        .contains(tempStack.peekLast())) {
//                    postfixStack.addLast(tempStack.peekLast());
//                    tempStack.pollLast();
//                }
//                tempStack.addLast(infixElement);
//            }
//            else if (infixElement == POWER.getHumanText()) {
//                while (!List.of(BRACKET_START.getHumanText(),
//                                PLUS.getHumanText(),
//                                MINUS.getHumanText(),
//                                MULTIPLY.getHumanText(),
//                                DIVIDE.getHumanText())
//                        .contains(tempStack.peekLast())) {
//                    postfixStack.addLast(tempStack.peekLast());
//                    tempStack.pollLast();
//                }
//                tempStack.addLast(infixElement);
//            }
//            else if (Token.fromHumanText(infixElement).getType().equals(TokenType.FUNCTION)) {
//                while (!List.of(BRACKET_START.getHumanText(),
//                                PLUS.getHumanText(),
//                                MINUS.getHumanText(),
//                                MULTIPLY.getHumanText(),
//                                DIVIDE.getHumanText(),
//                                POWER.getHumanText())
//                        .contains(tempStack.peekLast())) {
//                    postfixStack.addLast(tempStack.peekLast());
//                    tempStack.pollLast();
//                }
//                tempStack.addLast(infixElement);
//            }
        }
        if (tempStack.isEmpty()) {
            System.out.println("Resulting postfix expression: " + postfixStack);
            return postfixStack;
        }
        throw new IllegalArgumentException(
                "Malformed infix expression provided. Remaining temp stack while converting infix to postfix: " + tempStack);
    }

    @Override
    public Deque<String> backwards(Deque<String> Doubles) {
        return null;
    }
}
