package formSubmitingTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.text.DateFormatSymbols;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FormSubmissionAndCheck {

    String firstName = "Vasdas";
    String lastName = "Vasdasswrewr";
    String eMail = "Vasdas@marte.ru";
    String gender = "Male";
    String mobile = "0987654321";
    String dateOfBirthDay = "02";
    int dateOfBirthMonth = 11;
    String dateOfBirthYear = "2009";
    String subj1 = "Maths";
    String subj2 = "Chemistry";
    String hobby1 = "Sports";
    String hobby2 = "Music";
    String picName = "1.jpg";
    String picpath = "Documents/"+picName;
    String adress = "Togliatty, rest asdkj., 81 09";
    String state = "NCR";
    String city = "Delhi";


    @BeforeAll
    static void setup(){
        Configuration.startMaximized = true;
        Configuration.browser = "safari";
        open("https://demoqa.com/automation-practice-form");
    }

    @Test
    void submittingAndCheck(){

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(eMail);
        $("[name=gender][value="+gender+"]").parent().click();
        $("#userNumber").setValue(mobile);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(dateOfBirthMonth-1); // month -1
        $(".react-datepicker__year-select").selectOptionByValue(dateOfBirthYear);
        $(".react-datepicker__day--0" + dateOfBirthDay).click();
        $("#subjectsInput").setValue(subj1).pressEnter();
        $("#subjectsInput").setValue(subj2).pressTab();//$(byText(hobby1)).click();
        SelenideElement selenideElement = $(byText(hobby1));
        String locator = selenideElement.getAttribute("for");
        $("#"+locator).parent().click();
        $(byText(hobby2)).click();
        $("#uploadPicture").uploadFile(new File("/Users/vladimirklonin/"+picpath));
        $("#currentAddress").setValue(adress);
        $("#stateCity-wrapper #state input").setValue(state).pressTab();
        $("#stateCity-wrapper #city input").setValue(city).pressTab();
        $("#submit").pressEnter();

        ElementsCollection result = $$("td");

        result.find(exactText("Student Name")).sibling(0).shouldHave(text(firstName + " " + lastName));
        result.find(exactText("Student Email")).sibling(0).shouldHave(text(eMail));
        result.find(exactText("Gender")).sibling(0).shouldHave(text(gender));
        result.find(exactText("Mobile")).sibling(0).shouldHave(text(mobile));
        result.find(exactText("Date of Birth")).sibling(0).shouldHave(text(dateOfBirthDay+ " "+ new DateFormatSymbols().getMonths()[dateOfBirthMonth-1] + "," + dateOfBirthYear));
        result.find(exactText("Subjects")).sibling(0).shouldHave(text(subj1 +", "+subj2));
        result.find(exactText("Hobbies")).sibling(0).shouldHave(text(hobby1 + ", " + hobby2));
        result.find(exactText("Picture")).sibling(0).shouldHave(text(picName));
        result.find(exactText("Address")).sibling(0).shouldHave(text(adress));
        result.find(exactText("State and City")).sibling(0).shouldHave(text(state + " " + city));

    }

}
