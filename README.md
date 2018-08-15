# pricebasket
BJSS Coding exercise

There is a PriceBasket bash script (you may have to set this to executable) which you should be able to run as follows - 

`./PriceBasket Milk Soup Bread Soup Apples
Subtotal: £4.40
Apples 10% off: -10p
Buy 2 of Soup get 1 Bread at 50% off: -40p
Total: £3.90`

Or you can use the java executable jar 

`java -jar build/libs/pricebasket-1.0.0.jar Milk Milk
Subtotal: £2.60
(no offers available)
Total: £2.60`

I have currently set up products Milk, Bread, Soup and Apples. These appear in the file src/main/resources/prices.properties, where you can add or change the priceof existing products.

After changing this field rebuild by running `gradle build`.

If you specify an unlisted product then the application produces no output currently - errors can can found in pricebasket.log file.

**I strongly recommend Java 1.8 (as this is the only version I have tested with!)**