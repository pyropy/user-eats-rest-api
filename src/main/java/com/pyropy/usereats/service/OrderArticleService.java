package com.pyropy.usereats.service;

import com.pyropy.usereats.dto.OrderArticleDto;
import com.pyropy.usereats.model.OrderArticles;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderArticleService {


    @Autowired
    private ModelMapper modelMapper;

    /*
     * Converts OrderArticle Entity to OrderArticleDto class.
     * */
    public OrderArticleDto convertToDto(OrderArticles orderArticles) {
        return modelMapper.map(orderArticles, OrderArticleDto.class);
    }

    /*
     * Converts OrderArticleDto entity to OrderArticle class.
     * */
    public OrderArticles convertToEntity(OrderArticleDto orderArticleDto) {
        return modelMapper.map(orderArticleDto, OrderArticles.class);
    }

    public List<OrderArticleDto> convertIterableToListDto(Iterable<OrderArticles> orderArticles) {
        return StreamSupport
                .stream(orderArticles.spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
