package aventurian;

public class SecondaryAttributeHelper {

    private int basis;
    private int mod;
    private int modBuy;
    private int maxBuy;

    public SecondaryAttributeHelper() {
        this.basis = 0;
        this.mod = 0;
        this.modBuy = 0;
        this.maxBuy = 0;
    }

    public void setBasis(int basis) {
        if (basis < 0) throw new IllegalArgumentException("Basis cannot be less than zero!");
        this.basis = basis;
    }
    
    public void setMax(int max) {
    	if (max < 0) throw new IllegalArgumentException("Maximum cannot be less than zero!");
    	this.maxBuy = max;
    }

    public void increaseMod(int mod) {
        if (mod < 0) throw new IllegalArgumentException("Input must not be less than zero!");
        this.mod += mod;
    }

    public void decreaseMod(int mod) {
        if (mod < 0) throw new IllegalArgumentException("Input must not be less than zero!");
        this.mod -= mod;
    }

    public void increaseModBuy() {
        this.modBuy++;
    }

    public void decreaseModBuy() {
        this.modBuy--;
    }

    public int getActual() {
        return basis + mod + modBuy;
    }
    
    boolean isIncreasableByBuy() {
    	return modBuy < maxBuy;
    }
    
    boolean isDecreasableByBuy() {
    	return getActual() > 0;
    }
}
