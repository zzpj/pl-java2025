public class Person {

  private String firstName;
  private String lastName;
  private int age;
  
  private Person() {
     // private!
  }
   
   public  Person(String firstName, String lastName, int age) throws ValidationException {
         validateName(firstName);
    validateName(lastName);
    validateAge(age);
    
     this.firstName = firstName;
     this.lastName = lastName;
     this.age = age;
   }
  
  private void validateName(String name) throws ValidationException {
    if (name == null || name.isEmpty()) {
      throw new ValidationException("Name is invalid");
    }
  }
  
  private void validateAge(int age) throws ValidationException {
        if (age <= 0) {
      throw new ValidationException("Age is invalid");
    }
  }
}