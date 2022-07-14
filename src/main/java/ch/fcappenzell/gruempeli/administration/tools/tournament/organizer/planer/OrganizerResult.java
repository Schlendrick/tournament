package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer;

public class OrganizerResult {

	private final String message;

	private OrganizerResult(String message){
		this.message = message;
	}

	public static OrganizerResult create(String message){
		return new OrganizerResult(message);
	}

	public static OrganizerResult create(boolean errorIfTrue, String message) {
		return errorIfTrue ? new OrganizerResult(message) : ok();
	}

	public static OrganizerResult ok(){
		return new OrganizerResult(null);
	}

	public String getMessage(){
		return message == null ? "ok" : message;
	}

	public boolean isOk(){
		return message == null;
	}
}
