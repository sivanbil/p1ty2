package org.example.dinner.commons.model.pojo;

import lombok.Getter;
import lombok.Setter;
import org.example.dinner.commons.model.base.BaseModel;

@Getter
@Setter
public class DinnerUser extends BaseModel {

    private Integer id;

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private String password;

    private String avatar;

    private String roles;
}
