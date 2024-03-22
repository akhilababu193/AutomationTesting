import io.github.bonigarcia.wdm.WebDriverManager;
//import javafx.scene.layout.Priority;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class OrangeHRM{
    WebDriver driver;
    @Parameters("browserName")
    @BeforeTest()
    public void Browsernames(@Optional("Chrome") String browserName){
        switch (browserName){
            case "Chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "Firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:
                System.out.println("invalid browser name");
                break;
        }
        driver.manage().window().maximize();
    }

    @AfterTest()
        public void closeBrowser(){
            driver.close();
        }

    @Parameters("url")
    @Test(priority = 1)
    public void launchUrl(String u){
        System.out.println("URL is "+ u);
        driver.get(u);
    }

    @Parameters({"uname", "pass"})
    @Test(priority=2)
    public void loginOrangeHRM(String name, String pass) throws Exception{
        Thread.sleep(5000);
        driver.findElement(By.name("username")).sendKeys(name);
        driver.findElement(By.name("password")).sendKeys(pass);
        driver.findElement(By.xpath("//*[@type='submit']")).click();
    }

    @Test(dependsOnMethods = "loginOrangeHRM", priority = 3)
    public void verifyLogin() throws InterruptedException {
        Thread.sleep(5000);
        Assert.assertTrue(driver.findElement(By.xpath("//h6")).isDisplayed());
    }

    @Test
    public void inValidCrede() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//*[@type='submit']")).click();
    }

    @Test(dependsOnMethods = "invalidCrede")
    public void verifyInvalidLogin() throws InterruptedException{
        Thread.sleep(5000);
        Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Invalid credentials']")).isDisplayed());
    }

}

