import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class HiddenTest {

    @BeforeEach
    void setup(){
//        open();
//        sleep(30000);
//        Configuration.pageLoadTimeout = 5000; // Таймаут загрузки страницы
        open("http://localhost:8080");
    }

    @Test
    void hiddenTest() {
        SelenideElement addToCartButton = $x("//*[@data-action='add-to-cart']");
        addToCartButton.click();
        addToCartButton.click();
        addToCartButton.click();

        SelenideElement notificationContainer = $x("(//*[@datatest='notification-container'])[1]");
        SelenideElement secondNotificationContainer = $x("(//*[@datatest='notification-container'])[2]");
        SelenideElement thirdNotificationContainer = $x("(//*[@datatest='notification-container'])[3]");

        notificationContainer.shouldNot(visible);
        notificationContainer.should(exist);
        notificationContainer.$x("./..").should(visible);

        secondNotificationContainer.shouldNot(visible);
        secondNotificationContainer.should(exist);
        secondNotificationContainer.$x("./..").should(visible);

        thirdNotificationContainer.shouldNot(visible);
        thirdNotificationContainer.should(exist);
        thirdNotificationContainer.$x("./..").should(visible);

    }

}
