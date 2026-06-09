package com.example.daily_new.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.daily_new.R;

import java.util.Random;

public class GameFragment extends Fragment {
    private EditText inputExpression;
    private Button checkButton;
    private TextView resultText;
    private int[] randomNumbers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        inputExpression = view.findViewById(R.id.inputExpression);
        checkButton = view.findViewById(R.id.checkButton);
        resultText = view.findViewById(R.id.resultText);

        randomNumbers = generateRandomNumbers();
        displayNumbers();

        checkButton.setOnClickListener(v -> checkExpression());

        return view;
    }

    // 生成4个随机数字
    private int[] generateRandomNumbers() {
        Random random = new Random();
        int[] numbers = new int[4];
        for (int i = 0; i < 4; i++) {
            numbers[i] = random.nextInt(13) + 1; // 1到13之间
        }
        return numbers;
    }

    // 显示生成的随机数字
    private void displayNumbers() {
        StringBuilder builder = new StringBuilder("随机数: ");
        for (int number : randomNumbers) {
            builder.append(number).append(" ");
        }
        resultText.setText(builder.toString());
    }

    // 检查用户输入的表达式是否等于24
    private void checkExpression() {
        String expression = inputExpression.getText().toString();
        try {
            double result = evaluateExpression(expression);
            if (result == 24) {
                resultText.setText("结果: 24, 恭喜！");
            } else {
                resultText.setText("结果: " + result + ", 不相等于24！");
            }
        } catch (Exception e) {
            resultText.setText("无效的表达式，请重试.");
        }
    }

    // 计算表达式
    private double evaluateExpression(String expression) {
        // 用 JavaScript 引擎计算表达式
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9')) { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }
}