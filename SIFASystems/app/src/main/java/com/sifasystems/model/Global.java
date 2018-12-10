package com.sifasystems.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Global {
	public static String namePattern = "^[\\p{L} .'-]+$";
	public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	public static String phonePattern = "^[+]?[0-9]{8,20}$";
	public static String passwordPattern = "^([a-zA-Z0-9!@#$%&*]{6,20})$";

	public static String EMAIL_ERROR_MESSAGE = "Please input correct email address!";
	public static String PASSWORD_ERROR_MESSAGE = "Password should have 6 characters at least!";
	public static String PASSWORD_MATCH_ERROR_MESSAGE = "Password doesn't match.";
	public static String USERNAME_ERROR_MESSAGE = "Please input full name!";
	public static String AGE_ERROR_MESSAGE = "Age should less than 100.";

	public static User selectedUser = null;
	public static boolean user_type = false;
	public static int selectedPaper = 0;
	public static int malpractice_type = 0;

	public static String parseDateToddMMyyyy(String time) {
		String inputPattern = "yyyy-MM-dd";
		String outputPattern = "MMM dd, yyyy";
		SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
		SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

		Date date = null;
		String str = null;

		try {
			date = inputFormat.parse(time);
			str = outputFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

	public static boolean hasString(List<String> list, String search) {
		if (list == null) { return false; }
		for (int i = 0; i < list.size(); i ++) {
			String temp = list.get(i);
			if (temp.equals(search)) {
				return true;
			}
		}
		return false;
	}

	public static Date getConvertedLocalTime(String time) {
		SimpleDateFormat inputFormat = new SimpleDateFormat
				("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
		inputFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

		SimpleDateFormat outputFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Adjust locale and zone appropriately
		Date date = null;
		try {
			date = inputFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String outputText = outputFormat.format(date);
		System.out.println(outputText);
		return date;
	}
}
