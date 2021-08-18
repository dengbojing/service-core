package com.yichen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.Base64Decoder;
import com.yichen.major.entity.Account;
import com.yichen.core.param.account.AccountParam;
import com.yichen.major.entity.Message;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dengbojing
 */
public class SpringTest {

    public void testBeanCopy(){
        Account account = new Account();
        AccountParam accountParam = new AccountParam();
    }

    @Test
    public void testLocalDate(){
        String key  = UUID.randomUUID().toString().replace("-","");
        String appkey = UUID.randomUUID().toString().replace("-","");
        LocalDate now = LocalDate.now();
        LocalDate after = LocalDate.of(2019,10, 18);
        System.out.println(key);
        System.out.println(appkey);
        System.out.println(now.compareTo(after));
    }

    @Test
    public void testFile2Byte() throws IOException {
        byte[] b = Files.readAllBytes(Paths.get("C:\\Users\\weigx\\Desktop\\新建文件夹\\1.jpg"));
        System.out.println(Base64.getEncoder().encodeToString(b));
        System.out.println( byte2HexStr(b));
    }

    public static String byte2HexStr(byte[] b)
    {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++)
        {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    @Test
    public void testImage() throws IOException, URISyntaxException, InterruptedException {

        byte[] b = Files.readAllBytes(Paths.get("C:\\Users\\weigx\\Desktop\\归档\\1.jpg"));
        //System.out.println(Base64.getEncoder().encodeToString(b));
        String key = "ff3d3106a97e4e798dfc5926b2c4e54a";
        String secretKey = "9a6d394e7fb8479ea0a92e5caa15c9a5";
        String requestUrl = "http://rsjrxcjcl.weihai.cn/core/api/v1/file";
        Map<String,String> map = new HashMap<>();
        map.put("fileName","1.jpg");
        map.put("fileContent",Base64.getEncoder().encodeToString(b));
        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(50)).build();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(requestUrl))
                .setHeader("appKey",key)
                .setHeader("token",authentHeader(key, secretKey)[0])
                .setHeader("timeSpan",authentHeader(key,secretKey)[1])
                .setHeader("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON.toJSONString(map)))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(httpResponse.statusCode() == 200){
            JSONObject jsonObj = JSON.parseObject(httpResponse.body());
            System.out.println(jsonObj.toJSONString());
            Files.write(Paths.get("C:\\Users\\weigx\\Desktop\\归档\\4323.jpg"),Base64.getDecoder().decode(jsonObj.getString("data")), StandardOpenOption.CREATE);
        }



//        OkHttpClient httpClient = new OkHttpClient.Builder().callTimeout(50, TimeUnit.SECONDS)
//                .connectTimeout(50,TimeUnit.SECONDS)
//                .readTimeout(50,TimeUnit.SECONDS).build();
//
//        RequestBody body = RequestBody.create(MediaType.get("application/json"),JSON.toJSONString(map));
//        Request request = new Request.Builder().url(requestUrl).post(body)
//                .addHeader("appKey",key)
//                .addHeader("token",authentHeader(key,secretKey)[0])
//                .addHeader("timeSpan",authentHeader(key,secretKey)[1]).build();
//
//        okhttp3.Response okResponse = httpClient.newCall(request).execute();
//        checkNotNull(okResponse.body(),"未获取到企业信息!");
//        JSONObject jsonObj = JSON.parseObject(okResponse.body().string());
//        Files.write(Paths.get("C:\\Users\\weigx\\Desktop\\归档\\\\4323.jpg"),Base64.getDecoder().decode(jsonObj.getString("data")), StandardOpenOption.CREATE);
    }

    private static String[] authentHeader(String key,String secretKey){
        String timeSpan = String.valueOf(System.currentTimeMillis() / 1000);
        return new String[] { DigestUtils.md5Hex(key.concat(timeSpan).concat(secretKey)).toUpperCase(), timeSpan };
    }

    public static void main(String...args) throws InterruptedException, URISyntaxException {
        WebSocket webSocket = HttpClient.newHttpClient().newWebSocketBuilder().buildAsync(new URI("ws://localhost:8091/chat/dengbojing"), new WebSocket.Listener() {
            @Override
            public void onOpen(WebSocket webSocket) {
                System.out.println("open");
                WebSocket.Listener.super.onOpen(webSocket);
            }

            @Override
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                System.out.println("message: " + data);
                return WebSocket.Listener.super.onText(webSocket,data,last);
            }

            @Override
            public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {

                System.out.println("close");
                return WebSocket.Listener.super.onClose(webSocket,statusCode,reason);
            }

            @Override
            public void onError(WebSocket webSocket, Throwable error) {
                System.out.println(error.getMessage());
            }
        }).join();
        Message message = new Message();
        message.setContent("client data");
        webSocket.sendText(JSON.toJSONString(message), true);
        TimeUnit.SECONDS.sleep(10);
        webSocket.sendClose(WebSocket.NORMAL_CLOSURE,"ok");


    }

    @Test
    public void test1() throws IOException {
        int base = 0xff;
        int next = 0xd8;
        int pngBase = 0x89;
        int pngNext = 0x50;
        byte[] bs = Files.readAllBytes(Paths.get("D:\\WebChat\\WeChat Files\\wxid_sxelod34kou321\\FileStorage\\Image\\2020-06\\0ce7898a35eb2a7ac9045480419e5902.dat"));
        int firstByte = bs[0] & 0xff;
        int secondByte = bs[1] & 0xff;
        int key = base ^ firstByte;
        String suffix = ".jpg";
        if((pngBase ^ firstByte) == (pngNext ^ secondByte)){
            key = pngBase ^ firstByte;
            suffix = ".png";
        }
        try(OutputStream os = Files.newOutputStream(Paths.get("D:\\123"+suffix),StandardOpenOption.CREATE)){
            for(byte b : bs){
                os.write(b ^ key);
            }
        }
    }

}
