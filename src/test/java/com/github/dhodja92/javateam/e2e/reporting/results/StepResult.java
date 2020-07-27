package com.github.dhodja92.javateam.e2e.reporting.results;

import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import java.time.Duration;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class StepResult {

    private final String name;
    private final Status status;
    private final Duration duration;

    public StepResult(String name, Result result) {
        this.name = requireNonNull(name, "name must not be null");
        this.status = requireNonNull(result.getStatus(), "status must not be null");
        this.duration = requireNonNull(result.getDuration(), "duration must not be null");
    }

    public String getName() {
        return this.name;
    }

    public Status getStatus() {
        return this.status;
    }

    public Duration getDuration() {
        return this.duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) { return false; }

        StepResult that = (StepResult) o;

        return Objects.equals(this.name, that.name)
                && this.status == that.status
                && Objects.equals(this.duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.status, this.duration);
    }

    @Override
    public String toString() {
        return "StepResult{"
                + "name='" + this.name + '\''
                + ", status=" + this.status
                + ", duration=" + this.duration
                + '}';
    }
}
