package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rental {
    @Id
    private String id;
    private String bookId;
    private String renterName;
    private Date rentalDate;
    private Date returnDate;
}
