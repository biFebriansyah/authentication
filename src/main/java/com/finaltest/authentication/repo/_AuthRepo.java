package com.finaltest.authentication.repo;

import com.finaltest.authentication.model.AuthModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface _AuthRepo extends CrudRepository<AuthModel, Integer> {
}
