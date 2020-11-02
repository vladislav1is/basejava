package com.redfox.webapp.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.redfox.webapp.util.DateUtil.*;

public class Organization {
    private final Link homePage;
    private final List<Experience> periods;

    public Organization(String name, Experience... periods) {
        this(new Link(name), Arrays.asList(periods));
    }

    public Organization(String name, List<Experience> periods) {
        this(new Link(name), periods);
    }

    public Organization(String name, String url, Experience... periods) {
        this(new Link(name, url), Arrays.asList(periods));
    }

    public Organization(Link homePage, List<Experience> periods) {
        Objects.requireNonNull(homePage, "homePage must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.homePage = homePage;
        this.periods = periods;
    }

    @Override
    public String toString() {
        return "Organization(" + homePage + "," + periods + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    public static class Experience {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Experience(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Experience(int startYear, Month startMonth, String title) {
            this(of(startYear, startMonth), NOW, title, null);
        }

        public Experience(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);

        }

        public Experience(int startYear, Month startMonth, int endYear, Month endMonth, String title) {
            this(startYear, startMonth, endYear, endMonth, title, null);
        }

        public Experience(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public Experience(LocalDate startDate, LocalDate endDate, String title) {
            this(startDate, endDate, title, null);
        }

        @Override
        public String toString() {
            return "Experience(" + startDate + " " + endDate + " " + title + " " + description + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Experience period = (Experience) o;

            if (!startDate.equals(period.startDate)) return false;
            if (!endDate.equals(period.endDate)) return false;
            if (!title.equals(period.title)) return false;
            return description != null ? description.equals(period.description) : period.description == null;
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }
    }
}
