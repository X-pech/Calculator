package study.itmo.xpech.parserlib;

import java.math.BigDecimal;
import java.math.RoundingMode;

import study.itmo.xpech.parserlib.exceptions.*;

public class Parser {

    private String expression;
    private int index = 0;
    private int balance = 0;
    private BigDecimal constValue;

    private enum Token {
        MUL,
        DIV,
        ADD,
        SUB,
        NEG,
        CST,
        OBR,
        CBR,
        SQRT,
        INV,
        NEU
    }

    private Token token = Token.INV;

    private void skipSpaces() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private void setToken(Token t) {
        token = t;
    }

    private void parseConst(boolean negate) throws ParsingException {
        StringBuilder constSB = new StringBuilder();
        if (negate) {
            constSB.append('-');
        }
        while (index < expression.length() && (Character.isDigit(expression.charAt(index)) || expression.charAt(index) == '.')) {
            constSB.append(expression.charAt(index));
            index++;
        }
        constValue = null;
        try {
            //constValue = parse(constSB.toString());
            // TODO: check it out
            constValue = new BigDecimal(constSB.toString());
            setToken(Token.CST);
        } catch (NumberFormatException e) {
            throw new ParsingOverflowException(expression, index);
        }
        index--;
    }


    private void parseToken() throws ParsingException {
        skipSpaces();
        if (index >= expression.length()) {
            if (token != Token.NEU)
                setToken(Token.NEU);
            return;
        }
        char ch = expression.charAt(index);

        if (ch == '*') {
            setToken(Token.MUL);
        } else if (ch == '/') {
            setToken(Token.DIV);
        } else if (ch == '+') {
            setToken(Token.ADD);
        } else if (ch == '(') {
            setToken(Token.OBR);
        } else if (ch == ')') {
            setToken(Token.CBR);
        } else if (ch == '-') {
            if (token == Token.CST || token == Token.CBR) {
                setToken(Token.SUB);
            } else {
                index++;
                skipSpaces();
                if (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                    parseConst(true);
                } else {
                    setToken(Token.NEG);
                    index--;
                }
            }
        } else if (Character.isDigit(ch)) {
            parseConst(false);
        } else if (Character.isAlphabetic(ch)) {
            StringBuilder tokenSB = new StringBuilder();
            while (index < expression.length() && Character.isLetterOrDigit(expression.charAt(index))) {
                tokenSB.append(expression.charAt(index));
                index++;
            }
            String tokenStr = tokenSB.toString();
            if (tokenStr.equals("sqrt")) {
                setToken(Token.SQRT);
            } else {
                setToken(Token.INV);
            }
            index--;
        } else {
            setToken(Token.INV);
        }
        index++;
    }

    private boolean isOperation(Token t) {
        return (t == Token.ADD || t == Token.SUB || t == Token.MUL || t == Token.DIV || t == Token.NEG || t == Token.SQRT);
    }

    private boolean isNeutral(Token t) {
        return (t == Token.NEU);
    }

    private boolean isOpeningParethesis(Token t) {
        return (t == Token.OBR);
    }

    private boolean checkLowest() {
        return (isOpeningParethesis(token) || isOperation(token) || isNeutral(token));
    }


    private BigDecimal lowestPriority() throws ParsingException {
        if (checkLowest()) {
            return binAdd();
        } else {
            throw new MissingOperationException(expression, index);
        }
    }

    private BigDecimal unary() throws ParsingException {
        parseToken();
        BigDecimal res;
        switch (token) {
            case CST:
                res = constValue;
                parseToken();
                break;
            case NEG:
                try {
                    res = unary().negate();
                } catch (NullPointerException npe) {
                    throw new MissingOperandException(expression, index);
                }
                break;
            case OBR:
                balance++;
                res = lowestPriority();
                if (token != Token.CBR) {
                    throw new MissingClosingBracketException(expression, index);
                }
                parseToken();
                break;
            case CBR:
                if (balance == 0) {
                    throw new MissingOpeningBracketException(expression, index);
                }
                balance--;
                res = null;
                break;
            case INV:
                throw new IncorrectSymbolException(expression, index);
            default:
                res = null;
        }
        return res;
    }

    private BigDecimal binMul() throws ParsingException {
        BigDecimal res = unary();
        while (true) {
            try {
                switch (token) {
                    case MUL:
                        res = res.multiply(unary());
                        break;
                    case DIV:
                        BigDecimal divisor = unary();
                        if (divisor == null) {
                            throw new NullPointerException();
                        } else if (divisor.compareTo(BigDecimal.ZERO) == 0) {
                            throw new DivisionByZeroException(expression, index);
                        }
                        res = new BigDecimal(String.format("%s.0", res.toString()));
                        res = res.divide(divisor, 32, RoundingMode.CEILING);
                        break;
                    default:
                        return res;
                }
            } catch (NullPointerException e) {
                throw new MissingOperandException(expression, index);
            }
        }
    }

    private BigDecimal binAdd() throws ParsingException {
        BigDecimal res = binMul(); //TODO
        while (true) {
            try {
                switch (token) {
                    case ADD:
                        res = res.add(unary());
                        break;
                    case SUB:
                        res = res.subtract(unary());
                    default:
                        return res;
                }
            } catch (NullPointerException e) {
                throw new MissingOperandException(expression, index);
            }

        }
    }

    public BigDecimal eval(String expression) throws ParsingException {
        index = balance = 0;
        this.expression = expression;
        token = Token.NEU;
        return lowestPriority();
    }

}
