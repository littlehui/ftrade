//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.u17173.treasurebox.utils.url;

import com.u17173.treasurebox.utils.base.json.JsonUtil;
import com.u17173.treasurebox.utils.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XHttpRequest<P> {
    Logger logger = Logger.getLogger(XHttpRequest.class);
    protected Map<String, Object> headers = new HashMap();
    protected String charSet = "utf-8";
    protected String urlParams = "";
    protected P param;
    protected URL url;
    protected HttpURLConnection httpConnection;
    protected String getUrl;
    private ParamGetterValue<P> pParamGetterValue;
    protected String method = "GET";
    private String postJson = "";

    public P getParam() {
        return this.param;
    }

    public String getMethod() {
        return this.method;
    }

    public String getCharSet() {
        return this.charSet;
    }

    public HttpURLConnection getHttpConnection() {
        return this.httpConnection;
    }

    public String getPostJson() {
        return this.postJson;
    }

    public void setPostJson(String postJson) {
        this.postJson = postJson;
    }

    public String getUrlParams() {
        return this.urlParams;
    }

    public void setUrlParams(String urlParams) {
        this.urlParams = urlParams;
    }

    public String getGetUrl() {
        return this.getUrl;
    }

    public void setGetUrl(String getUrl) {
        this.getUrl = getUrl;
    }

    protected Map<String, Object> getDefaultHeaders() {
        HashMap defaultHeaders = new HashMap();
        defaultHeaders.put("Accept", "*/*");
        defaultHeaders.put("Connection", "Keep-Alive");
        defaultHeaders.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
        defaultHeaders.put("Accept-Charset", "utf-8");
        defaultHeaders.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        this.headers = defaultHeaders;
        return this.headers;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void addHeader(String headerKey, String headerValue) {
        if (this.headers == null) {
            this.headers = this.getDefaultHeaders();
        }

        this.headers.put(headerKey, headerValue);
    }

    protected void initConnection() throws IOException {
        if ("POST".equals(this.method)) {
            this.url = new URL(this.getUrl);
            if ("".equals(this.postJson) || this.postJson == null) {
                this.postJson = JsonUtil.toJson(this.param);
            }
        } else {
            this.url = new URL(this.getUrl + this.urlParams);
        }

        this.httpConnection = (HttpURLConnection) this.url.openConnection();
        Iterator var1 = this.headers.keySet().iterator();

        while (var1.hasNext()) {
            String keyset = (String) var1.next();
            this.httpConnection.setRequestProperty(keyset, this.headers.get(keyset).toString());
        }

        this.httpConnection.setRequestMethod(this.method);
        this.httpConnection.setUseCaches(false);
        if ("POST".equals(this.method)) {
            this.httpConnection.setDoOutput(true);
        } else {
            this.httpConnection.setDoOutput(true);
        }

        this.httpConnection.setDoInput(true);
    }

    public XHttpRequest(String url, P param, String charSet) {
        this.getUrl = url;
        this.param = param;
        this.pParamGetterValue = new ParamGetterValue(param);
        this.headers = this.getDefaultHeaders();
        if (!this.charSet.equals(charSet)) {
            this.headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charSet);
            this.headers.put("Accept-Charset", charSet);
        }

        this.charSet = charSet;
    }

    public static String urlEncode(String source, String encode) {
        try {
            String result = URLEncoder.encode(source, encode);
            return result;
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return "0";
        }
    }

    public String doRequest() {
        this.toUrlParams();
        OutputStreamWriter out = null;

        String var4;
        try {
            this.initConnection();
            out = new OutputStreamWriter(this.httpConnection.getOutputStream(), this.charSet);
            if (this.method.equals("POST")) {
                out.write(this.postJson);
            }

            out.flush();
            InputStream urlStream = this.httpConnection.getInputStream();
            this.logger.debug("连接状态:" + urlStream.available());
            String e = IOUtils.in2Str(urlStream, this.charSet);
            var4 = e;
        } catch (Exception var13) {
            var13.printStackTrace();
            throw new HttpGetterException(var13);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception var12) {
                    var12.printStackTrace();
                    throw new HttpGetterException(var12);
                }
            }

            this.httpConnection.disconnect();
        }

        return var4;
    }

    protected void toUrlParams() {
        String urlValue = this.pParamGetterValue.getGetterUrlValue();
        if (urlValue != null) {
            this.urlParams = urlValue;
        }

    }

    public static void main1(String[] args) {
        HashMap param = new HashMap();
        param.put("commodityCode", "151000000238");
        XHttpRequest XHttpRequest = new XHttpRequest("http://mai.17173.com/commodity/materil/detail.html", param, "utf-8");
        String result = XHttpRequest.doRequest();
        System.out.println(result);
    }

    public static void main(String[] args) {
        HashMap param = new HashMap();

        try {
            new FileImageInputStream(new File("C:\\Users\\Administrator\\Documents\\美图图库\\示例图片_03.jpg"));
            XHttpRequest XHttpRequest = new XHttpRequest("http://netmine.vicp.net:24869/upload", param, "utf-8");
            XHttpRequest.addHeader("Content-Type", "jpeg");
            XHttpRequest.setMethod("POST");
            String result = XHttpRequest.doRequest();
            System.out.println(result);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }
}
