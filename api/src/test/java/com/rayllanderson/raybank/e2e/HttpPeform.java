package com.rayllanderson.raybank.e2e;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public interface HttpPeform {

    MockMvc mvc();

    default ResultActions post(final String url, final Object body) {
        final var request = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(body));

        return perform(request);
    }

    default ResultActions post(final String url, final String id, final Object body) {
        final var request = MockMvcRequestBuilders.post(concatAsPathUrl(url, id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(body));

        return perform(request);
    }

    @NotNull
    private ResultActions perform(MockHttpServletRequestBuilder request) {
        try {
            return this.mvc().perform(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default ResultActions get(final String url) {
        final var request = MockMvcRequestBuilders.get(url);
        return perform(request);
    }

    default ResultActions get(final String url, final String id) {
        final var request = MockMvcRequestBuilders.get(concatAsPathUrl(url, id));
        return perform(request);
    }

    default ResultActions get(final String url, final String id, String endPathUrl) {
        final var request = MockMvcRequestBuilders.get(concatAsPathUrl(url, id, endPathUrl));
        return perform(request);
    }

    default ResultActions get(final String url, final int page, final int size, final String status, final String[] sort) {
        final var aRequest = MockMvcRequestBuilders.get(url)
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("status", status)
                .queryParam("sort", sort)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return perform(aRequest);
    }

    default ResultActions get(final String url, final int page, final int size, final String[] sort) {
        final var aRequest = MockMvcRequestBuilders.get(url)
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("sort", sort)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return perform(aRequest);
    }

    default ResultActions get(final String url, final int page, final int size) {
        final var aRequest = MockMvcRequestBuilders.get(url)
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return perform(aRequest);
    }

    default ResultActions get(final String url, final int page, final int size, final String status) {
        final var aRequest = MockMvcRequestBuilders.get(url)
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("status", status)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return perform(aRequest);
    }

    default ResultActions delete(final String url, final String id) {
        final var aRequest = MockMvcRequestBuilders.delete(concatAsPathUrl(url, id));
        return perform(aRequest);
    }

    default ResultActions patch(final String url, final String id, final Object requestBody) {
        final var aRequest = MockMvcRequestBuilders.patch(concatAsPathUrl(url, id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(requestBody));
        return perform(aRequest);
    }

    default ResultActions patch(final String url, final String id, String endPartUrl, final Object requestBody) {
        final var aRequest = MockMvcRequestBuilders.patch(concatAsPathUrl(url, id, endPartUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(requestBody));
        return perform(aRequest);
    }

    default ResultActions patch(final String url, final String id, String endPartUrl, final String json) {
        final var aRequest = MockMvcRequestBuilders.patch(concatAsPathUrl(url, id, endPartUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        return perform(aRequest);
    }

    default ResultActions patch(final String url, final Object requestBody) {
        final var aRequest = MockMvcRequestBuilders.patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(requestBody));
        return perform(aRequest);
    }

    private String concatAsPathUrl(String url, String id) {
        return url + "/" + id;
    }

    private String concatAsPathUrl(String url, String id, String endPartUrl) {
        return concatAsPathUrl(url, id).concat("/").concat(endPartUrl);
    }
}
