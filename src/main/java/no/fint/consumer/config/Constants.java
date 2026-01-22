package no.fint.consumer.config;

public enum Constants {
;

    public static final String COMPONENT = "administrasjon-personal";
    public static final String COMPONENT_CONSUMER = COMPONENT + " consumer";
    public static final String CACHE_SERVICE = "CACHE_SERVICE";

    
    public static final String CACHE_INITIALDELAY_ARBEIDSFORHOLD = "${fint.consumer.cache.initialDelay.arbeidsforhold:900000}";
    public static final String CACHE_FIXEDRATE_ARBEIDSFORHOLD = "${fint.consumer.cache.fixedRate.arbeidsforhold:900000}";
    
    public static final String CACHE_INITIALDELAY_FASTLONN = "${fint.consumer.cache.initialDelay.fastlonn:1000000}";
    public static final String CACHE_FIXEDRATE_FASTLONN = "${fint.consumer.cache.fixedRate.fastlonn:900000}";
    
    public static final String CACHE_INITIALDELAY_FASTTILLEGG = "${fint.consumer.cache.initialDelay.fasttillegg:1100000}";
    public static final String CACHE_FIXEDRATE_FASTTILLEGG = "${fint.consumer.cache.fixedRate.fasttillegg:900000}";
    
    public static final String CACHE_INITIALDELAY_FRAVAR = "${fint.consumer.cache.initialDelay.fravar:1200000}";
    public static final String CACHE_FIXEDRATE_FRAVAR = "${fint.consumer.cache.fixedRate.fravar:900000}";
    
    public static final String CACHE_INITIALDELAY_KONTAKTPERSON = "${fint.consumer.cache.initialDelay.kontaktperson:1300000}";
    public static final String CACHE_FIXEDRATE_KONTAKTPERSON = "${fint.consumer.cache.fixedRate.kontaktperson:900000}";
    
    public static final String CACHE_INITIALDELAY_PERSON = "${fint.consumer.cache.initialDelay.person:1400000}";
    public static final String CACHE_FIXEDRATE_PERSON = "${fint.consumer.cache.fixedRate.person:900000}";
    
    public static final String CACHE_INITIALDELAY_PERSONALRESSURS = "${fint.consumer.cache.initialDelay.personalressurs:1500000}";
    public static final String CACHE_FIXEDRATE_PERSONALRESSURS = "${fint.consumer.cache.fixedRate.personalressurs:900000}";
    
    public static final String CACHE_INITIALDELAY_VARIABELLONN = "${fint.consumer.cache.initialDelay.variabellonn:1600000}";
    public static final String CACHE_FIXEDRATE_VARIABELLONN = "${fint.consumer.cache.fixedRate.variabellonn:900000}";
    

}
