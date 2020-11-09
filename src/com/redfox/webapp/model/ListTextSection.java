package com.redfox.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<String> items;

    public ListTextSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListTextSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTextSection that = (ListTextSection) o;
        return items.equals(that.items);

    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}