package org.springframework.samples.petclinic.owner;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(DataProviderRunner.class)
public class SeleniumOwnerTest {

    private static final String PAGE_URL = "http://localhost:8080/owners/new/";

    private WebDriver driver;

    @DataProvider
    public static Object[][] AddOwner() {
        return new String[][]{
            new String[]{"Jan", "Iksiński", "Topolowa 3", "Gdańsk", "123456789"},
            new String[]{"Adam", "Nowak", "Orzechowa 9", "Sopot", "987654321"},
            new String[]{"Artur", "Mlecz", "Miodowa 4", "Gdynia", "224876094"},
            new String[]{"Maria", "Kowalska", "Tenisowa 55", "Gdynia", "345123678"},
            new String[]{"Anna", "Rydz", "Grzybowa 8", "Gdańsk", "333888975"},
            new String[]{"Janina", "Mróz", "Zimowa 2", "Sopot", "345765222"},
        };
    }

    @Before
    public void setUp() {
//        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get(PAGE_URL);

    }

    @Test
    @UseDataProvider("AddOwner")
    public void dataToAddOwner(String firstName, String lastName, String address, String city, String telephone) {
        WebElement firstNameOwner = driver.findElement(By.id("firstName"));
        firstNameOwner.sendKeys(firstName);

        WebElement lastNameOwner = driver.findElement(By.id("lastName"));
        lastNameOwner.sendKeys(lastName);

        WebElement addressOwner = driver.findElement(By.id("address"));
        addressOwner.sendKeys(address);

        WebElement cityOwner = driver.findElement(By.id("city"));
        cityOwner.sendKeys(city);

        WebElement telephoneOwner = driver.findElement(By.id("telephone"));
        telephoneOwner.sendKeys(telephone);

        if (!driver.findElement(By.xpath("//button[@class='btn btn-default']")).isSelected()) {
            driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
        }

        driver.get(driver.getCurrentUrl().concat("/pets/new"));
        WebElement addPet = driver.findElement(By.xpath("//button[@class='btn btn-default']"));
        assertEquals("Add Pet", addPet.getText());


        driver.get(PAGE_URL.concat(String.valueOf(1L).concat("/pets/new")));
        WebElement addPetNotExsistsOwner = driver.findElement(By.xpath("//h2[contains(text(),'Something happened...')]"));
        assertEquals("Something happened...", addPetNotExsistsOwner.getText());

        driver.get("http://localhost:8080/owners/find");
        WebElement findOwner = driver.findElement(By.xpath("//input[@id='lastName']"));
        findOwner.sendKeys(lastName);

        if (!driver.findElement(By.xpath("//button[@type='submit']")).isSelected()) {
            driver.findElement(By.xpath("//button[@type='submit']")).click();

        }

        WebElement ownersTable = driver.findElement(By.xpath("//table[@id='vets']"));
        assertEquals(ownersTable.getText().contains(lastName), true);
    }

        @After
        public void tearDown () {
            driver.close();
        }
    }



