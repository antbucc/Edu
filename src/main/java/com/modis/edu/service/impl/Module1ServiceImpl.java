package com.modis.edu.service.impl;

import com.modis.edu.domain.Module1;
import com.modis.edu.repository.Module1Repository;
import com.modis.edu.service.Module1Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Module1}.
 */
@Service
public class Module1ServiceImpl implements Module1Service {

    private final Logger log = LoggerFactory.getLogger(Module1ServiceImpl.class);

    private final Module1Repository module1Repository;

    public Module1ServiceImpl(Module1Repository module1Repository) {
        this.module1Repository = module1Repository;
    }

    @Override
    public Module1 save(Module1 module1) {
        log.debug("Request to save Module1 : {}", module1);
        return module1Repository.save(module1);
    }

    @Override
    public Module1 update(Module1 module1) {
        log.debug("Request to update Module1 : {}", module1);
        return module1Repository.save(module1);
    }

    @Override
    public Optional<Module1> partialUpdate(Module1 module1) {
        log.debug("Request to partially update Module1 : {}", module1);

        return module1Repository
            .findById(module1.getId())
            .map(existingModule1 -> {
                if (module1.getTitle() != null) {
                    existingModule1.setTitle(module1.getTitle());
                }
                if (module1.getDescription() != null) {
                    existingModule1.setDescription(module1.getDescription());
                }
                if (module1.getStartDate() != null) {
                    existingModule1.setStartDate(module1.getStartDate());
                }
                if (module1.getEndData() != null) {
                    existingModule1.setEndData(module1.getEndData());
                }
                if (module1.getLevel() != null) {
                    existingModule1.setLevel(module1.getLevel());
                }

                return existingModule1;
            })
            .map(module1Repository::save);
    }

    @Override
    public List<Module1> findAll() {
        log.debug("Request to get all Module1s");
        return module1Repository.findAll();
    }

    public Page<Module1> findAllWithEagerRelationships(Pageable pageable) {
        return module1Repository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<Module1> findOne(String id) {
        log.debug("Request to get Module1 : {}", id);
        return module1Repository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Module1 : {}", id);
        module1Repository.deleteById(id);
    }
}
