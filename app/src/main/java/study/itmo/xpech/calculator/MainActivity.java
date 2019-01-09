package study.itmo.xpech.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;

import study.itmo.xpech.parserlib.Parser;
import study.itmo.xpech.parserlib.exceptions.ParsingException;

public class MainActivity extends AppCompatActivity {

    final static String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private TextView getCalcScreen() {
        return (TextView) findViewById(R.id.calcScreen);
    }

    protected void onSaveInstanceState(Bundle out) {
        TextView calcScreen = getCalcScreen();
        out.putString(expression, calcScreen.getText().toString());
        super.onSaveInstanceState(out);
    }

    protected void onRestoreInstanceState(Bundle in) {
        super.onRestoreInstanceState(in);

        String bundleExpression = in.getString(expression);
        TextView calcScreen = getCalcScreen();
        calcScreen.setText(bundleExpression);
    }

    public void addCharacterToScreen(View button) {
        Button butt = (Button) button;
        TextView calcScreen = getCalcScreen();
        if (calcScreen.getText().toString().equals("0")) {
            calcScreen.setText(butt.getText());
        } else {
            calcScreen.setText(String.format("%s%s", calcScreen.getText(), butt.getText()));
        }
    }

    public void eraseLastCharacter(View button) {
        TextView calcScreen = getCalcScreen();
        String expr = calcScreen.getText().toString();
        if (expr.length() <= 1) {
            calcScreen.setText("0");
        } else {
            calcScreen.setText(expr.substring(0, expr.length() - 1));
        }
    }

    public void eraseAllCharacters(View button) {
        getCalcScreen().setText("0");
    }

    public void calc(View button) {
        TextView calcScreen = getCalcScreen();
        String expr = calcScreen.getText().toString();
        Parser parser = new Parser();
        try {
            BigDecimal res = parser.eval(expr);
            calcScreen.setText(String.format("%s", res));
        } catch (ParsingException pe) {
            Toast err = Toast.makeText(MainActivity.this, String.format("Error occured while parsing: %s", pe.getMessage()), Toast.LENGTH_LONG);
            err.show();
        }
    }
}
