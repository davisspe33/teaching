package employee;

public class HourlyEmployee extends Employee{
	private double hourlyWage;
	private double hoursWorked;
	private int maxHours=168;
	
	public HourlyEmployee(String firstName, String lastName, String socialSecurityNumber, double wage, double hours){
		super(firstName,lastName,socialSecurityNumber);
		if (hourlyWage < 0)
			throw new IllegalArgumentException("Wage must be higher than 0");
		else
			hourlyWage = wage;
		if (hours  < 1 || hours > maxHours)
			throw new IllegalArgumentException("Hours worked must be between 1 and 168");	
		else
			hoursWorked = hours;
	}
	public double getWage(){
		return hourlyWage;
	}	
	public void setWage(double wage){
		if (wage > 0)
			hourlyWage = wage;
		else
			throw new IllegalArgumentException("Wage must be higher than 0");
	}
	public double getHours(){
			return hoursWorked;
	}
	public void setHours(double hours){
		if (hours  < 1 || hours > maxHours)
			throw new IllegalArgumentException("Hours worked must be between 1 and 168");
		else
			hoursWorked = hours;
	}
	
	@Override
	public double earnings(){
			if (getHours() > 40)
			{
				return (getWage() * 40) + (1.5 * getWage() * (getHours() - 40));
			}
			else
				return getHours() * getWage();
	}
	
	@Override
	public void raise(double percent){
		if (percent <= 0){
			throw new IllegalArgumentException("Raise percentage must be greater than zero.");
		}
		else{
			setWage(hourlyWage + hourlyWage * percent);
		}
			
	}
	
	@Override
	public String toString(){
		return super.toString("Hourly Employee") + String.format("\t%s: %.2f%n\t%s: %.2f%n\t%s: $%.2f%n", "Hourly Wage", getWage(),"Hours Worked", getHours(),"Earnings", earnings());
	}
}