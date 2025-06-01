package me.ryanmood.ryutils.base;

public interface RyTaskBase<T> {

    /**
     * Create a task that runs within a scheduler.
     *
     * @param callable The Runnable code you would like to happen.
     */
    int run(Runnable callable);

    /**
     * Create a task that runs within an async scheduler.
     *
     * @param callable The Runnable code you would like to happen.
     */
    int runAsync(Runnable callable);

    /**
     * Create a task that runs within a scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    int runLater(Runnable callable, long delay);

    /**
     * Create a task that runs within an async scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     */
    int runAsyncLater(Runnable callable, long delay);

    /**
     * Create a task that runs on a timer within a scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    int runTimer(Runnable callable, long delay, long interval);

    /**
     * Create a task that runs on a timer within an async scheduler after a certain amount of time.
     *
     * @param callable The Runnable code you would like to happen.
     * @param delay    The delay before the runnable runs.
     * @param interval The interval before the runnable runs again.
     */
    int runAsyncTimer(Runnable callable, long delay, long interval);

    /**
     * Get a task based of its id.
     *
     * @param id The id of the task
     *
     * @return The runnable task that was requested.
     */
    T getById(int id);

    /**
     * Cancel a task based off its id.
     *
     * @param id The ID of the task you wish to cancel.
     */
    void cancel(int id);

    /**
     * Cancel all tasked that are running for the plugin.
     */
    void cancelAll();

}
