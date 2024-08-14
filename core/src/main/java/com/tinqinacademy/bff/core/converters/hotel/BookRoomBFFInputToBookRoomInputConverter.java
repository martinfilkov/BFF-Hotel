package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomBFFInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookRoomBFFInputToBookRoomInputConverter implements Converter<BookRoomBFFInput, BookRoomInput.BookRoomInputBuilder> {

    @Override
    public BookRoomInput.BookRoomInputBuilder convert(BookRoomBFFInput input) {
        log.info("Start converting from BookRoomBFFInput to BookRoomInput");

        BookRoomInput.BookRoomInputBuilder output = BookRoomInput.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phone(input.getPhone())
                .startDate(input.getStartDate())
                .endDate(input.getEndDate());

        log.info("End converting from BookRoomBFFInput to BookRoomInput");
        return output;
    }
}
