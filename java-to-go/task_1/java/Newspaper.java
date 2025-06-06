public class Newspaper implements Advertisement {
  
  	private int numberOfWords;
	private int pricePerWord;                    

    public Newspaper(int numberOfWords, int pricePerWord) {
        this.numberOfWords = numberOfWords;
        this.pricePerWord = pricePerWord;
    }

    @Override
    public int calculatePrice() {
        return this.numberOfWords * this.pricePerWord;
    }
}