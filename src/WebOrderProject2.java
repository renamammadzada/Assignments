import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WebOrderProject2 {
    public static void main(String[] args) throws IOException {

        //1. Launch Chrome browser.
        System.setProperty("webdriver.chrome.driver", "/Users/renamammadzada/Documents/drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //2. Navigate to http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx

        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        String currentURL = driver.getCurrentUrl();
        String expectedURL = "http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx";

        //3. Login using username Tester and password test

        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test" + Keys.ENTER);

        //other method:
        //driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester", Keys.TAB, "test", Keys.ENTER);

        //4. Click on Order link

        driver.findElement(By.xpath("//a[.='Order']")).click();


        //5. Enter a random product quantity between 1 and 100
        int quantity = (int) ((Math.random() * 100));
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys(Keys.BACK_SPACE, String.valueOf(quantity) + Keys.ENTER);


        //6. Click on Calculate and verify that the Total value is correct.
        //   Price per unit is 100.  The discount of 8 % is applied to quantities of 10+.
        //   So for example, if the quantity is 8, the Total should be 800.
        //   If the quantity is 20, the Total should be 1840.
        //   If the quantity is 77, the Total should be 7084. And so on.
        double total = 0;
        int pricePerUnit = 100;
        double x = (quantity * pricePerUnit) * 0.08;
        if (quantity >= 10) {
            total = (quantity * pricePerUnit) - x;
        } else {
            total = quantity * pricePerUnit;
        }
        String actualValue = driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtTotal")).getAttribute("value");
        String expectedValue = String.valueOf((int)total);
        Assert.assertEquals(actualValue, expectedValue);


        List<String[]> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/TestCase.csv"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line.split(","));
        }
        int random = (int)(Math.random() * 1000);

        //6a. Generate and enter random first name and last name.
//
//        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        String firstName = "";
//
//        String lastName = "";
//        for (int i = 0; i < 10; i++) {
//            firstName = firstName + str.charAt((int) (Math.random() * str.length()));
//            lastName = lastName + str.charAt((int) (Math.random()* str.length()));
//        }
        String fullName = list.get(random)[1];
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys(fullName);

        //7. Generate and Enter random street address

//        int streetNumber = 0;
//        for (int i = 0; i < 6; i++) {
//            streetNumber = (int) Math.random() * 10;
//        }
//        String str1 = "";
//        for (int i = 0; i < 10; i++) {
//            str1 = str1 + str.charAt((int) (Math.random()) * str.length());
//        }
//
//        String streetName = "" + streetNumber + str1;
        String streetName = "13710 Park Row Drive";
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys(streetName);

        //8. Generate and Enter random city

//        String city = "";
//        for (int i = 0; i < 10; i++) {
//            city = city + str.charAt((int) (Math.random() * str.length()));
//        }
        String city = "Houston";
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys(city);

        //9. Generate and Enter random state

         String state = "Texas";
         driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys(state);

//               //10. Generate and Enter a random 5 digit zip code

        String zipCode = "77084";
         driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(zipCode);

         //11. Select the card type randomly. On each run your script should select a random type.
        List<String>list2 = Arrays.asList("ctl00_MainContent_fmwOrder_cardList_0", "ctl00_MainContent_fmwOrder_cardList_1", "ctl00_MainContent_fmwOrder_cardList_2");
        String random2 = list2.get(new Random().nextInt(list2.size()));
        driver.findElement(By.id(random2)).click();

        //12. Generate and enter the random card number:

        long visa = (long) ((Math.random() * 1000000000000000l));
        long masterCard = (long) ((Math.random() * 1000000000000000l));
        long americanExpress = (long) ((Math.random() * 100000000000000l));


        if(random2.equals("ctl00_MainContent_fmwOrder_cardList_0")){
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys("4" + visa);
        }
        else if(random2.equals("ctl00_MainContent_fmwOrder_cardList_1")){
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys("5" + masterCard);
        }
        else if(random2.equals("ctl00_MainContent_fmwOrder_cardList_2")){
            driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys("3" + americanExpress);
        }

        //13. Enter a valid expiration date (newer than the current date)
        String validExpiration = "03/25";
        driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).sendKeys(validExpiration);

        //14. Click on Process
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();

        //15. Verify that “New order has been successfully added” message appeared on the page.
//        String expectedTitle = "New order has been successfully added";
//        String actualTitle = driver.getTitle();
//
//        Assert.assertTrue(actualTitle.contains(expectedTitle));
        System.out.println(driver.getPageSource().contains("New order has been successfully added"));

        //16. Click on View All Orders link.
        driver.findElement(By.xpath("//a[.='View all orders']")).click();

        //17. The placed order details appears on the first row of the orders table.
        //Verify that the entire information contained on the row (Name, Product, Quantity, etc)
        //matches the previously entered information in previous steps.





        //18. Log out of the application.
        driver.findElement(By.id("ctl00_logout")).click();

    }
    }


