package employee;

public class CommissionEmployee extends Employee{
		private double grossSales;
		private double commissionRate; 
		
		public CommissionEmployee(String firstName, String lastName, String socialSecurityNumber, double gSales, double cRate){
			super(firstName,lastName,socialSecurityNumber);
			if(gSales<=0.0)
				throw new IllegalArgumentException("Gross Sales must be >=0.0");
			else
				grossSales=gSales;
			if(cRate<=0.0 || cRate >=1)
				throw new IllegalArgumentException("Commision rate must be > 0.0 and <1.0");
			
			else
				this.commissionRate=cRate;
		}
		
		public void setGrossSales(double sales){
			if(sales<=0.0){
				throw new IllegalArgumentException("Gross Sales must be >=0.0");
			} 
			grossSales=sales;
		}
		
		public double getGrossSales(){
			return grossSales;
		}
		
		public void setCommissionRate(double rate){
			if(rate<=0.0 || rate >=1){
				throw new IllegalArgumentException("Commision rate must be > 0.0 and <1.0");
			}
			commissionRate=rate;
		}
		
		public double getCommissisonRate(){
			return commissionRate;
		}
		
		@Override
		public double earnings(){
			return getCommissisonRate() * getGrossSales();
		}
		
		@Override
		public void raise(double percent){
			if (percent > 0)
				setCommissionRate(commissionRate + commissionRate * percent);
			else
				throw new IllegalArgumentException("Percent raise must be greater than zero.");
		}
		
		@Override
		public String toString(){
			return  super.toString("Commissioned Employee") + 
					String.format("\t%s: %.2f%n\t%s: %.4f%n\t%s: $%.2f%n", 
					"Gross Sales", getGrossSales(),
					"Commission Rate", getCommissisonRate(),
					"Earnings", earnings());
		}
}