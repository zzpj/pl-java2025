package main

import (
	"fmt"
)

func squareData(id, input int, results chan<- string) {
	//TODO: Implement here
}

func main() {
	fmt.Println("Starting data processing...")
	data := []int{10, 21, 32, 45, 68}

	results := make(chan string, len(data))
	defer close(results)

	for i, val := range data {
		go squareData(i+1, val, results) // start processing in goroutines
	}

	for i := 0; i < len(data); i++ {
		fmt.Println(<-results)
	}

	fmt.Println("All data processed.")
}
