package com.example.demo.repository;

import com.example.demo.model.BillDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailsRepository extends CrudRepository<BillDetails, Long> {
    Iterable<BillDetails> findAllByBill_Id(Long id);
}
