package com.github.dhodja92.javateam.e2e.reporting.results;

import io.cucumber.plugin.event.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.github.dhodja92.javateam.e2e.util.Utils.TC_PATTERN;

public final class ExecutionResult {

    private final String scenarioId;
    private final String scenarioTitle;
    private Status status;
    private final List<StepResult> stepResults;

    public ExecutionResult(String scenarioTitle) {
        Matcher matcher = TC_PATTERN.matcher(scenarioTitle);
        if (matcher.find()) {
            this.scenarioId = matcher.group(0);
        } else {
            this.scenarioId = "(no ID) " + scenarioTitle;
        }
        this.scenarioTitle = scenarioTitle;
        this.stepResults = new ArrayList<>();
    }

    public void addStepResult(StepResult stepResult) {
        switch (stepResult.getStatus()) {
            case PASSED:
                if (this.status != Status.FAILED) {
                    this.status = Status.PASSED;
                }
                break;
            case FAILED:
                this.status = Status.FAILED;
                break;
            case SKIPPED:
            case PENDING:
            case AMBIGUOUS:
            case UNDEFINED:
            case UNUSED:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + stepResult.getStatus());
        }

        this.stepResults.add(stepResult);
    }

    public String getScenarioId() {
        return this.scenarioId;
    }

    public String getScenarioTitle() {
        return this.scenarioTitle;
    }

    public Status getStatus() {
        return this.status;
    }

    public List<StepResult> getStepResults() {
        return this.stepResults;
    }
}
