package com.malainey.medbook.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows the emitting and listening of class defined events
 * @param <R> Event type
 * @param <S> Data type
 */
public abstract class ChangeEventEmitter<R, S> {

    /**
     * Internal list to keep track of listeners
     */
    private final List<ChangeListener<R, S>> listeners = new ArrayList<>();

    /**
     * Add a listener
     * @param listener callback to register
     */
    public final void addListener(ChangeListener<R, S> listener) {
        if (listeners.contains(listener)) return;
        listeners.add(listener);
    }

    /**
     * Remove a listener
     * @param listener callback to unregister
     */
    public final void removeListener(ChangeListener<R, S> listener) {
        if (listeners.contains(listener)) return;
        listeners.remove(listener);
    }

    /**
     * Method to fire events
     * @param event event to fire
     * @param changedItem item affected by event
     */
    protected final void fireEvent(R event, S changedItem) {
        listeners.forEach(listener -> listener.onChange(event, changedItem));
    }

    /**
     * Callback interface
     * @param <T> Event type
     * @param <U> Data type
     */
    public interface ChangeListener<T, U> {
        void onChange(T event, U changedItem);
    }
}
