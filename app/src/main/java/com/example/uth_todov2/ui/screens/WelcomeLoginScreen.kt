package com.example.uth_todov2.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth_todov2.ui.components.TodoLogo
import com.example.uth_todov2.ui.theme.PrimaryBlue
import com.example.uth_todov2.ui.theme.SecondaryText

@Composable
fun WelcomeLoginScreen(
    onNavigateBack: () -> Unit,
    onContinueWithoutAccount: () -> Unit,
    onGoogleLogin: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Top bar
                IconButton(onClick = onNavigateBack, modifier = Modifier.size(24.dp)) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Quay lại", tint = Color(0xFF202124))
                }

                Spacer(modifier = Modifier.height(24.dp))

                TodoLogo(showSubtitle = false)

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Chào mừng đến với UTH Todo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color(0xFF202124)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Đăng nhập để lưu công việc và đồng bộ trên mọi thiết bị của bạn.",
                    fontSize = 14.sp,
                    color = SecondaryText
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Benefit bullets
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlue.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(12.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Tự động sao lưu, không lo mất dữ liệu", fontSize = 13.sp, color = Color(0xFF202124))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlue.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(12.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Dùng trên iPhone, iPad và Mac", fontSize = 13.sp, color = Color(0xFF202124))
                }
            }

            // Bottom actions
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Google Login Button
                OutlinedButton(
                    onClick = onGoogleLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Text(
                        text = "Tiếp tục với Google",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF202124)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE5E7EB))
                    Text(
                        text = "hoặc",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontSize = 13.sp,
                        color = SecondaryText
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE5E7EB))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Dùng thử không cần tài khoản",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue,
                    modifier = Modifier
                        .clickable { onContinueWithoutAccount() }
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Khi tiếp tục, bạn đồng ý với Điều khoản sử dụng và Chính sách quyền riêng tư của UTH Todo.",
                    fontSize = 11.sp,
                    color = SecondaryText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun WelcomeLoginScreenPreview() {
    com.example.uth_todov2.ui.theme.UTH_todov2Theme {
        WelcomeLoginScreen(
            onNavigateBack = {},
            onContinueWithoutAccount = {},
            onGoogleLogin = {}
        )
    }
}

