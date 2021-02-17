package com.example.demo.service.trademark;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.TradeMark;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TrademarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrademarkService implements ITrademarkService{

    @Autowired
    private TrademarkRepository trademarkRepository;

    @Override
    public Page<TradeMark> findAll(Pageable pageable) {
        return trademarkRepository.findAll(pageable);
    }

    @Override
    public Iterable<TradeMark> findAll() {
        return trademarkRepository.findAll();
    }

    @Override
    public Optional<TradeMark> findById(Long id) throws NotFoundException {
        Optional<TradeMark> tradeMark = trademarkRepository.findById(id);
        if (tradeMark == null)  throw new NotFoundException();
        return tradeMark;
    }

    @Override
    public void delete(Long id) {
        trademarkRepository.deleteById(id);
    }

    @Override
    public TradeMark save(TradeMark tradeMark) {
        return trademarkRepository.save(tradeMark);
    }

    @Override
    public Page<TradeMark> findAllByFirstNameContaining(String name, Pageable pageable) {
        return trademarkRepository.findAllByNameContaining(name, pageable);
    }
}
