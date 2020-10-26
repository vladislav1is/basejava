package com.redfox.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListCompanyContent implements Content {
    private final List<Company> companies;

    public ListCompanyContent(List<Company> companies) {
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = companies;
    }

    @Override
    public String toString() {
        return companies.toString();
    }
}
