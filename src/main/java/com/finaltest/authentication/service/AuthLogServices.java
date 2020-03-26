package com.finaltest.authentication.service;

import com.finaltest.authentication.model.AuthLog;
import com.finaltest.authentication.repo._AuthLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthLogServices implements _AuthLogServices {

    @Autowired
    _AuthLogs authLogs;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthLog> get() {
        List<AuthLog> logs = new ArrayList<>();
        authLogs.findAll().iterator().forEachRemaining(logs::add);
        return logs;
    }

    @Override
    public List<AuthLog> getByEmail(String email, String status) {
        Query query = null;
        if (status == null) {
            query = entityManager.createQuery("FROM AuthLog WHERE email = :email").setParameter("email", email);
        }
        else {
            query = entityManager.createQuery("FROM AuthLog WHERE email = :email AND status = :status").setParameter("email", email).setParameter("status", status);
        }
        List<AuthLog> data = query.getResultList();
        return data;
    }
}
