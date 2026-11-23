import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;


/**
 * Класс тестирования Drag-and-Drop
 */
public class DnDTest {

    SelenideElement firstCard = $x("//*[@id='card-50']");
    SelenideElement cartButton = $x("//*[@id='open-cart-btn']");

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @Test
    void simpleDnD() {
        sleep(1000);
        firstCard.dragAndDrop(DragAndDropOptions.to(cartButton));
        sleep(2000);
    }

    @Test
    void anotherDnD() {
        actions().moveToElement(firstCard)
                .clickAndHold()
                .pause(1000)
                .moveToElement(cartButton)
                .pause(Duration.ofSeconds(1))
                .release()
                .pause(Duration.ofSeconds(3))
                .perform();
    }

    @Test
    void slowDnD() {
        int firstCardX = firstCard.getCoordinates().inViewPort().getX() + firstCard.getSize().getWidth()/2;
        int firstCardY = firstCard.getCoordinates().inViewPort().getY() + firstCard.getSize().getHeight()/2;

        int cartButtonX = cartButton.getCoordinates().inViewPort().getX() + cartButton.getSize().getWidth()/2;
        int cartButtonY = cartButton.getCoordinates().inViewPort().getY() + cartButton.getSize().getHeight()/2;

        int diffX = cartButtonX - firstCardX;
        int diffY = cartButtonY - firstCardY;

        sleep(1000);
        Actions dndActions = actions().moveToElement(firstCard).clickAndHold();
        for(int i =0; i < 50; i++) {
            dndActions = dndActions.moveByOffset(diffX/50, diffY/50);
        }
        dndActions.release().perform();
        sleep(2000);
    }
}
