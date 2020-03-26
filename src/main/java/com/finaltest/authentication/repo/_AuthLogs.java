package com.finaltest.authentication.repo;

import com.finaltest.authentication.model.AuthLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface _AuthLogs extends CrudRepository<AuthLog, Integer> {
}
