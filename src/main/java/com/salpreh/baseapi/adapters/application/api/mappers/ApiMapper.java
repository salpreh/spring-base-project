package com.salpreh.baseapi.adapters.application.api.mappers;

import com.salpreh.baseapi.adapters.application.api.models.ApiPage;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ApiMapper {


  default <T> ApiPage<T> map(Page<T> src) {
    return ApiPage.of(src.getContent(), src.getPageable().getPageNumber(), src.getTotalPages());
  }
}
