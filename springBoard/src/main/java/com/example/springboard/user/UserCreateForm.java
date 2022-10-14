package com.example.springboard.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreateForm {

    @Size(min = 3, max = 25)
    @NotEmpty(message = "ユーザーIDを入力してください。")
    private String username;

    @NotEmpty(message = "パスワードを入力してください。")
    private String password1;

    @NotEmpty(message = "パスワード再確認にデータを入力してください。")
    private String password2;

    @NotEmpty(message = "Emailアドレスを入力してください。")
    @Email
    private String email;

}
