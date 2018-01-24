package com.fbee.modules.utils;

import com.fbee.modules.core.Log;
import com.fbee.modules.core.config.Global;
import com.fbee.modules.form.CertCheckForm;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ZSCXQuery
{
	private static String verfifyCodeUrl = "http://www.12333sh.gov.cn/wsbs/zypxjd/jnjd/jdcx/zscx/Bmblist2.jsp?dt=" + Math.random();
	private static String getResultPostUrl = "http://www.12333sh.gov.cn/wsbs/zypxjd/jnjd/jdcx/zscx/zscx_2016.jsp";
	private static Map<Long, Object> map = new HashMap();

	public static void getVerifyingCode(HttpClient client)
	{
		HttpGet getVerifyCode = new HttpGet(verfifyCodeUrl);
		FileOutputStream fileOutputStream = null;
		try
		{
			HttpResponse response = client.execute(getVerifyCode);

			String config = Global.getConfig("website_base_path");
			String purl = "/captcha";

			File file = new File(config + purl + "/verifyCode.jpg");
			if (!file.exists()) {
				file.mkdirs();
			}
			fileOutputStream = new FileOutputStream(file);
			response.getEntity().writeTo(fileOutputStream); return;
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fileOutputStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static String getImageRequest()
	{
		HttpGet getLoginPage = new HttpGet(getResultPostUrl);
		HttpClient client = HttpClients.createDefault();
		try
		{
			client.execute(getLoginPage);
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		getVerifyingCode(client);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String clientKey = df.format(new Date());
		long clientKeys = Long.parseLong(clientKey);
		map.put(Long.valueOf(clientKeys), client);
		clearMap();
		return clientKey;
	}

	public static void clearMap()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String claerKey = df.format(new Date());
		long claerKeys = Long.parseLong(claerKey);
		if ((map != null) && (map.size() > 0)) {
			synchronized (map)
			{
				Iterator<Map.Entry<Long, Object>> t = map.entrySet().iterator();
				List rmlist = new ArrayList();
				while (t.hasNext())
				{
					Map.Entry<Long, Object> entry = (Map.Entry)t.next();
					Long key = (Long)entry.getKey();
					if (claerKeys - key.longValue() > 300L) {
						rmlist.add(key);
					}
				}
				for (int i = 0; i < rmlist.size(); i++)
				{
					map.remove(rmlist.get(i));
					System.out.println(rmlist.get(i) + "======clearMap============");
				}
			}
		}
		System.out.println(map);
	}

	public String getHTMLResult(CertCheckForm certCheckForm, Object clientkey)
			throws Exception
	{
		if (null == map.get(Long.valueOf(clientkey.toString()))) {
			return null;
		}
		HttpClient client = (HttpClient)map.get(Long.valueOf(clientkey.toString()));
		Log.info("++++++++++++" + client);
		List<NameValuePair> postData = new ArrayList();
		postData.add(new BasicNameValuePair("idcard", certCheckForm.getQtzjhm()));
		postData.add(new BasicNameValuePair("qtzjhm", certCheckForm.getIdcard()));
		postData.add(new BasicNameValuePair("zshm", certCheckForm.getZshm()));
		postData.add(new BasicNameValuePair("action", "query"));
		postData.add(new BasicNameValuePair("sj_mima1", certCheckForm.getSj_mima1()));

		HttpPost post = new HttpPost(getResultPostUrl);
		post.setEntity(new UrlEncodedFormEntity(postData));
		HttpResponse response = null;

		//HttpHost host = new HttpHost("60.191.134.165",9999, "http");
		//RequestConfig config = RequestConfig.custom().setProxy(host).build();
		//post.setConfig(config);

		response = client.execute(post);
		String rawHtml = EntityUtils.toString(response.getEntity(), "utf-8");

		map.remove(Long.valueOf(clientkey.toString()));
		return rawHtml;
	}
}
