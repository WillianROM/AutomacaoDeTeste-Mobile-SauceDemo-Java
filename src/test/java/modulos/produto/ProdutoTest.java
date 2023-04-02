package modulos.produto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.TouchAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;

import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.touch.WaitOptions;

@DisplayName("Testes Mobile do Site SauceDemo")
public class ProdutoTest {

    private AndroidDriver app;
    
    //Elementos da Página
    String USERNAME_INPUT = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[1]/android.widget.EditText";
    String PASSWORD_INPUT = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[2]/android.widget.EditText";
    String LOGIN_BUTTON = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.widget.Button";
    String TEXT_PRODUCTS = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.view.View[4]";
    String TEXT_ERROR_LOGIN = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.view.View";

    //Dados de acesso
    String TXT_LOGIN = "standard_user";
    String TXT_PASSWORD = "secret_sauce";

    @BeforeEach
    public void beforeEach() throws MalformedURLException, URISyntaxException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:deviceName", "emulator-5554");
        desiredCapabilities.setCapability("appPackage", "com.android.chrome");
        desiredCapabilities.setCapability("appActivity", "com.google.android.apps.chrome.Main");
        desiredCapabilities.setCapability("noReset", true);
        desiredCapabilities.setCapability("password_manager_enabled", false);


        URI remoteUrl = new URI("http://127.0.0.1:4723/wd/hub");

        this.app = new AndroidDriver(remoteUrl.toURL(), desiredCapabilities);

