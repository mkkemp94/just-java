package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String name = getName();
        boolean hasWhippedCream = isWhippedCreamChecked();
        boolean hasChocolate = isChocolateChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        displayMessage(orderSummary);

        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Gets the name that the user entered
     * @return that name
     */
    private String getName() {
        EditText name = (EditText) findViewById(R.id.name_edit_text);
        return name.getText().toString();
    }

    /**
     * Calculates the price of the order.
     * @param addWhippedCream defines whether or not the user wants whipped cream
     * @param addChocolate defines whether or not the user wants chocolate
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    /**
     * Checks whether the whipped cream ingredient is checked
     * @return true or false
     */
    private boolean isWhippedCreamChecked() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return whippedCreamCheckBox.isChecked();
    }

    /**
     * Checks whether the chocolate ingredient is checked
     * @return true or false
     */
    private boolean isChocolateChecked() {
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return chocolateCheckBox.isChecked();
    }

    /**
     * Creates an order summary for the user
     *
     * @param price of the order
     * $return text summary
     */
    public String createOrderSummary(String name, int price, boolean whippedCream, boolean chocolate) {
        String priceMessage = getString(R.string.order_summary_name, name) + "\n" +
                getString(R.string.order_summary_whipped_cream, whippedCream) + "\n" +
                getString(R.string.order_summary_chocolate, chocolate) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_price, String.valueOf(price) + "\n" +
                getString(R.string.thank_you));
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method increments the quantity by 1
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this, "Max 100 Coffees!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method decrements the quantity by 1
     */
    public void decrement(View view) {
        if (quantity <= 1) {
            Toast.makeText(this, "Min 1 Coffee!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }



}