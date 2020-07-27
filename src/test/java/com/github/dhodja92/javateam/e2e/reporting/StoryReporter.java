package com.github.dhodja92.javateam.e2e.reporting;

import com.github.dhodja92.javateam.e2e.reporting.results.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public final class StoryReporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryReporter.class);

    private final Deque<ExecutionResult> runningScenarioStack = new ConcurrentLinkedDeque<>();
    private final Deque<ExecutionResult> scenarioExecutionSequence = new ConcurrentLinkedDeque<>();

    private StoryReporter() {
        //
    }

    public void startScenario(String scenarioTitle) {
        this.runningScenarioStack.addFirst(new ExecutionResult(scenarioTitle));
    }

    public ExecutionResult getRunningScenario() {
        return this.runningScenarioStack.getFirst();
    }

    public void finalizeRunningScenario() {
        ExecutionResult runningScenario = this.runningScenarioStack.removeFirst();
        this.scenarioExecutionSequence.addLast(runningScenario);
    }

    public void logReportSummary() {
        LOGGER.info("Execution summary ({} scenarios executed):", this.scenarioExecutionSequence.size());
        for (ExecutionResult executionResult : this.scenarioExecutionSequence) {
            LOGGER.info(
                    "{}: {}: {}",
                    executionResult.getScenarioId(),
                    executionResult.getScenarioTitle(),
                    executionResult.getStatus()
            );
        }
    }

    private static final class InstanceHolder {
        private static final StoryReporter instance = new StoryReporter();
    }

    public static StoryReporter instance() {
        return InstanceHolder.instance;
    }
}
