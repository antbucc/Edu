package com.modis.edu.service.impl;

import com.modis.edu.domain.FragmentOrder;
import com.modis.edu.repository.FragmentOrderRepository;
import com.modis.edu.service.FragmentOrderService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link FragmentOrder}.
 */
@Service
public class FragmentOrderServiceImpl implements FragmentOrderService {

    private final Logger log = LoggerFactory.getLogger(FragmentOrderServiceImpl.class);

    private final FragmentOrderRepository fragmentOrderRepository;

    public FragmentOrderServiceImpl(FragmentOrderRepository fragmentOrderRepository) {
        this.fragmentOrderRepository = fragmentOrderRepository;
    }

    @Override
    public FragmentOrder save(FragmentOrder fragmentOrder) {
        log.debug("Request to save FragmentOrder : {}", fragmentOrder);
        return fragmentOrderRepository.save(fragmentOrder);
    }

    @Override
    public FragmentOrder update(FragmentOrder fragmentOrder) {
        log.debug("Request to update FragmentOrder : {}", fragmentOrder);
        return fragmentOrderRepository.save(fragmentOrder);
    }

    @Override
    public Optional<FragmentOrder> partialUpdate(FragmentOrder fragmentOrder) {
        log.debug("Request to partially update FragmentOrder : {}", fragmentOrder);

        return fragmentOrderRepository
            .findById(fragmentOrder.getId())
            .map(existingFragmentOrder -> {
                if (fragmentOrder.getOrder() != null) {
                    existingFragmentOrder.setOrder(fragmentOrder.getOrder());
                }

                return existingFragmentOrder;
            })
            .map(fragmentOrderRepository::save);
    }

    @Override
    public List<FragmentOrder> findAll() {
        log.debug("Request to get all FragmentOrders");
        return fragmentOrderRepository.findAll();
    }

    @Override
    public Optional<FragmentOrder> findOne(String id) {
        log.debug("Request to get FragmentOrder : {}", id);
        return fragmentOrderRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete FragmentOrder : {}", id);
        fragmentOrderRepository.deleteById(id);
    }
}
