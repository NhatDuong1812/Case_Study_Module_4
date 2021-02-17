package com.example.demo.service.trademark;

import com.example.demo.model.TradeMark;
import com.example.demo.service.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface ITrademarkService extends IService<TradeMark> {
    Page<TradeMark> findAllByFirstNameContaining(String name, Pageable pageable);

    Page<TradeMark> findAll(Pageable pageable);

}
