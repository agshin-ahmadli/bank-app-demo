package com.example.bankinkapp.repository;

import com.example.bankinkapp.models.ToAnotherAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnotherAccRepo extends JpaRepository<ToAnotherAccount,Long> {
}
