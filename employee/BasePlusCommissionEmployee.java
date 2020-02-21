package employee;

public class BasePlusCommissionEmployee extends CommissionEmployee{
	private double base;
	
	public BasePlusCommissionEmployee(String firstName, String lastName, String socialSecurityNumber, double sales, double rate, double base){
		
		super(firstName,lastName,socialSecurityNumber,sales,rate);
		
		if (base <= 0)
			throw new IllegalArgumentException("Enter base greater than zero!");
		else
			this.base=base;
	}
	public double getBase(){
		return base;
	}
	public void setBase(double base){
		if (base <= 0)
			throw new IllegalArgumentException("Enter base greater than zero!");
		else
			this.base=base;
	}
	
	@Override
	public double earnings(){
		return base + super.earnings();
	}
	
	@Override
	public void raise(double percent){
		if (percent <= 0){
			throw new IllegalArgumentException("Percent raise must be greater than zero.");
		}
		else{
			super.raise(percent);
			this.setBase(base + base * percent);
		}
			
		
	}
	
	@Override
	public String toString(){
		return String.format("%s: %s %s with ssn: %s%n\t%s: %.2f%n\t%s: %.4f%n\t%s: $%.2f%n\t%s: $%.2f%n",
				"Base Salary Plus Commissioned Employee", 
				super.getFirstName(), super.getLastName(),
				super.getSocial(),
				"Gross Sales", super.getGrossSales(),
				"Commission Rate", super.getCommissisonRate(),
				"with Base Salary of", getBase(),
				"Earnings", earnings());
	}
}

