package com.kimngan.ComesticAdmin.services;

import com.kimngan.ComesticAdmin.entity.DanhMuc;
import com.kimngan.ComesticAdmin.repository.DanhMucRepository;
import com.kimngan.ComesticAdmin.services.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DanhMucServiceImpl implements DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Override
    public List<DanhMuc> getAll() {
        return danhMucRepository.findAll();
    }

    @Override
    public Boolean create(DanhMuc danhMuc) {
        try {
            danhMucRepository.save(danhMuc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public DanhMuc findById(Integer maDanhMuc) {
        Optional<DanhMuc> optionalDanhMuc = danhMucRepository.findById(maDanhMuc);
        return optionalDanhMuc.orElse(null); // Trả về null nếu không tìm thấy danh mục
    }

    @Override
    public Boolean delete(Integer maDanhMuc) {
        try {
            danhMucRepository.deleteById(maDanhMuc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
