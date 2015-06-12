package com.example.qrcode;


import java.io.IOException;
import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	String result;
	static String NameSpace = "http://tempuri.org/";
	static String URL = "http://60.30.135.94:838/PdaTransfer.svc";
	static String SOAP_ACTION = "http://tempuri.org/IPdaTransfer/";
	static String MethodName = "Register";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button saomiao = (Button) findViewById(R.id.btn);
		saomiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {

					@Override
					
					
					public void run() {
						
//						CallWebService();
//						String result = encrypt("{\"EnterpriseID\":\"RX\",\"StationID\":\"900000\",\"PDAID\":\"869573010995351\",\"MD5\":\"\",\"Version\":\"荣信V1.793\",\"DefaultLogic\":false,\"Compress\":true,\"FileType\":0}");
						String requestStr = AESEncrypt.encrypt("{\"EnterpriseID\":\"RX\",\"StationID\":\"900000\",\"PDAID\":\"869573010995351\",\"MD5\":\"\",\"Version\":\"荣信V1.793\",\"DefaultLogic\":false,\"Compress\":true,\"FileType\":0}");
						String result = register(requestStr);
						Log.e("aes", result);
					}
				}).start();

				// JSONObject params = new JSONObject();
				//
				// try {
				// params.put("EnterpriseID", "RX");
				// params.put("StationID", "900000");
				// params.put("PDAID", "869573010995351");
				// params.put("MD5", "");
				// params.put("Version", "xiaomi V1.793");
				// params.put("DefaultLogic", false);
				// params.put("Compress", true);
				// params.put("FileType", 0);
				// result =
				// HttpUtil.postRequst("http://60.30.135.94:838/PdaTransfer.svc/Register",
				// params);
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				//
				// // String result = HttpUtil.submitPostData(params, "UTF-8",
				// "http://tempuri.org/IPdaTransfer/Register");
				// if (!TextUtils.isEmpty(result)) {
				// Log.e("result", result);
				// }else {
				// Log.e("result", "result is null");
				// }
				// // Intent intent = new Intent(MainActivity.this,
				// MipcaActivityCapture.class);
				// // startActivity(intent);
			}
		});
	}
	
	
	private static String key = "Guz(%&hj7x89H$yuBI0456FtmaT5&fvH";
	private static String iv = "E4ghj*Ghg7!rNIfb";

	/**
	 * 加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String data) {
		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(Charset
					.forName("ASCII")), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes(Charset
					.forName("ASCII")));

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));

			return Base64.encodeToString(encrypted, Base64.NO_WRAP);

		} catch (Exception e) {
			return "";
		}
	}
	
	public String register(String registerInfo) {
		StringBuilder responseStr = new StringBuilder();
		String propertyName = "RegisterResult";
		String methodName = "Register";
		String action = SOAP_ACTION + methodName;
		PropertyInfo requestProperty = new PropertyInfo();
		requestProperty.setName("regsiterInfo");
		requestProperty.setValue(registerInfo);
		PropertyInfo[] params = { requestProperty };
		boolean flag = getRequesData(methodName, action, params, propertyName,
				responseStr);
		String result = "";
		result = responseStr.toString();
		return result;
	}

	private boolean getRequesData(String methodName, String soap_action,
			PropertyInfo[] proInfo, String responsePropetryName,
			StringBuilder response) {
		boolean flag = false;
		SoapObject soapObject = new SoapObject(NameSpace, methodName);
		// 添加参数
		if (proInfo != null) {
			for (int i = 0; i < proInfo.length; i++) {
				soapObject.addProperty(proInfo[i]);
			}
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		HttpTransportSE trans = new HttpTransportSE(URL);
		trans.debug = true;
		try {
			trans.call(soap_action, envelope);
			flag = true;
		} catch (HttpResponseException e) {
			response.append("网络或服务端异常");
		} catch (IOException e) {
			response.append("网络或服务端异常");
		} catch ( Exception e) {
			e.printStackTrace();
		}
		try {
			SoapObject soapObj = null;
			Object objResponse=envelope.getResponse();
			if (flag == true && objResponse!= null) {
				soapObj = (SoapObject) envelope.bodyIn;
				Object obj = soapObj.getProperty(responsePropetryName);
				response.append(obj.toString());
			}
		} catch (Exception e) {
			flag = false;
			response.append(e.getMessage());
		}
		return flag;
	}
	
	
	public void CallWebService() {
		// 指定的命名空间和调用的方法名
		SoapObject request = new SoapObject(NameSpace, MethodName);
		// 设置需调用接口需要传入的参数
		request.addProperty("Name", "regsiterInfo");
		request.addProperty("Value", "I13GX8DmhxDrlXMqklSHDiVoPy1blUXxnPnKnvn2ksgph9sUvuKTbmD6vcwAWF1+g7meMsuON/WN5mndWfABpHp3Ee+bGsBt0z6xnQqOeBldMFKa3aFpHN7UEscL3FjjXZ8RgdFFUrravYzjIhy1ztxeTXvKV0ralNQNtDJCkm9Hq8shPcbGT1eR7ckdyohnWrncg9+scFgc8/8z+qihgA==");
		
		//		request.addProperty("PDAID", "869573010995351");
//		request.addProperty("MD5", "");
//		request.addProperty("Version", "xiaomi V1.793");
//		request.addProperty("DefaultLogic", false);
//		request.addProperty("Compress", true);
//		request.addProperty("FileType", 0);
		// 生成调用方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		// 下面这两句是一样的作用，写一句就行了
		envelope.bodyOut = request;
		envelope.setOutputSoapObject(request);
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		HttpTransportSE transport = new HttpTransportSE(URL);
		try {
			// 调用
			transport.call(SOAP_ACTION, envelope);
		} catch (Exception e) {
			e.printStackTrace();
		} // 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		if (null == object) {
			return;
		} // 获取返回的结果
		String result = object.getProperty(0).toString();
		
		Log.e("result", "aaaaaaaaaa");
		Log.e("result", result);
		System.out.println(result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
