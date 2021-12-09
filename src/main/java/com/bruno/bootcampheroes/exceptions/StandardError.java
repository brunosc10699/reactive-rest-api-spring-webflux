package com.bruno.bootcampheroes.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private int status;
    private String error;
    private String message;
}
