package com.example.demo.service.billdetails;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.BillDetails;
import com.example.demo.repository.BillDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BillDetailsService implements IBillDetailsService {
   @Autowired
   private BillDetailsRepository billDetailsRepository;
    @Override
    public Iterable<BillDetails> findAll() {
        return billDetailsRepository.findAll();
    }

    @Override
    public Optional<BillDetails> findById(Long id) throws NotFoundException {
        return billDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        billDetailsRepository.deleteById(id);
    }

    @Override
    public BillDetails save(BillDetails billDetails) {
        return billDetailsRepository.save(billDetails);
    }

    @Override
    public Iterable<BillDetails> findAllByBillDetails(Long id) {
        return billDetailsRepository.findAllByBill_Id(id);
    }
}
