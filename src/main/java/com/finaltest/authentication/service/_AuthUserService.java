package com.finaltest.authentication.service;

import com.finaltest.authentication.dto.AuthDto;
import com.finaltest.authentication.exception.AuthException;
import com.finaltest.authentication.model.AuthModel;

import java.util.List;
import java.util.Map;

public interface _AuthUserService {

    Map<String, Object> login (String email, String password) throws AuthException;
    List<AuthModel> get();
    AuthModel getById(int id);
    AuthDto getByWhat(String email, String fullname);
    AuthModel updateUser(int id, AuthDto dto);
    String delete(int id);
    AuthModel addUser(String name, String email, String password, String repass) throws AuthException;
}
