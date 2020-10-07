package model;

public enum BoatType {
	SAILBOAT("sailboat"),
	MOTORSAILER("motorsailer"),
	KAYAK("kayak"),
	CANOE("canoe"),
	OTHER("other");
	
	

    private final String text;

    /**
     * @param text
     */
    BoatType(final String text) {
        this.text = text;
    }
    
    public String getText() {
    	return text;
    }
}
