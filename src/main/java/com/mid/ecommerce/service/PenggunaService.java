package com.mid.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mid.ecommerce.entity.Pengguna;
import com.mid.ecommerce.exception.BadRequestException;
import com.mid.ecommerce.exception.ResourceNotFoundException;
import com.mid.ecommerce.repository.PenggunaRepository;

@Service
public class PenggunaService {
	
    @Autowired
    private PenggunaRepository penggunaRepository;
    
    public List<Pengguna> findAll() {
        return penggunaRepository.findAll();
    }
    
    public Pengguna findById(String id) {
        return penggunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pengguna dengan id " + id + " tidak ditemukan"));
    }
    
    public Pengguna create(Pengguna pengguna) {
    	if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestException("Username harus diisi");
        }
        
        if (penggunaRepository.existsById(pengguna.getId())) {
            throw new BadRequestException("Username " + pengguna.getId() + " sudah terdaftar");
        }
        
        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestException("Email harus diisi");
        }
        
        if (penggunaRepository.existsByEmail(pengguna.getEmail())) {
            throw new BadRequestException("Email " + pengguna.getEmail() + " sudah terdaftar");
        }
        
        pengguna.setIsAktif(true);
        return penggunaRepository.save(pengguna);
    }
    
    public Pengguna edit(Pengguna pengguna) {
        if (!StringUtils.hasText(pengguna.getId())) {
            throw new BadRequestException("Username harus diisi");
        }
        
        if (!StringUtils.hasText(pengguna.getEmail())) {
            throw new BadRequestException("Email harus diisi");
        }
        
        return penggunaRepository.save(pengguna);
    }
    
    public void deleteById(String id) {
        penggunaRepository.deleteById(id);
    }
}
