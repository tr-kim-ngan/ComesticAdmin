package com.kimngan.ComesticAdmin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kimngan.ComesticAdmin.entity.NguoiDung;
import com.kimngan.ComesticAdmin.repository.NguoiDungRepository;
@Service
public class NguoiDungServiceImpl implements NguoiDungService{
	@Autowired
	private NguoiDungRepository nguoiDungRepository;
	@Override
	public NguoiDung findByTenNguoiDung(String tenNguoiDung) {
		// TODO Auto-generated method stub
		return nguoiDungRepository.findByTenNguoiDung(tenNguoiDung);
	}

}
