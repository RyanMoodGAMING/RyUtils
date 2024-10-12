package me.ryanmood.ryutils.base;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface RyTasksBase {

    @Getter
    final Map<Integer, Objects> tasks = new HashMap<>();

    /**
     * Create a task that runs within a scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    void run(Object plugin, Runnable callable);

    /**
     * Create a task that runs within an async scheduler.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     */
    void runAsync(Object plugin, Runnable callable);

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
     void runLater(Object plugin, Runnable callable, long delay);

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
     void runAsyncLater(Object plugin, Runnable callable, long delay);

    /**
     * Create a task that runs on a timer within a scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    void runTimer(Object plugin, Runnable callable, long delay, long interval);

    /**
     * Create a task that runs on a timer within an async scheduler after a certain amount of time.
     *
     * @param plugin   The instance of the plugin you are using.
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    void runAsyncTimer(Object plugin, Runnable callable, long delay, long interval);

    Object getById(int id);

    void cancel(int id);

    void cancelAll();

}
