package edu.mum.coffee.service;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Orderline;
import edu.mum.coffee.repository.OrderlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderlineService {

    @Autowired
    OrderlineRepository orderlineRepository;

    public Orderline save(Orderline orderline){
        return orderlineRepository.save(orderline);
    }

}
