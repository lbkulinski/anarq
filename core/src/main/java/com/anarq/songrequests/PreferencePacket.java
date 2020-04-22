package com.anarq.songrequests;

public class PreferencePacket {
	
	private boolean pop;
    private boolean rock;
    private boolean country;
    private boolean jazz;
    private boolean rap;
    private boolean metal;
    private boolean rb;
    private boolean hiphop;
    private boolean electronic;
    private boolean christian;
	private boolean explicit;
	private boolean visible;
	private boolean requests;
	private int maxBPM;
	private int minBPM;
	private int dislikeThreshold;
	
	public PreferencePacket(int maxBPM, int minBPM, int dislikeThreshold, boolean pop, boolean rock,
	boolean country, boolean jazz, boolean rap, boolean metal,
	boolean rb, boolean hiphop, boolean electronic, boolean christian, boolean explicit,
	boolean visible, boolean requests) {
		
		this.maxBPM = maxBPM;
		this.minBPM = minBPM;
		this.dislikeThreshold = dislikeThreshold;
		this.pop = pop;
		this.rock = rock;
		this.country = country;
		this.jazz = jazz;
		this.rap = rap;
		this.metal = metal;
		this.rb = rb;
		this.hiphop = hiphop;
		this.electronic = electronic;
		this.christian = christian;
		this.explicit = explicit;
		this.visible = visible;
		this.requests = requests;
		
	}
	
	public int getMaxBPM() {
		return maxBPM;
	}
	public int getMinBPM() {
		return minBPM;
	}
	public int getDislikeThreshold() {
		return dislikeThreshold;
	}
	public boolean getPop() {
		return pop;
	}
	public boolean getRock() {
		return rock;
	}
	public boolean getCountry() {
		return country;
	}
	public boolean getJazz() {
		return jazz;
	}
	public boolean getMetal() {
		return metal;
	}
	public boolean getRB() {
		return rb;
	}
	public boolean getHiphop() {
		return hiphop;
	}
	public boolean getElectronic() {
		return electronic;
	}
	public boolean getChristian() {
		return christian;
	}
	public boolean getExpilcit() {
		return explicit;
	}
	public boolean getVisible() {
		return visible;
	}
	public boolean getRequests() {
		return requests;
	}
	
}