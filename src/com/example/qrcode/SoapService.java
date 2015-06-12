package com.example.qrcode;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapService implements ISoapService{
	private static final String NAMESPACE="http://chad.cao";  
    /*方法名*/  
    private static final String METHODNAME="HelloWorld";  
    /*WCF在iis中的调用路径(http://服务器/虚拟目录/服务)*/  
    private static final String URL="http://192.168.2.102/AndroidUseWCF/Service1.svc";  
    /*Namespace/服务接口/方法*/  
    private static final String SOAPACTION="http://chad.cao/IService1/HelloWorld";  
    
    
    
    private String name;  
    public SoapService(String _name){  
        this.name=_name;  
    }  
      
    public SoapObject HelloWorldResult(){  
        SoapObject result=null;  
        SoapObject soapObject=new SoapObject(NAMESPACE, METHODNAME);  
        soapObject.addProperty("_name", name);//传参，记住参数名必须和WCF方法中的参数名一致  
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        envelope.bodyOut=soapObject;  
        envelope.dotNet=true;  
        envelope.setOutputSoapObject(soapObject);  
        HttpTransportSE transportSE=new HttpTransportSE(URL);  
        transportSE.debug=true;//使用调式功能  
        try {  
            transportSE.call(SOAPACTION, envelope);  
            result=(SoapObject) envelope.bodyIn;  
        } catch (Exception e) {  
            String exceptionString=e.toString();  
        }  
        return result;  
    }  
}
