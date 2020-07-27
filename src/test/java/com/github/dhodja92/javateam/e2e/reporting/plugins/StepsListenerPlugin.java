package com.github.dhodja92.javateam.e2e.reporting.plugins;

import com.github.dhodja92.javateam.e2e.reporting.StoryReporter;
import com.github.dhodja92.javateam.e2e.reporting.results.StepResult;
import com.github.dhodja92.javateam.e2e.selenium.SeleniumHandler;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Step;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestStepFinished;

public final class StepsListenerPlugin implements ConcurrentEventListener {

    private final EventHandler<TestCaseStarted> testCaseStartedEventHandler =
            event -> StoryReporter.instance().startScenario(event.getTestCase().getName());

    private final EventHandler<TestStepFinished> testStepFinishedEventHandler = event -> {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            Step testStep = ((PickleStepTestStep) event.getTestStep()).getStep();

            final String stepName = testStep.getKeyWord() + testStep.getText();

            StepResult stepResult = new StepResult(stepName, event.getResult());

            StoryReporter.instance().getRunningScenario().addStepResult(stepResult);
        }
    };

    private final EventHandler<TestCaseFinished> testCaseFinishedEventHandler =
            event -> StoryReporter.instance().finalizeRunningScenario();

    private final EventHandler<TestRunFinished> testRunFinishedEventHandler = event -> {
        StoryReporter.instance().logReportSummary();

        SeleniumHandler.instance().getDriver().quit();
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, testCaseStartedEventHandler);
        publisher.registerHandlerFor(TestStepFinished.class, testStepFinishedEventHandler);
        publisher.registerHandlerFor(TestCaseFinished.class, testCaseFinishedEventHandler);
        publisher.registerHandlerFor(TestRunFinished.class, testRunFinishedEventHandler);
    }
}