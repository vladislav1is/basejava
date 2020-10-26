package com.redfox.webapp.model;

import java.util.List;

public class ListCompanyContent implements Content {
    private final List<Company> companies;

    public ListCompanyContent(List<Company> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return companies.toString();
    }
}
