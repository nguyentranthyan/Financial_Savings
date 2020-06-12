package com.example.financial_savings.modules.fingerprints;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.mains.HomeActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FingerprintModule {

    public static void handlingFingerprint(final FragmentActivity fragmentActivity) {
        Executor executor = Executors.newSingleThreadExecutor();

        final BiometricPrompt biometricPrompt = new BiometricPrompt(fragmentActivity, executor,
                new BiometricPrompt.AuthenticationCallback() {

                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        String TAG = null;
                        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                            // user clicked negative button
                            Log.d(TAG, "Close");
                        } else {
                            TAG = null;
                            Log.d(TAG, "Error");
                        }
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        String TAG = null;
                        Log.d(TAG, "Success");
                        Intent intent = new Intent(fragmentActivity, HomeActivity.class);
                        fragmentActivity.startActivity(intent);
                        fragmentActivity.finish();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        String TAG = null;
                        Log.d(TAG, "Fail");
                    }

                });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(fragmentActivity.getResources().getString(R.string.title_fingerprint))
                .setSubtitle(fragmentActivity.getResources().getString(R.string.subtitle_fingerprint))
                .setDescription(fragmentActivity.getResources().getString(R.string.desc_fingerprint))
                .setNegativeButtonText(fragmentActivity.getResources().getString(R.string.use_pass))
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}
