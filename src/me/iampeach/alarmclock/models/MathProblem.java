package me.iampeach.alarmclock.models;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Random;

public class MathProblem {
    private static final char[] OPERATORS = {'+', '-', '*'};

    private char[] operators = new char[3];
    private int[] operands = new int[4];
    private int answer;

    public MathProblem() {
        do {
            answer = generateProblem();
        } while (answer <= 0 || answer > 100);
    }

    public int getAnswer() {
        return answer;
    }

    private String getExpression() {
        StringBuilder result = new StringBuilder();
        result.append(operands[0]);
        for (int i = 0; i < 3; i++)
            result.append(' ').append(operators[i]).append(' ').append(operands[i + 1]);
        return result.toString();
    }

    @Override
    public String toString() {
        return getExpression().replace("*", "x");
    }

    private int generateProblem() {
        Random random = new Random();
        for (int i = 0; i < 3; i++)
            operators[i] = OPERATORS[random.nextInt(3)];
        for (int i = 0; i < 4; i++)
            operands[i] = random.nextInt(9) + 1; // 1 to 9

        try {
            return Integer.parseInt(new ScriptEngineManager().getEngineByName("JavaScript").eval(getExpression()).toString());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
