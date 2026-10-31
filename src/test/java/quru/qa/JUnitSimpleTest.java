package quru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.CollectionCondition.containExactTextsCaseSensitive;

public class JUnitSimpleTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;
    }

    @ValueSource(strings = {"Git", "JavaScript"})
    @ParameterizedTest(name = "Поиск книги по запросу: {0}")
    @Tag("UI_TEST")
    void searchBookTest(String bookTitle) {
        open("https://demoqa.com/books");

        // 1. Вбиваем название в поле поиска
        $("#searchBox").setValue(bookTitle);

        // 2. Проверяем, что в таблице результатов отображается книга с нужным названием
        $("tbody").shouldHave(text(bookTitle)).shouldBe(visible);
    }

    @CsvSource({
            "Alex, alex@alex.com, Yekaterinburg",
            "Bab, bab@bab.com, Moscow",
            "Yanix, yanni@yan.com, Yalta"
    })
    @CsvFileSource(resources = "/users_100_data.csv", numLinesToSkip = 1)
    @ParameterizedTest(name = "Заполнение формы Text Box для пользователя {0}")
    @Tag("UI_TEST")
    void searchTest(
            String fullName,
            String email,
            String city,
            String address
    ){
        open("https://demoqa.com/text-box");
        // 1. Вбиваем название в поле поиска
        $("#userName").setValue(fullName);
        $("#userEmail").setValue(email);
        $("#currentAddress").setValue(city);
        $("#permanentAddress").setValue(address);
        $("#submit").click();

        $("#output")
                .shouldHave(text(fullName))
                .shouldHave(text(email))
                .shouldHave(text(city))
                .shouldHave(text(address));


    }
    static Stream<Arguments> selenideMethodSourceTest(){
        return Stream.of(
                Arguments.of("Developers", List.of("Documentation", "Training", "Certifications", "Build with Copilot", "Customize Copilot", "Integrations", "Trending repos", "GitHub Sponsors", "Events")),
                Arguments.of("Businesses", List.of("Enterprises", "Team", "Startups", "Nonprofits", "Healthcare", "Financial services", "Manufacturing", "Government", "See all industries", "Why GitHub", "Customer Stories", "Business insights", "Trust Center")),
                Arguments.of("Resources", List.of("Documentation", "Resource center", "Blog", "Events & webinars", "Community Forum", "Customer Stories", "Integrations", "Customer support","Training", "GitHub Status","About","Partners","Changelog"))

        );
    }
    @MethodSource
    @ParameterizedTest(name = "Для меню {0} отображается разделы {1}")
    @Tag("UI_TEST")
    void selenideMethodSourceTest(
            String menuTitle,
            List<String> expectedSubsections
    ){
        open("https://github.com/");
        $(byText(menuTitle)).hover();
        $$("button[aria-expanded='true'] + div a")
                .filter(visible)
                .shouldHave(containExactTextsCaseSensitive(expectedSubsections));

    }
}
