package com.kakaobank.location.endpoint;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ToString
public class JoinUserRequest {
    @ApiModelProperty(required = true, example = "아이다", value = "아이디")
    @NotNull(message = "아이디는 필수 값입니다.")
    @Size(min = 3, max = 20)
    private String userId;

    @ApiModelProperty(required = true, example = "비밀번호", value = "비밀번호")
    @NotNull(message = "비밀번호는 필수 값입니다.")
    @Size(min = 4, max = 20)
    private String password;
}
