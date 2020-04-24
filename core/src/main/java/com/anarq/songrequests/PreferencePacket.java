package com.anarq.songrequests;

/**
 * A packet of preferences in the AnarQ Application.
 *
 * @version April 24, 2020
 */
public class PreferencePacket {
    /**
     * The pop flag of this preference packet.
     */
    private final boolean pop;

    /**
     * The rock flag of this preference packet.
     */
    private final boolean rock;

    /**
     * The country flag of this preference packet.
     */
    private final boolean country;

    /**
     * The jazz flag of this preference packet.
     */
    private final boolean jazz;

    /**
     * The rap flag of this preference packet.
     */
    private final boolean rap;

    /**
     * The metal flag of this preference packet.
     */
    private final boolean metal;

    /**
     * The R&B flag of this preference packet.
     */
    private final boolean rb;

    /**
     * The hip hop flag of this preference packet.
     */
    private final boolean hiphop;

    /**
     * The electronic flag of this preference packet.
     */
    private final boolean electronic;

    /**
     * The christian flag of this preference packet.
     */
    private final boolean christian;

    /**
     * The explicit flag of this preference packet.
     */
    private final boolean explicit;

    /**
     * The visible flag of this preference packet.
     */
    private final boolean visible;

    /**
     * The requests flag of this preference packet.
     */
    private final boolean requests;

    /**
     * The maximum BPM of this preference packet.
     */
    private final int maxBPM;

    /**
     * The minimum BPM of this preference packet.
     */
    private final int minBPM;

    /**
     * The dislike threshold of this preference packet.
     */
    private final int dislikeThreshold;

    /**
     * Constructs a newly allocated {@code PreferencePacket} object with the specified preferences.
     *
     * @param maxBPM the maximum BPM to be used in construction
     * @param minBPM the minimum BPM to be used in construction
     * @param dislikeThreshold the dislike threshold to be used in construction
     * @param pop the pop flag to be used in construction
     * @param rock the rock flag to be used in construction
     * @param country the country flag to be used in construction
     * @param jazz the jazz flag to be used in construction
     * @param rap the rap flag to be used in construction
     * @param metal the metal flag to be used in construction
     * @param rb the R&B flag to be used in construction
     * @param hiphop the hip hop flag to be used in construction
     * @param electronic the electronic flag to be used in construction
     * @param christian the christian flag to be used in construction
     * @param explicit the explicit flag to be used in construction
     * @param visible the visible flag to be used in construction
     * @param requests the requests flag to be used in construction
     */
    public PreferencePacket(int maxBPM, int minBPM, int dislikeThreshold, boolean pop, boolean rock, boolean country,
                            boolean jazz, boolean rap, boolean metal, boolean rb, boolean hiphop, boolean electronic,
                            boolean christian, boolean explicit, boolean visible, boolean requests) {
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
    } //PreferencePacket

    /**
     * Returns the maximum BPM of this preference packet.
     *
     * @return the maximum BPM of this preference packet
     */
    public int getMaxBPM() {
        return maxBPM;
    } //getMaxBPM

    /**
     * Returns the minimum BPM of this preference packet.
     *
     * @return the minimum BPM of this preference packet
     */
    public int getMinBPM() {
        return minBPM;
    } //getMinBPM

    /**
     * Returns the dislike threshold of this preference packet.
     *
     * @return the dislike threshold of this preference packet
     */
    public int getDislikeThreshold() {
        return dislikeThreshold;
    } //getDislikeThreshold

    /**
     * Returns the pop flag of this preference packet.
     *
     * @return the pop flag of this preference packet
     */
    public boolean getPop() {
        return pop;
    } //getPop

    /**
     * Returns the rock flag of this preference packet.
     *
     * @return the rock flag of this preference packet
     */
    public boolean getRock() {
        return rock;
    } //getRock

    /**
     * Returns the country flag of this preference packet.
     *
     * @return the country flag of this preference packet
     */
    public boolean getCountry() {
        return country;
    } //getCountry

    /**
     * Returns the jazz flag of this preference packet.
     *
     * @return the jazz flag of this preference packet
     */
    public boolean getJazz() {
        return jazz;
    } //getJazz

    /**
     * Returns the rap flag of this preference packet.
     *
     * @return the rap flag of this preference packet
     */
    public boolean getRap() {
        return rap;
    } //getRap

    /**
     * Returns the metal flag of this preference packet.
     *
     * @return the metal flag of this preference packet
     */
    public boolean getMetal() {
        return metal;
    } //getMetal

    /**
     * Returns the R&B flag of this preference packet.
     *
     * @return the R&B flag of this preference packet
     */
    public boolean getRB() {
        return rb;
    } //getRB

    /**
     * Returns the hip hop flag of this preference packet.
     *
     * @return the hip hop flag of this preference packet
     */
    public boolean getHiphop() {
        return hiphop;
    } //getHiphop

    /**
     * Returns the electronic flag of this preference packet.
     *
     * @return the electronic flag of this preference packet
     */
    public boolean getElectronic() {
        return electronic;
    } //getElectronic

    /**
     * Returns the christian flag of this preference packet.
     *
     * @return the christian flag of this preference packet
     */
    public boolean getChristian() {
        return christian;
    } //getChristian

    /**
     * Returns the explicit flag of this preference packet.
     *
     * @return the explicit flag of this preference packet
     */
    public boolean getExpilcit() {
        return explicit;
    } //getExpilcit

    /**
     * Returns the visible flag of this preference packet.
     *
     * @return the visible flag of this preference packet
     */
    public boolean getVisible() {
        return visible;
    } //getVisible

    /**
     * Returns the requests flag of this preference packet.
     *
     * @return the requests flag of this preference packet
     */
    public boolean getRequests() {
        return requests;
    } //getRequests
}