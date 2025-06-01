package org.myapp.jwttest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Builder
public class BooksDTO {

    private Long id;

    private String name;
    private String author;
    private double price;
    private Timestamp createDate;
}
