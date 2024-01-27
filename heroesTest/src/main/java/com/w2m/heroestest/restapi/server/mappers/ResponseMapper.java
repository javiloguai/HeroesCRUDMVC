package com.w2m.heroestest.restapi.server.mappers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Mapper from Domain entity to response DTO
 *
 * @param <I> input
 * @param <R> Response
 */
public interface ResponseMapper<I, R> {

    /**
     * To response.
     *
     * @param input the input
     * @return the r
     */
    R toResponse(I input);

    /**
     * To responses.
     *
     * @param inputs the inputs
     * @return the list
     */
    List<R> toResponses(List<I> inputs);

    /**
     * Map list of data transfer objects on a list of response model objects. Both contained in Page container.
     *
     * @param dtoPage Page container containing data transfer objects
     * @return Page container containing response model objects
     */
    default Page<R> toResponses(Page<I> dtoPage) {
        if (dtoPage == null || dtoPage.isEmpty()) {
            return Page.empty();
        } else {
            List<R> list = dtoPage.stream().map(this::toResponse).toList();
            return new PageImpl<>(list);
        }
    }

}
