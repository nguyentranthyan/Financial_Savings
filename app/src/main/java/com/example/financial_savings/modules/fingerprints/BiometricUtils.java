package com.example.financial_savings.modules.fingerprints;

import android.content.Context;
import android.os.Build;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

/*https://viblo.asia/p/5-buoc-tich-hop-biometric-authentication-vao-ung-dung-android-07LKXNnElV4*/
public class BiometricUtils {

    /*Kiểm tra cảm biến vân tay đã được kích hoạt hay chưa.*/
    public static boolean isBiometricPromptEnabled() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P);
    }

    /*ĐiềukiệnI: Kiểm tra xem phiên bản Android trong thiết bị có lớn hơn không
     * Marshmallow, vì xác thực dấu vân tay chỉ được hỗ trợ từ Android 6.0.
     * Lưu ý: Nếu minSdkversion của dự án của bạn là 23 hoặc cao hơn,
     * sau đó bạn sẽ không cần phải thực hiện kiểm tra này.
     */
    public static boolean isSdkVersionSupported() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    /* Điều kiện II: Kiểm tra xem thiết bị có cảm biến vân tay không.
     * Lưu ý: Nếu bạn đã đánh dấu android.hardware.fingerprint là thứ gì đó
     * ứng dụng của bạn yêu cầu (android: required = "true"), sau đó bạn không cần
     * để thực hiện kiểm tra này.
     */
    public static boolean isHardwareSupported(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        return fingerprintManager.isHardwareDetected();
    }

    /* Điều kiện III: Xác thực vân tay có thể được khớp với
     * dấu vân tay đã đăng ký của người dùng. Vì vậy, chúng tôi cần phải thực hiện kiểm tra này
     * để kích hoạt xác thực vân tay
     */
    public static boolean isFingerprintAvailable(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        return fingerprintManager.hasEnrolledFingerprints();
    }

}
