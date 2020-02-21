package employee;

public class SalariedEmployee extends Employee{
	private double salary;

	public SalariedEmployee(String firstName, String lastName, String socialSecurityNumber, double salary)
	{
		super(firstName,lastName,socialSecurityNumber);
		if (salary <= 0)
			throw new IllegalArgumentException("Salary must be greater than zero!");
		else 
		   this.salary = salary;
	}
	
	public void setSalary(double salary) {
		if (salary <= 0)
			throw new IllegalArgumentException("Salary must be greater than zero!");
		else 
		   this.salary = salary;
	}
	
	public double getSalary(){
		return salary;
	}
	
	@Override
	public double earnings(){
		return salary;
	}
	
	@Override
	public void raise(double percent){
		if (percent <= 0)
			throw new IllegalArgumentException("Percent raise must be greater than zero.");
		else
			setSalary(salary + salary * percent);
	}
	
	@Override
	public String toString(){
		return String.format("%s\t%s: %.2f%n\t%s: $%.2f%n", 
				super.toString("Salaried Employee"),
				"Salary", salary,
				"Earnings", earnings());
	}
}