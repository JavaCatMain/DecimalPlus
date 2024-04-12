/**
 * just very big numbers in range from -10^Double.MAX_VALUE (about -1ee308) to 10^Double.MAX_VALUE (about 1ee308)
 */
@SuppressWarnings("unused")
public class DecimalPlus {

    double value = 0;
    private int sign = 1;

    /**
     * @param mantissa acts like multiplier
     * @param exponent 10^exponent
     */
    public DecimalPlus(double mantissa,double exponent){
        if(mantissa==0){
            this.sign = 0;
        }else
        if(mantissa>0){
            this.value = Math.log10(mantissa)+exponent;
        }else
        {
            this.sign = -1;
            this.value = Math.log10(Math.abs(mantissa))+exponent;
        }
    }
    public DecimalPlus(double basicValue){
        this(basicValue,0);
    }
    public DecimalPlus(){
        this(0,0);
    }

    public void flipSign(){
        this.sign = -this.sign;
    }
    public void setSign(int sign) {
        if(sign>1||sign<-1)throw new IllegalArgumentException("Sign must be 1, 0 or -1");
        else this.sign = sign;
    }

    /**
     * Increases current decimal value by other decimal
     */
    public void add(DecimalPlus decimal){
        if (this.sign != 0 && decimal.sign == this.sign){
            this.value = addFormula(this.value, decimal.value);
        }
        else if (this.sign == 0 && decimal.sign != 0) {
            this.value = decimal.value;
            this.sign = decimal.sign;
        }
        else if (this.sign == -decimal.sign && this.sign != 0){
            if(this.value == decimal.value)this.sign=0;
            else if(this.value < decimal.value){
                this.sign = decimal.sign;
                this.value = subFormula(decimal.value,this.value);
            } else {
                this.value = subFormula(this.value,decimal.value);
            }
        }
    }
    public void add(double value){
        add(new DecimalPlus(value));
    }
    /**
     * Decreases current decimal value by other decimal
     * (just add method with flipped sign)
     */
    public void sub(DecimalPlus decimal){
        decimal.flipSign();
        add(decimal);
    }
    public void sub(double decimal){
        add(-decimal);
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
        if(this.sign == 0)return "0.000";

        String out;
        double mantissa = Math.floor(Math.pow(10,(this.value % 1))*100)/100;;
        double exponent = Math.floor(this.value);

        if(this.value>3 || (this.value<-3 && !isRounded)){
            out = mantissa + "e" + (exponent+"");//.substring(0,(int)Math.floor(Math.log10(exponent)+1));
        }
        else {
            if (isRounded) {
                out = Math.round(Math.pow(10, this.value)) + "";
            } else {
                out = ((Math.pow(10, this.value+1)/10) + "");
                out = out.substring(0,Math.min(out.length(),5));
                out = out + "0".repeat(5-out.length());
            }
        }
        if(this.sign==-1 && !out.equals("0")){out = "-"+out;}
        return out;
    }
    public String toString(){
        return toString(true);
    }
}
