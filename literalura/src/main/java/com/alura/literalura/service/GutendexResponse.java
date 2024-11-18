package com.alura.literalura.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexResponse {

    private List<Book> results;

    // Getters y setters
    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Book {
        private String title;
        private List<Person> authors;
        private List<String> languages;
        private Integer downloadCount;

        // Getters y setters
        @JsonProperty("title")
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @JsonProperty("authors")
        public List<Person> getAuthors() {
            return authors;
        }

        public void setAuthors(List<Person> authors) {
            this.authors = authors;
        }

        @JsonProperty("languages")
        public List<String> getLanguages() {
            return languages;
        }

        public void setLanguages(List<String> languages) {
            this.languages = languages;
        }

        @JsonProperty("download_count")
        public Integer getDownloadCount() {
            return downloadCount;
        }

        public void setDownloadCount(Integer downloadCount) {
            this.downloadCount = downloadCount;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Person {
        private String name;
        private Integer birthYear;
        private Integer deathYear;

        // Getters y setters
        @JsonProperty("name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JsonProperty("birth_year")
        public Integer getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(Integer birthYear) {
            this.birthYear = birthYear;
        }

        @JsonProperty("death_year")
        public Integer getDeathYear() {
            return deathYear;
        }

        public void setDeathYear(Integer deathYear) {
            this.deathYear = deathYear;
        }
    }
}