        app.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


    }



    @DisplayName("Acessar o site com sucesso")
    @Test
    public void testValidarAcessoAoSiteComSucesso() {
        // Navegar até a página inicial do site
        this.app.get("https://www.saucedemo.com/");

         // Preenchendo os campos de usuário e senha
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).sendKeys(TXT_LOGIN);

        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).sendKeys(TXT_PASSWORD);

        // Clicando no botão de login
        this.app.findElement(AppiumBy.xpath(this.LOGIN_BUTTON)).click();

        // Encontre o elemento pelo xpath para validação
        WebElement element = this.app.findElement(AppiumBy.xpath(this.TEXT_PRODUCTS));

        // Obtenha o texto do elemento
        String element_text = element.getText();

        // Verificar se o login foi bem-sucedido
        assertEquals("Products", element_text);

    }


    @DisplayName("Acessar o site com senha inválida")
    @Test
    public void testTentarAcessarOSiteComSenhaInvalida() {
        // Navegar até a página inicial do site
        this.app.get("https://www.saucedemo.com/");

        // Preenchendo os campos de usuário e senha
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).sendKeys(TXT_LOGIN);

        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).sendKeys("senha_invalida");

        // Clicando no botão de login
        this.app.findElement(AppiumBy.xpath(this.LOGIN_BUTTON)).click();

        // Encontre o elemento pelo xpath para validação
        WebElement element = this.app.findElement(AppiumBy.xpath(this.TEXT_ERROR_LOGIN));

        // Obtenha o texto do elemento
        String element_text = element.getText();

         // Verificar se o login falhou e se aparece a mensagem de erro correta
        assertEquals("Epic sadface: Username and password do not match any user in this service", element_text);

    }


    @DisplayName("Acesso com usuário em branco")
    @Test
    public void testTentarAcessarOSiteComUsuarioEmBranco() {
        // Navegar até a página inicial do site
        this.app.get("https://www.saucedemo.com/");

        // Preenchendo os campos de usuário e senha
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).clear();
        //this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).sendKeys(TXT_LOGIN);

        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).sendKeys(TXT_PASSWORD);

        // Clicando no botão de login
        this.app.findElement(AppiumBy.xpath(this.LOGIN_BUTTON)).click();

        // Encontre o elemento pelo xpath para validação
        WebElement element = this.app.findElement(AppiumBy.xpath(this.TEXT_ERROR_LOGIN));

        // Obtenha o texto do elemento
        String element_text = element.getText();

         // Verificar se o login falhou e se aparece a mensagem de erro correta
        assertEquals("Epic sadface: Username is required", element_text);

    }


    @DisplayName("Acesso com senha em branco")
    @Test
    public void testTentarAcessarOSiteComSenhaEmBranco() {
        // Navegar até a página inicial do site
        this.app.get("https://www.saucedemo.com/");

        // Preenchendo os campos de usuário e senha
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).sendKeys(TXT_LOGIN);

        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).clear();
        //this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).sendKeys(TXT_PASSWORD);

        // Clicando no botão de login
        this.app.findElement(AppiumBy.xpath(this.LOGIN_BUTTON)).click();

        // Encontre o elemento pelo xpath para validação
        WebElement element = this.app.findElement(AppiumBy.xpath(this.TEXT_ERROR_LOGIN));

        // Obtenha o texto do elemento
        String element_text = element.getText();

         // Verificar se o login falhou e se aparece a mensagem de erro correta
        assertEquals("Epic sadface: Password is required", element_text);

    }

    @DisplayName("Adicionar produto ao carrinho e validar valor")
    @Test
    public void testAdicionarProdutoAoCarrinhoEValidarValor(){
        // Navegar até a página inicial do site
        this.app.get("https://www.saucedemo.com/");

        // Preenchendo os campos de usuário e senha
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).sendKeys(TXT_LOGIN);

        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).sendKeys(TXT_PASSWORD);

        // Clicando no botão de login
        this.app.findElement(AppiumBy.xpath(this.LOGIN_BUTTON)).click();

        // Arrastar a tela para baixo
        Dimension size = app.manage().window().getSize();
        TouchAction touchAction = new TouchAction(app);

        int startX;
        int startY;
        int endY;

        startX = size.width / 2;
        startY = (int) (size.height * 0.8);
        endY = (int) (size.height * 0.2);

        touchAction.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();

        //Clicar no  botão "Add to cart" do Backpack
        app.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.widget.Button")).click();

        // Arrastar a tela para cima
        startX = size.width / 2;
        startY = (int) (size.height * 0.8);
        endY = (int) (size.height * 0.2);

        touchAction.press(PointOption.point(startX, endY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(startX, startY))
                .release()
                .perform();

        //Clique no shopping_cart
        this.app.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.view.View[3]")).click();


        // Validar o valor
        String valor = this.app.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[4]/android.view.View[3]")).getText();
        assertEquals("$29.99", valor);

    }


    @DisplayName("Validar texto de erro na página de checkout")
    @Test
    public void testValidarTextoDeErroNaPaginaDeCheckout() {
        // Navegar até a página inicial do site
        this.app.get("https://www.saucedemo.com/");

        // Preenchendo os campos de usuário e senha
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.USERNAME_INPUT)).sendKeys(TXT_LOGIN);

        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).clear();
        this.app.findElement(AppiumBy.xpath(this.PASSWORD_INPUT)).sendKeys(TXT_PASSWORD);

        // Clicando no botão de login
        this.app.findElement(AppiumBy.xpath(this.LOGIN_BUTTON)).click();

        // Aguardar a página que tem os produtos
        this.app.findElement(AppiumBy.xpath(this.TEXT_PRODUCTS));

        //Clique no shopping_cart
        this.app.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.view.View[3]")).click();

        // Clicar no botão Checkout na página do carrinho
        this.app.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[2]/android.widget.Button[2]")).click();

        // Preencher informações do checkout
        //First-Name
        this.app.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View/android.view.View[1]/android.widget.EditText"))
                .sendKeys("Maria");

        //Last-Name
        this.app.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View/android.view.View[2]/android.widget.EditText"))
                .sendKeys("Silva");

        //Clicar no botão Continue
        this.app.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View/android.view.View[5]/android.widget.Button[2]"))
                .click();

        // Encontre o elemento pelo xpath para validação
        WebElement element = this.app.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View/android.view.View[4]/android.view.View/android.view.View"));

        // Obtenha o texto do elemento
        String element_text = element.getText();

        // Verificar se o login falhou e se aparece a mensagem de erro correta
        assertEquals("Error: Postal Code is required", element_text);

    }


    public void scrollToElement(String elementXpath) {
        boolean isElementPresent = false;
        while (!isElementPresent) {
            try {
                app.findElement(AppiumBy.xpath(elementXpath));
                isElementPresent = true;

            } catch (NoSuchElementException e) {
                Dimension size = app.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.8);
                int endY = (int) (size.height * 0.2);
                TouchAction touchAction = new TouchAction(app);
                touchAction.press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
            }
        }
    }


}