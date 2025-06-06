package main

import (
	"errors"
	"fmt"
)

type ValidationError struct {
	Field string
}

func (e ValidationError) Error() string {
	return fmt.Sprintf("%s is invalid", e.Field)
}

type Person struct {
	FirstName string
	LastName  string
	Age       int
}

func (p Person) PrintPerson() {
	fmt.Printf("%s %s, %d\n", p.FirstName, p.LastName, p.Age)
}

func errorsIsComparison() {
	//TODO Implement here
}

func errorsAsComparison() {
	var validationErr ValidationError
	_, err := createPerson("Krzysztof", "", -1)

	if errors.As(err, &validationErr) {
		fmt.Printf("Validation error occured: %s\n", validationErr)
	}
}

func main() {
	jan, err := createPerson("Jan", "Kowalski", 39)
	if err != nil {
		fmt.Println("Error:", err)
	} else {
		jan.PrintPerson()
	}

	krzysztof, err := createPerson("Krzysztof", "", -1)
	if err != nil {
		fmt.Println("Error:", err)
	} else {
		krzysztof.PrintPerson()
	}

	errorsIsComparison()
	errorsAsComparison()
}

func validateName(name string, nameOfField string) error {
	if name == "" {
		return ValidationError{Field: nameOfField}
	}
	return nil
}

func validateAge(age int) error {
	//TODO Implement here
}

func createPerson(fName string, lName string, age int) (Person, error) {
	fmt.Printf("Entered: %s, %s, %d\n", fName, lName, age)

	err := validateName(fName, "FirstName")
	if err != nil {
		return Person{}, err
	}
	err = validateName(lName, "LastName")
	if err != nil {
		return Person{}, err
	}
	err = validateAge(age)
	if err != nil {
		return Person{}, err
	}

	return Person{FirstName: fName, LastName: lName, Age: age}, nil
}
