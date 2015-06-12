package com.example.qrcode;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapService implements ISoapService{
	private static final String NAMESPACE="http://chad.cao";  
    /*������*/  
    private static final String METHODNAME="HelloWorld";  
    /*WCF��iis�еĵ���·��(http://������/����Ŀ¼/����)*/  
    private static final String URL="http://192.168.2.102/AndroidUseWCF/Service1.svc";  
    /*Namespace/����ӿ�/����*/  
    private static final String SOAPACTION="http://chad.cao/IService1/HelloWorld";  
    
    
    
    private String name;  
    public SoapService(String _name){  
        this.name=_name;  
    }  
      
    public SoapObject HelloWorldResult(){  
        SoapObject result=null;  
        SoapObject soapObject=new SoapObject(NAMESPACE, METHODNAME);  
        soapObject.addProperty("_name", name);//���Σ���ס�����������WCF�����еĲ�����һ��  
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);  
        envelope.bodyOut=soapObject;  
        envelope.dotNet=true;  
        envelope.setOutputSoapObject(soapObject);  
        HttpTransportSE transportSE=new HttpTransportSE(URL);  
        transportSE.debug=true;//ʹ�õ�ʽ����  
        try {  
            transportSE.call(SOAPACTION, envelope);  
            result=(SoapObject) envelope.bodyIn;  
        } catch (Exception e) {  
            String exceptionString=e.toString();  
        }  
        return result;  
    }  
}
