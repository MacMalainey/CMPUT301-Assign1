package com.malainey.medbook.util;

import java.util.ArrayList;
import java.util.List;

public abstract class ChangeEventEmitter<R, S> {
    private List<ChangeListener<R, S>> listeners = new ArrayList<>();

    public void addListener(ChangeListener<R, S> listener) {
        if (listeners.contains(listener)) return;
        listeners.add(listener);
    }

    public void removeListener(ChangeListener<R, S> listener) {
        if (listeners.contains(listener)) return;
        listeners.remove(listener);
    }

    protected void fireEvent(R event, S changedItem) {
        listeners.forEach(listener -> listener.onChange(event, changedItem));
    }

    interface ChangeListener<T, U> {
        void onChange(T event, U changedItem);
    }
}
