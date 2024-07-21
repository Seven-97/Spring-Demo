package com.seven.springmvchelloworld.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Seven
 */
@Data
@AllArgsConstructor
public class User {

    private String name;

    private int age;
}
