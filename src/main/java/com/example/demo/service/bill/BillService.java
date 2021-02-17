package com.example.demo.service.bill;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class BillService implements IBillService {
    @Autowired
    private BillRepository billRepository;
    @Override
    public Iterable<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Optional<Bill> findById(Long id) throws NotFoundException {
        return billRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        billRepository.deleteById(id);
    }

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }
}
