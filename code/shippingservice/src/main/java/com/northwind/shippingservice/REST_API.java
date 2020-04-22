package com.northwind.shippingservice;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;


public interface REST_API {



    //packingslips end-points
    public static final String PACKINGSLIPS = "/packingslips";
    public static final String PACKINGSLIP_BY_ID = "/{id}";
    public static final String PACKINGSLIP_BY_SHIPNAME = "shipName";
    public static final String PACKINGSLIP_BY_SHIPCOUNTRY = "shipCountry";
    public static final String PACKINGSLIP_BY_SHIPPOSTALCODE = "shipPostalCode";
    public  static final String PACKINGSLIP_BY_ORDERID ="orderID";


    //packingslipdetails end-points

    public static final String PACKINGSLIPDETAILS = "/packingslips/{packingSlipID}/packingslipdetails";
    public static final String PACKINGSLIPDETAILS_BY_ID = "/{id}";

    //shippers end-points

    public static final String SHIPPERS = "/shippers";
    public static final String SHIPPERS_BY_ID = "/{id}";
    public static final String SHIPPERS_BY_COMPANYNAME = "companyName";

    //shippingrates end-points


    public static final String SHIPPINGRATES = "/shipping/rates";
    public static final String SHIPPINGRATES_BY_ID = "/{id}";
    public static final String SHIPPINGRATES_BY_COUNTRY = "country";
    public static final String SHIPPINGRATE_BY_COUNTRY_FLATRATE = "country";

}
