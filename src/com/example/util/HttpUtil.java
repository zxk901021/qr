package com.example.util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


import android.util.Log;

@SuppressWarnings("deprecation")
public class HttpUtil {
	public static HttpClient httpClient = new DefaultHttpClient();

	public interface onDataCallBackListener {
		public void getdata(String data);
	}


	public static String postRequst(final String url, final JSONObject rawParams)
			throws Exception {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);
		final HttpClient client = new DefaultHttpClient(httpParams);

		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						HttpGet request = new HttpGet(url);
						request.setHeader("Accept", "application/json");

						request.setHeader("Content-type", "application/json;charset=utf-8");
						/**
						 * 
						 */
						StringEntity entity = new StringEntity(rawParams.toString());
//						request.setEntity(entity);
						/**
						 * 
						 */
//						List<NameValuePair> params = new ArrayList<NameValuePair>();
//						params.add(new BasicNameValuePair("regsiterInfo",
//								rawParams.toString()));
//						post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
						
						
						HttpResponse httpResponse = client.execute(request);
						Log.e("111", httpResponse.getStatusLine().getStatusCode()+ "");
						HttpEntity responseEntity = httpResponse.getEntity();
						Log.d("WCF", responseEntity.toString());
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							String result = EntityUtils.toString(httpResponse
									.getEntity());
							Log.e("111", result);
							return result;
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}

	public static String post(final String url) throws Exception {

		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						HttpPost post = new HttpPost(url);
						HttpResponse response = new DefaultHttpClient()
								.execute(post);
						if (response.getStatusLine().getStatusCode() == 200) {
							String result = EntityUtils.toString(response
									.getEntity());
							return result;
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}
	public static String submitPostData(Map<String, String> params,
			String encode, String urlStr) {

		byte[] data = getRequestData(params, encode).toString().getBytes();
		URL url;
		
		// 获得请求体
		try {
			url = new URL(urlStr);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setConnectTimeout(3000); // 设置连接超时时间
			httpURLConnection.setDoInput(true); // 打开输入流，以便从服务器获取数据
			httpURLConnection.setDoOutput(true); // 打开输出流，以便向服务器提交数据
			httpURLConnection.setRequestMethod("POST"); // 设置以Post方式提交数据
			httpURLConnection.setUseCaches(false); // 使用Post方式不能使用缓存
			// 设置请求体的类型是文本类型
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 设置请求体的长度
			httpURLConnection.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			// 获得输出流，向服务器写入数据
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(data);

			int response = httpURLConnection.getResponseCode(); // 获得服务器的响应码
			if (response == HttpURLConnection.HTTP_OK) {
				InputStream inptStream = httpURLConnection.getInputStream();
				return dealResponseResult(inptStream); // 处理服务器的响应结果
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static StringBuffer getRequestData(Map<String, String> params,
			String encode) {
		StringBuffer stringBuffer = new StringBuffer(); // 存储封装好的请求体信息
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1); // 删除最后的一个"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}

	public static String dealResponseResult(InputStream inputStream) {
		String resultData = null; // 存储处理结果
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultData = new String(byteArrayOutputStream.toByteArray());
		return resultData;
	}

	
	
}
