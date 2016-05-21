package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayPrice(12 );
    }



    int base_price = 0;
    /**
     * This method is called when the order button is clicked.
     */
    int numberOfCoffees =0 ;
    public void submitOrder(View view) {
        //int price = calculatePrice();
       /*displayPrice(price);
        String priceMessage = "Total : "+calculatePrice();
        priceMessage += "\n"+"Thank You !";
        displayMessage(priceMessage);
        //displayMessage("Thank You!");
        //displayPrice(numberOfCoffees*5);*/
        CheckBox cb = (CheckBox) findViewById(R.id.chocolate_topping);
        boolean hasChocolateCream = cb.isChecked();

        CheckBox wc = (CheckBox) findViewById(R.id.whipped_cream_topping);
        boolean hasWippedCream = wc.isChecked();

        //Edit text
        EditText namefield = (EditText)findViewById(R.id.name_field);
        String name_of_customer = namefield.getText().toString();


        String Message =createOrderSummary(numberOfCoffees,hasWippedCream,hasChocolateCream,name_of_customer);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just jave order for "+name_of_customer);
        intent.putExtra(Intent.EXTRA_TEXT,Message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(Message);
    }

    /**
     *
     *  @param numberOfCoffees total number of coffee
     *  @param hasWippedCream boolean value
     *  @param name customer name
     *  @return order summary
     *
     */
    public String createOrderSummary(int numberOfCoffees,boolean hasWippedCream,boolean hasChocolateCream,String name){


        String message = " Name : "+name;
        message =   message + "\n"+" Add whipped cream ?"+hasWippedCream;
        message =   message + "\n"+" Add Chocolate cream ?"+hasChocolateCream;
        message =   message + "\n"+" Quantity: "+numberOfCoffees;
        message =   message + "\n"+" Total : "+calculatePrice(hasChocolateCream,hasWippedCream);
        message =   message+  "\n "+getString(R.string.thank_you);
        Log.v("MainActivity","checked state "+hasWippedCream);
        Log.v("MainActivity","checked state "+hasChocolateCream);
        return message;
    }
    public int calculatePrice(boolean hasChocolateCream,boolean hasWippedCream){
        base_price =5;
        if(hasChocolateCream){
            base_price = base_price +2;
        }
        Log.v("calculateprice","is 1:"+base_price);
        if(hasWippedCream){
            base_price += 1;
        }
        Log.v("calculateprice","is 2 :"+base_price);
        base_price =base_price *  numberOfCoffees;
        Log.v("calculateprice","is 3 :"+base_price);
    return base_price;
    }
    public void increment (View view){
      if (numberOfCoffees==100)
        {
            Toast.makeText(this,"you cannot have more than 100 coffee ",Toast.LENGTH_SHORT).show();
            return;
        }
        ++numberOfCoffees;
        display(numberOfCoffees);

    }
    public void decrement (View view){

        if (numberOfCoffees == 1)
        {
            Toast.makeText(this,"you cannot have less than 1 coffee ",Toast.LENGTH_SHORT).show();
            return;
        }
        --numberOfCoffees;
        display(numberOfCoffees);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity);
        quantityTextView.setText("" + number);


    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary);
        orderSummaryTextView.setText(message);
    }

}
