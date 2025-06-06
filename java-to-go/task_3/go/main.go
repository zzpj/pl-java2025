package main

import (
	"encoding/json"
	"fmt"
)

type User struct {
	Name     string `json:"name"`
	Nickname string `json:"nickname"`
	Age      int    `json:"age"`
	IsActive bool   `json:"is_active"`
}

// simulates API or config file
func read() string {
	return `{
		"name": "Jan",
		"nickname": "jan_91",
		"age": 34,
		"is_active": true
	}`
}

func unmarshalJson(jsonData string) (User, error) {
	var user User
	err := json.Unmarshal([]byte(jsonData), &user)

	if err != nil {
		fmt.Println("Error decoding JSON: ", err)
		return User{}, err
	}

	return user, nil
}

func marshalJson(user User) ([]byte, error) {
	//TODO: implement it
}

func main() {
	jsonData := read()

	user, err := unmarshalJson(jsonData)
	if err != nil {
		return
	}

	user.Nickname = "janek"
	user.Age += 1
	user.IsActive = false

	updatedJson, err := marshalJson(user)
	if err != nil {
		return
	}

	fmt.Println("Updated User JSON: ")
	fmt.Println(string(updatedJson))
}
