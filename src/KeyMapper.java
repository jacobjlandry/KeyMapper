import java.util.ArrayList;

public class KeyMapper {

	public static void main(String[] args) {
		// TODO Clean up
		// TODO Difference between List<String> and ArrayList<String> and String[] and why do i use each one?
		// TODO Tests
		// TODO Comments
		
		KeyBindsFile binds = new KeyBindsFile("Custom.3.0.binds");
		KeyMaps maps = new KeyMaps();

		maps.setUsed((ArrayList<String>) binds.getBinds());
		binds.replaceContents(maps.getDiffs(), maps.getModifiers());
	}

}
