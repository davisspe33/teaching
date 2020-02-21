/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

/**
 *
 * @author sod3
 */
public abstract class Employee {
    private String firstName;
    private String lastName;
    private String socialSecrurityNumber; 
    /**
     * @param args the command line arguments
     */
    public Employee(String first, String last, String social){
        firstName=first;
        lastName=last;
        socialSecrurityNumber=social; 
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName; 
    }
    public String getSocial(){
        return this.socialSecrurityNumber;
    }
    public String toString(String employeeType){
        return String.format("%s: %s %s with ssn: %s%n",
                employeeType, getFirstName(), getLastName(), getSocial());
    }
    
    public abstract double earnings();
    
    public abstract void raise(double percent);
    
    public static void main(String[] args) {
            CommissionEmployee employee1 = new CommissionEmployee("Fred", "Jones", "111-11-1111", 2000.0, .05);
            BasePlusCommissionEmployee employee2 = new BasePlusCommissionEmployee("Sue", "Smith", "222-22-2222", 3000.0,  0.05, 300);
            SalariedEmployee employee3 = new SalariedEmployee("Sha", "Yang", "333-33-3333", 1150.0);
            HourlyEmployee employee4 = new HourlyEmployee("Ian", "Tanning", "444-44-4444", 15.0, 50);
            HourlyEmployee employee5 = new HourlyEmployee("Angela", "Domchek", "555-55-5555", 20.0, 40);
            System.out.printf("%s%n%s%s%s%s%s%n", "Employee information.", employee1, employee2, employee3, employee4, employee5);
            
            Employee[] employees=new Employee[5];
            
            employees[0]= employee1;
            employees[1]= employee2;
            employees[2]= employee3;
            employees[3]= employee4;
            employees[4]= employee5;
            
            for (Employee currentemployee: employees){
                if (currentemployee instanceof SalariedEmployee)
                    currentemployee.raise(.04);
                else
                    currentemployee.raise(.02);
            }
            
            System.out.printf("%s%n%s%s%s%s%s%n", "Employee information after raises.", employee1, employee2, employee3, employee4, employee5);
    }
}
