package com.example.entity;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Currency {
    private long id;
    private String code;
    private String fullName;
    private String sign;
}
