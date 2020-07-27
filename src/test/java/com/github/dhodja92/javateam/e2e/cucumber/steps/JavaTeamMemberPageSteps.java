package com.github.dhodja92.javateam.e2e.cucumber.steps;

import com.github.dhodja92.javateam.e2e.selenium.SeleniumHandler;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JavaTeamMemberPageSteps {

    @Given("The Java team members page is visible")
    public void givenTheJavaTeamMembersPageIsVisible() {
        SeleniumHandler.instance().getDriver().get("http://localhost:8080/index");

        getAndAssertTabTitle("Members");
    }

    private void getAndAssertTabTitle(String expectedTitle) {
        String tabTitle = new WebDriverWait(SeleniumHandler.instance().getDriver(), 10)
                .until(WebDriver::getTitle);

        assertEquals(
                String.format("Tab title should be equal to '%s'", expectedTitle),
                expectedTitle,
                tabTitle
        );
    }

    @When("A Java team member attempts to add a new member with first name {string} and last name {string}")
    public void aJavaTeamMemberAttemptsToAddANewMemberWithFirstNameAndLastName(
            String firstName,
            String lastName
    ) {
        WebElement addMemberButton = findElementWithWaitPeriodAndWaitUntilClickable(By.id("add-user"));
        addMemberButton.click();

        WebElement title = findElementWithWaitPeriodAndWaitUntilVisible(By.tagName("h2"));
        WebElement firstNameInput = findElementWithWaitPeriodAndWaitUntilClickable(By.id("firstName"));
        WebElement lastNameInput = findElementWithWaitPeriodAndWaitUntilClickable(By.id("lastName"));
        WebElement submitNewMemberButton = findElementWithWaitPeriodAndWaitUntilClickable(By.id("add-member"));

        assertEquals(
                "Page title should be equal to 'New member'",
                "New member",
                title.getText()
        );
        assertEquals(
                "First name form input placeholder should be equal to 'First name'",
                "First name",
                firstNameInput.getAttribute("placeholder")
        );
        assertEquals(
                "Last name form input placeholder should be equal to 'Last name'",
                "Last name",
                lastNameInput.getAttribute("placeholder")
        );

        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        submitNewMemberButton.click();

        getAndAssertTabTitle("Members");
    }

    @Then("The Java team members page should be updated, with the new member having handle {string}")
    public void theJavaTeamMembersPageShouldBeUpdatedWithTheNewMemberHavingHandle(
            String expectedUserHandle
    ) {
        WebElement membersTable = findElementWithWaitPeriodAndWaitUntilVisible(By.tagName("tbody"));

        Optional<WebElement> maybeUserHandle = membersTable.findElements(By.tagName("tr"))
                .stream()
                .flatMap(row -> row.findElements(By.tagName("td")).stream())
                .filter(column -> column.getText().equals(expectedUserHandle))
                .findFirst();

        if (!maybeUserHandle.isPresent()) {
            fail("Table does not contain team member with user handle '" + expectedUserHandle + '\'');
        }
    }

    private static WebElement findElementWithWaitPeriodAndWaitUntilClickable(By by) {
        return findElementWithWaitPeriodAndWaitUntilClickable(by, 10);
    }

    private static WebElement findElementWithWaitPeriodAndWaitUntilClickable(By by, int timeOutInSeconds) {
        return new WebDriverWait(SeleniumHandler.instance().getDriver(), timeOutInSeconds)
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    private static WebElement findElementWithWaitPeriodAndWaitUntilVisible(By by) {
        return findElementWithWaitPeriodAndWaitUntilVisible(by, 10);
    }

    private static WebElement findElementWithWaitPeriodAndWaitUntilVisible(By by, int timeOutInSeconds) {
        return new WebDriverWait(SeleniumHandler.instance().getDriver(), timeOutInSeconds)
                .until(driver -> driver.findElement(by));
    }
}
