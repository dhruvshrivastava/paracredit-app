package com.paracredit.paracredit.repository;

import com.paracredit.paracredit.entity.LoanScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanSchemeRepository extends JpaRepository<LoanScheme, Long> {
}
