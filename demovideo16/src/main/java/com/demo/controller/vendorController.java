  package com.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.buisnesslogic.vendorOperation;
import com.demo.entity.Vendor;

@RestController
public class vendorController {
	


		@Autowired
		vendorOperation vo;
		//we are doing get mapping for displaying all vendors.
		@GetMapping("/vendors")
		public List<Vendor> displayAllVendor(){
			return vo.getAllVendor();
		}
		
		public vendorController() {
			// TODO Auto-generated constructor stub
		}
		
		@GetMapping("/vendors/{id}")
	    public Vendor getVendorById(@PathVariable("id") String id) throws SQLException {
	        return vo.getSingleVendor(id);
		}

		@PostMapping("/vendors")
	    public Vendor createNewVendor(@RequestBody Vendor payload) throws SQLException {
	        return vo.createVendor(payload);
	    }
	}


	



