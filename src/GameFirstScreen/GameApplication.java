package GameFirstScreen;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

public class GameApplication {
	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");//파일 인코딩 방식
		try {
			Field charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null, null);
		}
		catch (Exception e) {}
		FirstFrame firstFrame = new FirstFrame();
	}
}
