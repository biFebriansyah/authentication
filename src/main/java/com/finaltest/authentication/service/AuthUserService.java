package com.finaltest.authentication.service;

import com.finaltest.authentication.dto.AuthDto;
import com.finaltest.authentication.exception.AuthException;
import com.finaltest.authentication.model.AuthLog;
import com.finaltest.authentication.model.AuthModel;
import com.finaltest.authentication.repo._AuthLogs;
import com.finaltest.authentication.repo._AuthRepo;
import com.finaltest.authentication.token.AuthTokenBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthUserService implements _AuthUserService {

    @Autowired
    _AuthRepo authRepo;

    @Autowired
    _AuthLogs authLogs;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    AuthTokenBuilder tokenBuilder;

    @Override
    public Map<String, Object> login(String email, String password) throws AuthException {
        Query query = entityManager.createQuery("From AuthModel WHERE email = :email").setParameter("email", email);
        List<AuthModel> respon = query.getResultList();
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if (respon.size() > 0) {
            AuthModel aM = respon.get(0);
            if (new BCryptPasswordEncoder().matches(password, aM.getPassword())) {
                AuthDto AuthDto = new AuthDto();
                AuthDto.setEmail(aM.getEmail());
                AuthDto.setFullname(aM.getFullname());

                Map<String, Object> data = new HashMap<>();
                data.put("id", aM.getId());
                data.put("fullname", aM.getFullname());
                data.put("email", aM.getEmail());
                data.put("token", tokenBuilder.GenerateToken(AuthDto));

                AuthLog logs = new AuthLog();
                logs.setEmail(aM.getEmail());
                logs.setDate(date.format(dateTimeFormatter));
                logs.setStatus(AuthLog.Succsess);
                logs.setUserAuth(aM);
                authLogs.save(logs);
                return data;

            } else {
                AuthLog logs = new AuthLog();
                logs.setEmail(aM.getEmail());
                logs.setDate(date.format(dateTimeFormatter));
                logs.setStatus(AuthLog.Filed);
                logs.setUserAuth(aM);
                authLogs.save(logs);
                throw new AuthException("Password Wrong");
            }

        } else {
            throw new AuthException("Username not Found");
        }
    }

    @Override
    public List<AuthModel> get() {
        List<AuthModel> data = new ArrayList<>();
        authRepo.findAll().iterator().forEachRemaining(data::add);
        return data;
    }

    @Override
    public AuthModel getById(int id) {
        return authRepo.findById(id).get();
    }

    @Override
    public AuthDto getByWhat(String email, String fullname) {

        Query query = null;
        if (email != null && fullname == null) {
            query = entityManager.createQuery("From AuthModel WHERE email = :email").setParameter("email", email);
        }
        else if (fullname != null && email == null) {
            query = entityManager.createQuery("From AuthModel WHERE fullname = :email").setParameter("email", email);
        }

        List<AuthModel> respon = query.getResultList();
        if (respon.size() > 0) {
            AuthModel aM = respon.get(0);
            AuthDto AuthDto = new AuthDto();
            AuthDto.setFullname(aM.getFullname());
            AuthDto.setEmail(aM.getEmail());
            AuthDto.setPassword(aM.getPassword());
            return AuthDto;
        } else {
            return null;
        }
    }

    @Override
    public AuthModel updateUser(int id, AuthDto dto) {
        return null;
    }

    @Override
    public String delete(int id) {
        return null;
    }

    @Override
    public AuthModel addUser(String fullName, String email, String password, String repass) throws AuthException {
        System.out.println(email);
        if (getByWhat(email, null) == null) {
            AuthModel userModel = new AuthModel();
            String pas = new BCryptPasswordEncoder().encode(password);
            if (password.equals(repass)) {
                userModel.setEmail(email);
                userModel.setFullname(fullName);
                userModel.setPassword(pas);
                authRepo.save(userModel);
                return userModel;
            }
            throw new AuthException("password not match");

        } else {throw new AuthException("email alredy exsist");}
    }
}
