package com.example.uth_todov2.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uth_todov2.ui.components.BottomNavigationBar
import com.example.uth_todov2.ui.theme.PrimaryBlue
import com.example.uth_todov2.ui.theme.SecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    onNavigate: (String) -> Unit,
    onAddTaskClick: () -> Unit
) {
    var selectedThemeMode by remember { mutableIntStateOf(if (isDarkMode) 1 else 0) } // 0: Sáng, 1: Tối, 2: Tự động
    var selectedColorIndex by remember { mutableIntStateOf(0) }
    var fontSizeSlider by remember { mutableFloatStateOf(0.5f) }
    var reminderNotif by remember { mutableStateOf(true) }
    var soundNotif by remember { mutableStateOf(true) }
    var dailySummary by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "profile",
                onNavigate = onNavigate,
                onAddTaskClick = onAddTaskClick
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Header: Cài đặt + MA avatar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cài đặt",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF202124)
                )

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MA",
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            // Section: GIAO DIỆN
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "GIAO DIỆN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryText
                )

                // Theme Mode Cards (Sáng, Tối, Tự động)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ThemeCard(
                        title = "Sáng",
                        selected = selectedThemeMode == 0,
                        onClick = {
                            selectedThemeMode = 0
                            onToggleDarkMode(false)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.width(24.dp).height(4.dp).background(Color(0xFFE5E7EB), RoundedCornerShape(2.dp)))
                        }
                    }

                    ThemeCard(
                        title = "Tối",
                        selected = selectedThemeMode == 1,
                        onClick = {
                            selectedThemeMode = 1
                            onToggleDarkMode(true)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(Color(0xFF1C1B1F), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.width(24.dp).height(4.dp).background(Color(0xFF49454F), RoundedCornerShape(2.dp)))
                        }
                    }

                    ThemeCard(
                        title = "Tự động",
                        selected = selectedThemeMode == 2,
                        onClick = { selectedThemeMode = 2 },
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color.White))
                            Box(modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFF1C1B1F)))
                        }
                    }
                }

                // Accent Color Picker
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Màu chủ đạo", fontSize = 14.sp, color = Color(0xFF202124), fontWeight = FontWeight.Medium)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            val colors = listOf(PrimaryBlue, Color(0xFF34A853), Color(0xFFEA4335), Color(0xFF9333EA))
                            colors.forEachIndexed { index, color ->
                                Box(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .let {
                                            if (selectedColorIndex == index) it.border(2.dp, Color(0xFF202124), CircleShape) else it
                                        }
                                        .clickable { selectedColorIndex = index }
                                )
                            }
                        }
                    }
                }

                // Font Size Slider
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Cỡ chữ", fontSize = 14.sp, color = Color(0xFF202124), fontWeight = FontWeight.Medium)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(text = "A", fontSize = 12.sp, color = SecondaryText, fontWeight = FontWeight.Bold)
                            Slider(
                                value = fontSizeSlider,
                                onValueChange = { fontSizeSlider = it },
                                modifier = Modifier.weight(1f),
                                colors = SliderDefaults.colors(thumbColor = PrimaryBlue, activeTrackColor = PrimaryBlue)
                            )
                            Text(text = "A", fontSize = 18.sp, color = SecondaryText, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Section: THÔNG BÁO
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "THÔNG BÁO",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryText
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        SettingSwitchRow(
                            title = "Nhắc việc",
                            subtitle = "Báo trước hạn chót 15 phút",
                            checked = reminderNotif,
                            onCheckedChange = { reminderNotif = it }
                        )
                        HorizontalDivider(color = Color(0xFFF3F4F7))
                        SettingSwitchRow(
                            title = "Âm thanh",
                            subtitle = "Phát âm khi có thông báo",
                            checked = soundNotif,
                            onCheckedChange = { soundNotif = it }
                        )
                        HorizontalDivider(color = Color(0xFFF3F4F7))
                        SettingSwitchRow(
                            title = "Tổng kết cuối ngày",
                            subtitle = "Gửi lúc 21:00 mỗi tối",
                            checked = dailySummary,
                            onCheckedChange = { dailySummary = it }
                        )
                    }
                }
            }

            // Section: CHUNG
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "CHUNG",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryText
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {}
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Ngôn ngữ", fontSize = 14.sp, color = Color(0xFF202124), fontWeight = FontWeight.Medium)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Tiếng Việt", fontSize = 14.sp, color = SecondaryText)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = SecondaryText, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ThemeCard(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) PrimaryBlue else Color(0xFFE5E7EB))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (selected) PrimaryBlue else Color(0xFF202124)
            )
        }
    }
}

@Composable
fun SettingSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, color = Color(0xFF202124), fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 11.sp, color = SecondaryText)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = PrimaryBlue)
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun SettingsScreenPreview() {
    com.example.uth_todov2.ui.theme.UTH_todov2Theme {
        SettingsScreen(
            isDarkMode = false,
            onToggleDarkMode = {},
            onNavigate = {},
            onAddTaskClick = {}
        )
    }
}

