package com.example.demo.service.approle;

import com.example.demo.model.AppRole;
import com.example.demo.repository.AppRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppRoleService implements IAppRoleService {
    @Autowired
    private AppRoleRepository roleRepository;

    @Override
    public Iterable<AppRole> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<AppRole> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public AppRole save(AppRole appRole) {
        return roleRepository.save(appRole);
    }
}
