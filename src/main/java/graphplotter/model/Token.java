package graphplotter.model;

import static graphplotter.constant.GlobalConstants.MIN_OP_PRIORITY;
import static graphplotter.model.TokenType.*;

public enum Token {

    // operators
    SIN("sin", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.sin(args[0])),
    COS("cos", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.cos(args[0])),
    TAN("tan", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.tan(args[0])),
    ASIN("asin", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.asin(args[0])),
    ACOS("acos", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.acos(args[0])),
    ATAN("atan", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.atan(args[0])),
    PLUS("+", OPERATOR, MIN_OP_PRIORITY, 2, args -> args[0] + args[1]),
    MINUS("-", OPERATOR, MIN_OP_PRIORITY, 2, args -> args[0] - args[1]),
    MULTIPLY("*", OPERATOR, MIN_OP_PRIORITY+1, 2, args -> args[0] * args[1]),
    DIVIDE("/", OPERATOR, MIN_OP_PRIORITY+1, 2, args -> args[0] / args[1]),
    POWER("^", OPERATOR, MIN_OP_PRIORITY+2, 2, args -> Math.pow(args[0], args[1])),
    LOG("log", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.log10(args[0])),
    LN("ln", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.log(args[0])),
    ANTILOG("antilog", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.pow(10, args[0])),
    ANTILN("antiln", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.exp(args[0])),
    ROOT("root", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.sqrt(args[0])),
    FLOOR("floor", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.floor(args[0])),
    CEIL("ceil", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.ceil(args[0])),
    ABS("abs", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> Math.abs(args[0])),
    FRAC("frac", OPERATOR, MIN_OP_PRIORITY+3, 1, args -> args[0] - Math.floor(args[0])),

    // variables
    VAR_X("x", VARIABLE, MIN_OP_PRIORITY-1, 0, args -> args[0]),
    VAR_Y("y", VARIABLE, MIN_OP_PRIORITY-1, 0, args -> args[0]),

    // brackets
    BRACKET_START("(", BRACKET, MIN_OP_PRIORITY-1, 0, args -> null),
    BRACKET_END(")", BRACKET, MIN_OP_PRIORITY-1, 0, args -> null);

    private final String humanText;
    private final TokenType type;
    private final Integer operatorPriority;
    private final Integer operandCount;
    private final OperateFunction operateFunction;

    Token(String humanText, TokenType type, Integer operatorPriority, Integer operandCount, OperateFunction operateFunction) {
        this.humanText = humanText;
        this.type = type;
        this.operatorPriority = operatorPriority;
        this.operandCount = operandCount;
        this.operateFunction = operateFunction;
    }

    public static Token fromHumanText(String humanText) {
        for(Token t: Token.values()) {
           if(t.humanText.equals(humanText)) {
               return t;
           }
        }
        return null;
    }

    public String getHumanText() {
        return humanText;
    }

    public TokenType getType() {
        return type;
    }
    
    public Integer getOperatorPriority() {
        return operatorPriority;
    }
    
    public Integer getOperandCount() {
        return operandCount;
    }

    public Double operate(Double...args) {
        return operateFunction.apply(args);
    }
}
