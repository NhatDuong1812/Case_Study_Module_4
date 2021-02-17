package com.example.demo.service.billdetails;

import com.example.demo.model.BillDetails;
import com.example.demo.service.IService;

public interface IBillDetailsService extends IService<BillDetails> {
    Iterable<BillDetails> findAllByBillDetails(Long id);
}
