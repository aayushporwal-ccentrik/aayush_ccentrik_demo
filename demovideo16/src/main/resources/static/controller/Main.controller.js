	
		sap.ui.define(
		    [
		        "sap/ui/core/mvc/Controller",
		        "jquery.sap.global",
		        "aayush/util/service",
		        "sap/m/MessageBox"
		    ],
		    function (Controller, jQuery, service, MessageBox) {
		        "use strict";

		        return Controller.extend("aayush.controller.Main", {

		            onInit: function () {
		                var oModel = new sap.ui.model.json.JSONModel();

		                oModel.setData({
		                    "postPayload": {
		                        "id": "",
		                        "companyName": "",
		                        "firstName": "",
		                        "lastName": "",
		                        "website": "",
		                        "email": "",
		                        "status": "A",
		                        "gstNumber": ""
		                    },
		                    "vendor": {}
		                });

		                // Set model to entire app
		                this.getView().setModel(oModel);
		            },

		            onSave: function () {
		                var oModel = this.getView().getModel();
		                var payload = oModel.getProperty("/postPayload");

		                service.callService("/vendor", "POST", payload)
		                    .then(function () {
		                        MessageBox.success("Saved Successfully");
		                    })
		                    .catch(function () {
		                        MessageBox.error("Post failed");
		                    });
		            },

		            onLoadData: function () {
		                var that = this;

		                service.callService("/vendors", "GET", {})
		                    .then(function (data) {

		                        var oTable = that.getView().byId("idTable");
		                        var oModel = that.getView().getModel();

		                        oModel.setProperty("/vendors", data);

		                        oTable.bindRows("/vendors");
		                    })
		                    .catch(function (err) {
		                        console.error(err);
		                    });
		            }

		        });
		    }
		);