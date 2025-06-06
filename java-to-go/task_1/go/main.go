package main

import (
	"fmt"
)

type Advertisment interface {
	calculatePrice() int
}

type Billboard struct {
	squareMeters             int
	hours                    int
	hourlyRatePerSquareMeter int
}

type Radio struct {
	numberOfEmissions int
	ratePerEmission   int
}

type Newspaper struct {
	numberOfWords int
	pricePerWord  int
}

func (b Billboard) calculatePrice() int {
	return b.squareMeters * b.hours * b.hourlyRatePerSquareMeter
}

func (r Radio) calculatePrice() int {
	return r.numberOfEmissions * r.ratePerEmission
}

func (n Newspaper) calculatePrice() int {
	return n.numberOfWords * n.pricePerWord
}

func calculatePriceOfAdvertisingCampaign(adverts []Advertisment) int {
	var result = 0
	for _, advert := range adverts {
		result += advert.calculatePrice()
	}
	return result
}

func main() {
	bAdvert := Billboard{squareMeters: 12, hours: 168, hourlyRatePerSquareMeter: 120}
	rAdvert := Radio{numberOfEmissions: 15, ratePerEmission: 1000}
	nAdvert := Newspaper{numberOfWords: 450, pricePerWord: 79}

	advertisments := []Advertisment{bAdvert, rAdvert, nAdvert}

	for _, advert := range advertisments {
		fmt.Println("Price: ", advert.calculatePrice())
	}

	finalPrice := calculatePriceOfAdvertisingCampaign(advertisments)

	fmt.Println("Final price of the campaign: ", finalPrice)
}
