package com.demo.buisnesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Component;

import com.demo.entity.vendor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class vendorOperation {
	



	    private Connection conn;

	    @PostConstruct
	    public void startConnection() {
	        String vcap_service = System.getenv("VCAP_SERVICES");

	        System.out.println("=== VCAP_SERVICES raw value ===");
	        System.out.println(vcap_service);

	        if (vcap_service != null && vcap_service.length() > 0) {
	            try {
	                ObjectMapper mapper = new ObjectMapper();
	                JsonNode root = mapper.readTree(vcap_service);

	                JsonNode credentials = root.path("hana").get(0).path("credentials");

	                String host = credentials.path("host").asText();
	                String port = credentials.path("port").asText();
	                String user = credentials.path("user").asText();
	                String password = credentials.path("password").asText();

	                String url = credentials.path("url").asText();

	                System.out.println("=== Attempting HANA connection ===");
	                System.out.println("URL: " + url);
	                System.out.println("User: " + user);

	                this.conn = DriverManager.getConnection(url, user, password);

	                System.out.println("=== HANA connection successful ===");

	            } catch (Exception e) {
	                System.err.println("=== Failed to initialize HANA connection ===");
	                e.printStackTrace();
	            }
	        } else {
	            System.err.println("=== VCAP_SERVICES is null or empty ===");
	        }
	    }

	    public List<vendor> getAllVendor() {
	        List<vendor> vendorList = new ArrayList<>();

	        if (conn == null) {
	            System.err.println("=== getAllVendor called but conn is null ===");
	            return vendorList;
	        }

	        // VERSION 1 — Use this if your HANA columns are UPPERCASE (ID, FIRSTNAME, LASTNAME...)
	        String sql = "SELECT TOP 1000 "
	                + "\"ID\", \"FIRSTNAME\", \"LASTNAME\", \"COMPANYNAME\", "
	                + "\"WEBSITE\", \"EMAIL\", \"VSTATUS\", \"GSTNUMBER\" "
	                + "FROM \"190AB20F7D9B412EACB72C9ED99DB2D3\".\"VENDOR\"";

	        try (PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                vendorList.add(new vendor(
	                    rs.getString("ID"),
	                    rs.getString("FIRSTNAME"),
	                    rs.getString("LASTNAME"),
	                    rs.getString("COMPANYNAME"),
	                    rs.getString("WEBSITE"),
	                    rs.getString("EMAIL"),
	                    rs.getString("VSTATUS"),
	                    rs.getString("GSTNUMBER")
	                ));
	            }

	            System.out.println("=== Vendors fetched: " + vendorList.size() + " ===");

	        } catch (SQLException e) {
	            System.err.println("=== SQLException in getAllVendor ===");
	            e.printStackTrace();
	        }

	        return vendorList;
	    }

	    @PreDestroy
	    public void endConnection() {
	        try {
	            if (conn != null) conn.close();
	            System.out.println("=== HANA connection closed ===");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


