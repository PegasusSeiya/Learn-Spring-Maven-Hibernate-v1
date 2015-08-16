package fr.todooz.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleTest {
	private WebDriver webDriver;

	@Before
	   public void init() {
	       // création du pilote firefox
	       webDriver = new FirefoxDriver();
		   
		   //pilote Chrome
	       //webDriver = new RemoteWebDriver("http://127.0.0.1:9515", DesiredCapabilities.chrome());
		   //webDriver = new ChromeDriver();
	   }

	@After
	public void close() {
		// fermeture du navigateur
		webDriver.quit();
	}

	@Test
	public void search() throws InterruptedException {
		// naviguer vers google.fr
		webDriver.navigate().to("http://google.fr");
		// webDriver.get("http://www.google.com");

		// remplir le champs de recherche
		WebElement input = webDriver.findElement(By.name("q"));
		
		try{
			WebElement input2 = webDriver.findElement(By.name("hello"));
		}catch(NoSuchElementException e){
			
		}finally{
			input.sendKeys("selenium");

			// post du formulaire contenant l'input
			input.submit();

			// on attends le chargement de la page
		   WebElement link = new WebDriverWait(webDriver, 5).until(new ExpectedCondition<WebElement>() {
		      public WebElement apply(WebDriver input) {
		         // on doit trouver le lien vers le site
		         return webDriver.findElement(By.partialLinkText("Web Browser Automation"));
		      }
		   });

		   Assert.assertEquals("http://docs.seleniumhq.org/", link.getAttribute("href"));
		}

		
	}
	
	@Test
	public void calculStatsCumul() throws InterruptedException {
		//Utiliser FluentLenium!!
		
		// Aller à la page changer de collectivité
		
		// Se connecter si nécessaire:
		/*
		try{
			WebElement login = webDriver.findElement(By.name("login"));
		}catch(NoSuchElementException e){
			login.sendKeys("selenium");
			WebElement password = webDriver.findElement(By.name("login"));
			password.sendKeys("S6pp0RT68");
			WebElement valider = webDriver.findElement(By.name("valider"));
			valider.click();
		}finally{
		
		}
		*/
		
		//Choisir la collectivité
		
		//Choisir l'url de calcul des statistiques cumulées
		
		//Procéder à la boucle de calcul.
	}

}
