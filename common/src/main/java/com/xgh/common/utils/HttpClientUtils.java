package com.xgh.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * HttpClientUtils
 */
public class HttpClientUtils {
	/**
	 * 日志工具
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(HttpClientUtils.class);
	/**
	 * 5分钟
	 */
	public static final int MINUTE_FIVE = 300000;

	/**
	 * 10分钟
	 */
	public static final int MINUTE_TEN = 600000;

	/**
	 * HttpClient
	 */
	private static final HttpClient client = getInstance();

	/**
	 * 让Httpclient支持https
	 * 
	 * @return HttpClient
	 */
	private static HttpClient getInstance() {
		X509TrustManager x509mgr = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string) {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.SSL);
			sslContext.init(null, new TrustManager[] { x509mgr }, null);
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			logger.error("error to init httpclient", e);
		}
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslContext, NoopHostnameVerifier.INSTANCE);
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setSSLSocketFactory(sslsf);
		httpClientBuilder.setMaxConnPerRoute(40); // 客户端总并行链接最大数
		httpClientBuilder.setMaxConnTotal(400);// 每个主机的最大并行链接数
		return httpClientBuilder.build();
	}

	public static final RequestConfig getDefaultTimeOutConfig() {
		return getTimeOutConfig(30000, 15000);
	}

	private static final RequestConfig getTimeOutConfig(int socketTimeout,
			int connectionTimeout) {
		return RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectionTimeout).build();
	}

	/**
	 * Get方法查询
	 */
	public static String getMethodGetResponse(String address) throws Exception {
		return getMethodGetResponse(address, getDefaultTimeOutConfig());
	}

	/**
	 * Post方法查询
	 */
	public static String getMethodPostResponse(String address,
			HttpEntity paramEntity) throws Exception {
		RequestConfig config = getDefaultTimeOutConfig();
		return getMethodPostResponse(address, paramEntity, config);
	}

	/**
	 * 自定义超时的Get方法查询
	 */
	public static String getMethodGetResponse(String address,
			int connectionTimeout, int socketTimeout) throws Exception {
		return getMethodGetResponse(address,
				getTimeOutConfig(socketTimeout, connectionTimeout));
	}

	/**
	 * 自定义超时的Post方法
	 */
	public static String getMethodPostResponse(String address,
			HttpEntity paramEntity, int connectionTimeout, int socketTimeout)
			throws Exception {
		RequestConfig config = getTimeOutConfig(socketTimeout,
				connectionTimeout);
		return getMethodPostResponse(address, paramEntity, config);
	}

	/**
	 * Post Entity
	 */
	public static byte[] getMethodPostBytes(String address,
			HttpEntity paramEntity) throws Exception {
		return getMethodPostContent(address, paramEntity,
				getDefaultTimeOutConfig());
	}

	/**
	 * HttpClient get方法请求返回Entity
	 */
	public static String getMethodGetContent(String address) throws Exception {
		byte[] b = getMethodGetContent(address, getDefaultTimeOutConfig());
		return new String(b,"utf-8");
	}

	/**
	 * HttpClient Get方法请求数据
	 */
	private static String getMethodGetResponse(String address,
			RequestConfig config) throws Exception {
//		logger.info("Start Access Address(" + address + ") With Get Request");
		byte[] result = getMethodGetContent(address, config);
		return new String(result, "utf-8");
	}

	/**
	 * HttpClient Post方法请求数据
	 */
	private static String getMethodPostResponse(String address,
			HttpEntity paramEntity, RequestConfig config) throws Exception {
//		logger.info("Begin Access Url(" + address + ") By Post");
		byte[] content = getMethodPostContent(address, paramEntity, config);
		String result = new String(content, "utf-8");
//		logger.info("Response -> " + result);
		return result;

	}

	/**
	 * HttpClient get方法请求返回Entity
	 * 
	 * @param address  地址
	 * @param headerParams 请求头
	 * @return
	 * @throws Exception
	 */
    public static String getMethodGetContent(String address, Map<String, String> headerParams) throws Exception {
        HttpGet get = new HttpGet(address);
        for (Map.Entry<String, String> entry : headerParams.entrySet()) {
            get.setHeader(entry.getKey(), entry.getValue());
        }
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                int code = response.getStatusLine().getStatusCode();
                throw new RuntimeException("HttpGet Access Fail , Return Code(" + code + ")");
            }
            response.getEntity().getContent();
            byte[] bytes = convertEntityToBytes(response.getEntity());
            return new String(bytes, "utf-8");
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
        }
    }
    
    /**
     * 
     * HttpClient Post方法请求返回Entity
     * @param address
     * @param paramEntity
     * @param headerParams
     * @return
     * @throws Exception
     */
    public static String getMethodPostContent(String address, HttpEntity paramEntity,Map<String, String> headerParams) throws Exception {
        HttpPost post = new HttpPost(address);
        try {
            if (paramEntity != null) {
                post.setEntity(paramEntity);
            }
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                int code = response.getStatusLine().getStatusCode();
                throw new RuntimeException("HttpPost Request Access Fail Return Code(" + code + ")");
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new RuntimeException("HttpPost Request Access Fail Response Entity Is null");
            }
            byte[] bytes = convertEntityToBytes(response.getEntity());
            return new String(bytes, "utf-8");
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }
    
	/**
	 * HttpClient get方法请求返回Entity
	 */
	private static byte[] getMethodGetContent(String address,
			RequestConfig config) throws Exception {
		HttpGet get = new HttpGet(address);
		try {
//			logger.info("Start Access Address(" + address + ") With Get Request");
			get.setConfig(config);
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				int code = response.getStatusLine().getStatusCode();
				throw new RuntimeException("HttpGet Access Fail , Return Code("
						+ code + ")");
			}
			response.getEntity().getContent();
			return convertEntityToBytes(response.getEntity());
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}
	}

	/**
	 * Post Entity
	 */
	private static byte[] getMethodPostContent(String address,
			HttpEntity paramEntity, RequestConfig config) throws Exception {
		HttpPost post = new HttpPost(address);
		try {
			if (paramEntity != null) {
				post.setEntity(paramEntity);
			}
			post.setConfig(config);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				int code = response.getStatusLine().getStatusCode();
				throw new RuntimeException(
						"HttpPost Request Access Fail Return Code(" + code
								+ ")");
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				throw new RuntimeException(
						"HttpPost Request Access Fail Response Entity Is null");
			}
			return convertEntityToBytes(entity);
		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}
	}

	/**
	 * 转化返回为byte数组
	 * 
	 * @param entity
	 * @return byte[]
	 * @throws Exception
	 */
	private static byte[] convertEntityToBytes(HttpEntity entity)
			throws Exception {
		InputStream inputStream = null;
		try {
			if (entity == null || entity.getContent() == null) {
				throw new RuntimeException("Response Entity Is null");
			}
			inputStream = entity.getContent();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			return out.toByteArray();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
