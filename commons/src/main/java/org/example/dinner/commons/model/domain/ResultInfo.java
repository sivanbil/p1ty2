package org.example.dinner.commons.model.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResultInfo<T> implements Serializable {

    private Integer code;

    private String message;

    private String path;

    private T data;
}
