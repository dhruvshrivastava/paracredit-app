package com.paracredit.paracredit.repository;

import com.paracredit.paracredit.entity.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {
}
