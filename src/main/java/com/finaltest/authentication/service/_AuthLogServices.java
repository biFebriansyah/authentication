package com.finaltest.authentication.service;

import com.finaltest.authentication.model.AuthLog;
import java.util.List;

public interface _AuthLogServices {

    List<AuthLog> get();
    List<AuthLog> getByEmail(String email, String status);
}
