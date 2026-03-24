package com.example.priceservice.mapping;

import com.example.priceservice.dto.PriceDTO;
import com.example.priceservice.entities.Price;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceDTO toPriceDTO(Price price);

    Price fromDTO(PriceDTO dto);

}
