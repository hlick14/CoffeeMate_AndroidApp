package ie.cm.models;

import java.io.Serializable;

public class Coffee implements Serializable
{

	public String _id;
	public double coffePrice;
	public String coffeName;
	public String coffeeShop;
	public double rating;
	public boolean favorite;




	public Coffee(double coffeePrice, String coffeeName, String coffeeShop, double rating, boolean favourite)
	{
		this.coffePrice = coffeePrice;
		this.coffeName = coffeeName;
		this.coffeeShop = coffeeShop;
		this.rating = rating;
		this.favorite = favourite;
	}
	public Coffee() {
        this._id = "";
		this.coffePrice = 0.0;
		this.coffeName = "";
		this.coffeeShop = "";
		this.rating = 0.0;
		this.favorite = false;
	}
    public double getRating(){return this.rating;
        }
    public String getName(){return coffeName;
    }
	public String toString()
	{
		return "["+ _id +"]" + coffePrice + ", " + coffeName + ", " + coffeeShop + ", "+ rating + ", "+ favorite;
	}



//	@Override
//	public String toString() {
//		return "Coffee [coffeeId"+ coffeeId +"coffeePrice=" + coffeePrice
//				+ ", coffeeName =" + coffeeName + ", coffeeShop=" + coffeShop + ", rating=" + rating
//				+ ", favourite =" + favourite + "]";
//	}
}
