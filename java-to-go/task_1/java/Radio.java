public class Radio implements Advertisement {
  
  	private int numberOfEmissions;
	private int ratePerEmission;                    

    public Radio(int numberOfEmissions, int ratePerEmission) {
        this.numberOfEmissions = numberOfEmissions;
        this.ratePerEmission = ratePerEmission;
    }

    @Override
    public int calculatePrice() {
        return this.numberOfEmissions * this.ratePerEmission;
    }
}