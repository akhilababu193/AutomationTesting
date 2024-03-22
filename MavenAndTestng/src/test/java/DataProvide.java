import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProvide {

    WebDriver driver;

    @Test(dataProvider = "testdata")
    public void login(String uname, String pass) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        Thread.sleep(5000);
        driver.findElement(By.name("username")).sendKeys(uname);
        driver.findElement(By.name("password")).sendKeys(pass);
        driver.findElement(By.xpath("//*[@type=\"submit\"]")).click();
        Thread.sleep(5000);
        Assert.assertTrue(driver.findElement(By.xpath("//h6")).isDisplayed());
        Thread.sleep(5000);
        driver.close();

    }


    @DataProvider()
    public Object[][] testdata() {
    Object[][] obj = new Object[2][2];

    obj[0][0] = "Admin";
    obj[0][1] = "admin123";

    obj[1][0] = "admin";
    obj[1][1] = "ad";

    return obj;
    }
}