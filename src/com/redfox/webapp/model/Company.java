package com.redfox.webapp.model;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.Objects;

public class Company {
    private final String name;
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String header;
    private String description;

    public Company(String name, YearMonth startDate, YearMonth endDate, String header) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(header, "header must not be null");
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.header = header;
    }

    public Company(String name, YearMonth startDate, YearMonth endDate, String header, String description) {
        this(name, startDate, endDate, header);
        this.description = description;
    }

    @Override
    public String toString() {
        return Arrays.asList(name, startDate, endDate, header, description).toString();
    }
}
