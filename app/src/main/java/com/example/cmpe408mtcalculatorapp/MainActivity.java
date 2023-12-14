package com.example.cmpe408mtcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static int MAX_ALLOWED_OPERATION_LENGTH = 4;

    private TextView operationsTv;
    private TextView resultTv;
    private Button modBtn, clearAllBtn, clearBtn, deleteBtn,
            oneOverXBtn, squareBtn, squareRootBtn, divisionBtn,
            sevenBtn, eightBtn, nineBtn, multiplyBtn,
            fourBtn, fiveBtn, sixBtn, subtractionBtn,
            oneBtn, twoBtn, threeBtn, additionBtn,
            plusMinusBtn, zeroBtn, decimalBtn, equalsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operationsTv = findViewById(R.id.operations);
        resultTv = findViewById(R.id.result);

        assignButtonIds(modBtn, R.id.modBtn);
        assignButtonIds(clearAllBtn, R.id.clearAllBtn);
        assignButtonIds(clearBtn, R.id.clearBtn);
        assignButtonIds(deleteBtn, R.id.deleteBtn);
        assignButtonIds(oneOverXBtn, R.id.oneOverXBtn);
        assignButtonIds(squareBtn, R.id.squareBtn);
        assignButtonIds(squareRootBtn, R.id.squareRootBtn);
        assignButtonIds(divisionBtn, R.id.divisionBtn);
        assignButtonIds(sevenBtn, R.id.sevenBtn);
        assignButtonIds(eightBtn, R.id.eightBtn);
        assignButtonIds(nineBtn, R.id.nineBtn);
        assignButtonIds(multiplyBtn, R.id.multiplyBtn);
        assignButtonIds(fourBtn, R.id.fourBtn);
        assignButtonIds(fiveBtn, R.id.fiveBtn);
        assignButtonIds(sixBtn, R.id.sixBtn);
        assignButtonIds(subtractionBtn, R.id.subtractionBtn);
        assignButtonIds(oneBtn, R.id.oneBtn);
        assignButtonIds(twoBtn, R.id.twoBtn);
        assignButtonIds(threeBtn, R.id.threeBtn);
        assignButtonIds(additionBtn, R.id.additionBtn);
        assignButtonIds(plusMinusBtn, R.id.plusMinusBtn);
        assignButtonIds(zeroBtn, R.id.zeroBtn);
        assignButtonIds(decimalBtn, R.id.decimalBtn);
        assignButtonIds(equalsBtn, R.id.equalsBtn);
    }

    void assignButtonIds(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    void checkIfOperationIsEmpty(String operations) {
        if (operations.isEmpty()) {
            return;
        }
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        // Concat the operations in textview
        String operations = operationsTv.getText().toString();

        if (buttonText.equals("C") || buttonText.equals("CE")) {
            operationsTv.setText("");
            resultTv.setText("");
            return;
        }

        if (buttonText.equals("=")) {
            operationsTv.setText(resultTv.getText());
            return;
        }

        if (buttonText.equals("D")) {
            if (!operations.isEmpty()) {
                operations = operations.substring(0, operations.length() - 1);
            }
        } else if (buttonText.equals("1/x")) {
            // Handle 1/x button click
            operations = getResult(operations, "1/x") ;
        } else if (buttonText.equals("x²")) {
            // Handle x^2 button click
            operations = getResult(operations, "x²" );
        } else if (buttonText.equals("√x")) {
            // Handle √x button click
            operations = getResult(operations,"√x");

        } else {
            operationsTv.setText("");
            operations += buttonText;
        }

        operationsTv.setText(operations);

        String finalResult = getResult(operations, buttonText);

        if (!finalResult.equals("something went wrong")) {
            resultTv.setText(finalResult);
        }
    }

    String getResult(String data, String button) {
        try {
            String result;
            if (button.equals("1/x")) {
                // Handle 1/x operation
                result = String.valueOf(1 / Double.parseDouble(data));
            }  else if (button.equals("x²")) {
                // Handle 1/x operation
                result = String.valueOf(Double.parseDouble(data) * Double.parseDouble(data));
            }
            else if (button.equals("√x")){
                result = String.valueOf( Math.pow(Double.parseDouble(data) ,0.5)) ;
            }
            else {
                Context context = Context.enter();
                context.setOptimizationLevel(-1);
                Scriptable scriptable = context.initStandardObjects();
                result = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
                if (result.endsWith(".0")) {
                    result = result.replace(".0", "");
                }
            }
            return result;
        } catch (Exception e) {
            return "something went wrong";
        }
    }
}