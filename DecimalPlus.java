public class DecimalPlus {

    double value = 0;
    private int sign = 1;

    public DecimalPlus(double mantissa,double exponent){
        if(mantissa==0){
            this.sign = 0;
        }else
        if(mantissa>0){
            this.sign = 1;
            this.value = Math.log10(mantissa)+exponent;
        }else
        {
            this.sign = -1;
            this.value = Math.log10(mantissa)+exponent;
        }
    }
    public DecimalPlus(double mantissa){
        this(mantissa,0);
    }
    public DecimalPlus(){
        this(0,0);
    }

    public void flipSign(){
        this.sign = -this.sign;
    }
    
    public void add(DecimalPlus decimal){
        if (this.sign != 0 && decimal.sign == this.sign){
            this.value = addFormula(this.value, decimal.value);
        }
        else if (this.sign == 0 && decimal.sign != 0) {
            this.value = decimal.value;
            this.sign = decimal.sign;
        }
    }
    
    private double addFormula(double a, double b){
        if(Math.abs(a-b)>300){
            return Math.max(a,b);
        }else{
            return a + Math.log10(1 + Math.pow(10, b-a));
        }
    };
    private double subFormula(double a, double b){
        if(Math.abs(a-b)>300){
            return Math.max(a,b);
        }else{
            return a + Math.log10(1 - Math.pow(10, b-a));
        }
    };

    public String toString(boolean isRounded) {
        if(this.sign == 0 && isRounded)return "0";
        if(this.sign == 0 && !isRounded)return "0.000";

        String out = "";
        double mantissa = Math.floor(Math.pow(10,(this.value % 1))*100)/100;;
        double exponent = Math.floor(this.value);

        if(this.value>3 || (this.value<-3 && !isRounded)){
            out = mantissa + "e" + (exponent+"").substring(0,(int)Math.floor(Math.log10(exponent)+1));
        }
        else {
            if (isRounded) {
                out = (long)Math.round(Math.pow(10, this.value)) + "";
            } else {
                out = ((Math.pow(10, this.value+1)/10) + "").substring(0, 5);
            }
        }
        if(this.sign==-1){out = "-"+out;}
        return out;
    }
    public String toString(){
        return toString(true);
    }
}
