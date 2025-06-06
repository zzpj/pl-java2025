public class Billboard implements Advertisement {
  
  	private int squareMeters;
	private int hours;                    
	private int hourlyRatePerSquareMeter;
	
    public Billboard(int squareMeters, int hours, int hourlyRatePerSquareMeter) {
        this.squareMeters = squareMeters;
        this.hours = hours;
        this.hourlyRatePerSquareMeter = hourlyRatePerSquareMeter;
    }

    @Override
    public int calculatePrice() {
        return this.squareMeters * this.hours * this.hourlyRatePerSquareMeter;
    }
}