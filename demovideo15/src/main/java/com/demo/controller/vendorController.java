package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.buisnesslogic.vendorOperation;
import com.demo.entity.vendor;

@RestController
public class vendorController {
	


		@Autowired
		vendorOperation vo;
		//we are doing get mapping for displaying all vendors.
		@RequestMapping("/vendors")
		public List<vendor> displayAllVendor(){
			return vo.getAllVendor();
		}
		
		public vendorController() {
			// TODO Auto-generated constructor stub
		}

	}



