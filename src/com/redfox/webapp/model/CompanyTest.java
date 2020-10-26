package com.redfox.webapp.model;

import java.time.YearMonth;
import java.util.List;

public class CompanyTest {
    private final String name;
    private final List<YearMonth> startDate;
    private final List<YearMonth> endDate;
    private final List<String> header;

    public CompanyTest(String name, List<YearMonth> startDate, List<YearMonth> endDate, List<String> header) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.header = header;
    }
}
