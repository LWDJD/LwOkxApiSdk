package io.github.lwdjd.okxapisdk.account;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SignatureUtil {

    public static String sign(String timestamp, String method, String requestPath, String body, String secretKey) {
        try {
            // 构建签名字符串
            StringBuilder signStr = new StringBuilder();
            signStr.append(timestamp).append(method).append(requestPath);
            if (body != null && !body.isEmpty()) {
                signStr.append(body);
            }

            // 使用HMAC SHA256进行签名
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secretKeySpec);

            byte[] hash = hmacSha256.doFinal(signStr.toString().getBytes(StandardCharsets.UTF_8));

            // 对签名结果进行Base64编码
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            // 异常处理，实际应用中应根据具体情况进行处理
            throw new RuntimeException("签名过程中发生错误", e);
        }
    }
}
