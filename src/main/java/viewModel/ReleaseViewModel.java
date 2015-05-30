package viewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReleaseViewModel {
	private String duration;
	private String date;
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = String.format("%d minutes and %d seconds",
				TimeUnit.MILLISECONDS.toMinutes(duration),
				TimeUnit.MILLISECONDS.toSeconds(duration) -
	            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
	}

	public String getDate() {
		return date;
	}

	public void setDate(String dateString) {
		String pattern1 = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern1);
		String pattern2 = "dd-MM-yyyy";
		SimpleDateFormat format2 = new SimpleDateFormat(pattern2);

		Date date = null;
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.date = format2.format(date);
	}

}
