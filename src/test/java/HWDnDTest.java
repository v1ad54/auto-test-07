import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ApiUtils;

import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


/**
 * Домашнее задание. Тема 7. BP и типичные проблемы
 * Задача 1: написать 2 автотеста для кейсов:
 * 1.1 Перетащить элемент в корзину с помощью Drag-and-Drop.
 * 1.2 Удалить добавленный элемент из корзины и проверить, что он там больше не отображается.
 */
public class HWDnDTest {

    /**
     * Id тестового товара
     */
    static Integer id;

    /**
     * Карточка тестового товара
     */
    SelenideElement goodsCard;

    /**
     * Кнопка корзины
     */
    SelenideElement cartButton = $x("//*[@id='open-cart-btn']");

    /**
     * Счетчик товаров в корзине
     */
    SelenideElement cartCounter = $x("//*[@id='cart-count']");

    /**
     * Карточка тестового товара в корзине
     */
    SelenideElement goodsInCart;

    /**
     * Кнопка удаления тестового товара из корзины
     */
    SelenideElement deleteGoodsFromCart;


    @BeforeEach
    void setup() {
        // Создаем тестовый товар
        id = ApiUtils.createGood(
                "Товар " + new Random().nextInt(1000, 10000),
                new Random().nextInt(10, 25)
        );

        // Обновляем локатор карточки тестового товара на витрине
        goodsCard = $x("//*[@id='card-%s']".formatted(id));
        // Обновляем локатор карточки тестового товара в корзине
        goodsInCart = $x("//*[@id='cart-item-%s']".formatted(id));
        // Обновляем локатор кнопки удаления карточки тестового товара из корзины
        deleteGoodsFromCart = goodsInCart.$x(".//*[@data-action='remove']");

        // Открываем главную страницу
        Selenide.open("http://localhost:8080");
    }

    /**
     * Тест 1.1 Перетащить элемент в корзину с помощью Drag-and-Drop.
     */
    @Test
    void hwDnDTest(){
        // Добавим товар в корзину с помощью Drag-and-Drop
//        sleep(2000);
        goodsCard.dragAndDrop(DragAndDropOptions.to(cartButton));
//        sleep(2000);

        // Проверим что счетчик на корзине увеличился
        cartCounter.should(text("1"));
    }

    /**
     * 1.2 Удалить добавленный элемент из корзины и проверить, что он там больше не отображается.
     */
    @Test
    void deleteGoodsFromCart(){
        // Добавим товар в корзину с помощью Drag-and-Drop
        goodsCard.dragAndDrop(DragAndDropOptions.to(cartButton));

        // Кликнем по кнопке "Корзина"
        cartButton.click();

        // Проверим что товар есть в корзине
        goodsInCart.should(visible);

        // Удалим товар
//        deleteGoodsFromCart.click();
        actions().moveToElement(deleteGoodsFromCart)
                .click().perform();

        // Убедимся что теперь товара нет в корзине
        goodsInCart.shouldNot(exist);
    }

    @AfterEach
    void deleteTestData() {
        // Удаление товара через Utils класс
        if (id != null) {
            ApiUtils.deleteGood(id);
        }
    }

}
